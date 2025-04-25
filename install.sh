#!/usr/bin/env bash
set -e

APP_DIR="$HOME/Hidify-telegram-bot-vpn"
JAR_NAME="vpn-bot-telegram-1.0-SNAPSHOT-all.jar"
SERVICE_NAME="vpn-bot"

# 0. Подготовка каталога приложения
mkdir -p "$APP_DIR"
cd "$APP_DIR"

# 1. Проверка наличия .env
if [ ! -f ".env" ]; then
  cat > .env.example << 'EOF'
# Hiddify
HIDDIFY_API_URL=
HIDDIFY_API_KEY=
HIDDIFY_PROXY_PATCH_ADMIN=
HIDDIFY_PROXY_PATCH_CLIENT=
HIDDIFY_PROXY_PATCH_UNIVERSAL=

# YooKassa
PAYMENT_API_KEY=
PAYMENT_API_KEY_TEST=
YOO_KASSA_API_URL=
SHOP_ID=

# Telegram
TELEGRAM_BOT_TOKEN=
TELEGRAM_BOT_URL=
TELEGRAM_INFO_URL=
TELEGRAM_OFFER_AGREEMENT=
EOF

  echo
  echo "⚠️  Файл .env не найден в $APP_DIR"
  echo "Шаблон создан: $APP_DIR/.env.example"
  echo "Скопируйте его в .env и заполните все значения."
  echo "Затем запустите скрипт снова:"
  echo
  echo "  cd $APP_DIR"
  echo "  cp .env.example .env"
  echo "  sudo bash install.sh"
  exit 1
fi

# 2. Устанавливаем необходимые пакеты (OpenJDK 17)
echo "==> Устанавливаем OpenJDK 17 (если ещё не установлено)"
sudo apt-get update
sudo apt-get install -y openjdk-17-jdk

# 3. Останавливаем сервис, если он уже запущен
echo "==> Останавливаем сервис $SERVICE_NAME (если он запущен)"
sudo systemctl stop "$SERVICE_NAME" || true

# 4. Загружаем последний релиз JAR
echo "==> Загружаем последний релиз JAR из GitHub"
LATEST_RELEASE=$(curl -s https://api.github.com/repos/5ir1us/Hidify-telegram-bot-vpn/releases/latest | grep "tag_name" | cut -d '"' -f 4)
if [ -z "$LATEST_RELEASE" ]; then
  echo "❌ Не удалось определить последний релиз. Проверьте доступ к GitHub."
  echo "URL запроса: https://api.github.com/repos/5ir1us/Hidify-telegram-bot-vpn/releases/latest"
  exit 1
fi
echo "Найден последний релиз: $LATEST_RELEASE"
mkdir -p build/libs
DOWNLOAD_URL="https://github.com/5ir1us/Hidify-telegram-bot-vpn/releases/download/$LATEST_RELEASE/$JAR_NAME"
curl -sSL -o "build/libs/$JAR_NAME" "$DOWNLOAD_URL"
if [ $? -ne 0 ]; then
  echo "❌ Ошибка при загрузке JAR. URL: $DOWNLOAD_URL"
  exit 1
fi

# Проверяем, что JAR-файл загружен
if [ ! -f "build/libs/$JAR_NAME" ]; then
  echo "❌ Ошибка: JAR-файл build/libs/$JAR_NAME не был загружен!"
  exit 1
fi

# 5. Генерируем скрипт запуска start.sh
cat > start.sh << EOF
#!/usr/bin/env bash
set -e

# Подгружаем переменные окружения из .env
if [ -f "\$(dirname "\$0")/.env" ]; then
  export \$(grep -v '^#' "\$(dirname "\$0")/.env" | xargs)
fi

# Запускаем JAR
java -jar "\$(dirname "\$0")/build/libs/$JAR_NAME"
EOF
chmod +x start.sh
echo "==> Сгенерирован start.sh"

# 6. Проверка на наличие systemd
if pidof systemd > /dev/null; then
  echo "==> Systemd обнаружен, настраиваем сервис $SERVICE_NAME"

  SERVICE_FILE="/etc/systemd/system/$SERVICE_NAME.service"

  echo "==> Создаём/обновляем systemd unit $SERVICE_FILE"
  sudo tee "$SERVICE_FILE" > /dev/null << EOF
[Unit]
Description=VPN Telegram Bot
After=network.target

[Service]
Type=simple
WorkingDirectory=$APP_DIR
ExecStart=$APP_DIR/start.sh
Restart=on-failure
User=$USER
EnvironmentFile=$APP_DIR/.env

[Install]
WantedBy=multi-user.target
EOF

  echo "==> Перезагружаем systemd и запускаем сервис $SERVICE_NAME"
  sudo systemctl daemon-reload
  sudo systemctl enable "$SERVICE_NAME"
  sudo systemctl restart "$SERVICE_NAME"

  echo "==> Проверяем статус сервиса $SERVICE_NAME"
  sudo systemctl status "$SERVICE_NAME" --no-pager
else
  echo "⚠️  Systemd не обнаружен. Пропускаем настройку systemd-сервиса."
  echo "👉 Вы можете запустить бота вручную:"
  echo "   cd $APP_DIR && ./start.sh"
fi

echo
echo "✅ Установка/обновление завершено!"
echo "   Логи: sudo journalctl -u $SERVICE_NAME -f"
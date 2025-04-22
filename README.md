# VPN Telegram Bot

Telegram бот для продажи VPN-подключений через интеграцию с Hiddify и оплатой через YooKassa.

![Снимок экрана 2025-04-22 в 21 12 07](https://github.com/user-attachments/assets/19d16ff7-49f5-4d08-8a98-3ccaef7c83ba)

 ![Снимок экрана 2025-04-22 в 21 12 28](https://github.com/user-attachments/assets/4d978b1f-8e4c-4ee6-84bc-fe035e3bbb19)

![Снимок экрана 2025-04-22 в 21 12 48](https://github.com/user-attachments/assets/5329d836-e71c-4f3e-bea8-7bf31b7cda2f)

/start - Главное меню
/buy - Купить подписку
/status - Проверить статус
/info - Информация об услугах

## Основные функции
- 🔒 Покупка VPN-доступа через Telegram
- 📆 Управление подпиской и проверка статуса
- 📲 Автоматическая генерация конфигураций
- 💳 Безопасные платежи через YooKassa

Главное меню содержит три кнопки:

Купить - переход к выбору тарифов
Статус - проверка текущей подписки
Информация - FAQ и контакты поддержки

## Настройка окружения
Создайте `.env` файл со следующими параметрами:

```env
# Telegram
TELEGRAM_BOT_TOKEN=ваш_токен_бота
TELEGRAM_WEBHOOK_URL=ваш_вебхук_урл

# Hiddify
HIDDIFY_API_URL=ваш_api_url
HIDDIFY_API_KEY=ваш_api_ключ

# YooKassa
YOO_KASSA_API_URL=ваш_url_api
SHOP_ID=ваш_shop_id
PAYMENT_API_KEY=ваш_платежный_ключ
```

Тарифные планы в фале константы

```kotlin
Параметр	Значение	Описание
MIN_PAYMENT_AMOUNT	 	Стоимость базового тарифа
MID_PAYMENT_AMOUNT	 	Стоимость стандартного тарифа
MAX_PAYMENT_AMOUNT	  Стоимость премиум тарифа
Лимит трафика	Значение	Соответствующий тариф
MIN_LIMIT_GB	100.00 GB	Базовый  
MID_LIMIT_GB	600.00 GB	Стандартный  
MAX_LIMIT_GB	1200.00 GB	Премиум  
```





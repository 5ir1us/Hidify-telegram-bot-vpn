# VPN Telegram Bot

Telegram бот для продажи VPN-подключений через интеграцию с Hiddify и оплатой через YooKassa.

<div align="center">
  <img src="https://github.com/user-attachments/assets/19d16ff7-49f5-4d08-8a98-3ccaef7c83ba" width="200" alt="Главное меню">
  <img src="https://github.com/user-attachments/assets/4d978b1f-8e4c-4ee6-84bc-fe035e3bbb19" width="200" alt="Выбор тарифа">
  <img src="https://github.com/user-attachments/assets/5329d836-e71c-4f3e-bea8-7bf31b7cda2f" width="200" alt="Оплата">
  <br>
  <img src="https://github.com/user-attachments/assets/8911cd98-e84f-4bf6-813d-ce9c9ee14cee" width="400" alt="Статус подписки">
  <img src="https://github.com/user-attachments/assets/6abf7ca9-90fa-417b-9f51-77a163145318" width="400" alt="Информация">
</div>
 

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
# Hiddify
HIDDIFY_API_URL=  домен который в панели настроили / желательно sub ссылку / не желательно cdn ссылку
HIDDIFY_API_KEY= api панели в вашей ссылке на панель 
HIDDIFY_PROXY_PATCH_ADMIN= в расширенных настройках панели 
HIDDIFY_PROXY_PATCH_CLIENT= в расширенных настройках панели 
HIDDIFY_PROXY_PATCH_UNIVERSAL= не используется в проекте это уневерсальный патч для удобства 
# YooKassa
PAYMENT_API_KEY=  api Ю-Кассы ваш 
PAYMENT_API_KEY_TEST= тестовый api в проете он зашит везде оригинальный сюда вставлять либо переделывать в data слое 
YOO_KASSA_API_URL = https://api.yookassa.ru/v3/   тут если ю касса не поменяет ничего трогать не надо 
SHOP_ID = тут ваш id магазина либо тестовый либо настаящий 
# Telegram
TELEGRAM_BOT_TOKEN = у Отца ботов попросите 
TELEGRAM_BOT_URL= https://t.me/vpn_Rabbit_bot ссылка на ваш бот  
TELEGRAM_INFO_URL =  это ссылка по которой пользователь переходит при нажатии на "Вся неоходимая информация" можно группу сделать 
TELEGRAM_OFFER_AGREEMENT = тут у нас ссылка на соглошение об окаазании услуг  в html формате 
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
## Быстрая установка и обновление

На чистом Ubuntu -сервере выполните _одну_ команду:

```bash
sudo bash <(curl -sSL https://raw.githubusercontent.com/5ir1us/Hidify-telegram-bot-vpn/main/install.sh)
```






# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя**: _api/auth/signin_
* входные данные: userName, password (Зашифрованные данные - строка); 
* выходные данные: accessToken, tokenType, role, user (Зашифрованные данные - строка)

**Регистрация пользователя**:_api/auth/signup_
* входные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка); 
* выходные данные: ok

**Получение информации пользователя**: _api/user/info_
* входные данные: id, publicKey (клиента)
* выходные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка)

**Получение открытого ключа**: _api/auth/public/key_, _api/user/public/key_, _api/admin/public/key_
* входные данные: нет;
* выходные данные: строка
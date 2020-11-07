# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя**: _api/auth/signin_
* входные данные: userName, password, publicKey; 
* выходные данные: message (accessToken, tokenType, role, user), publicKey 

**Регистрация пользователя**: _api/auth/signup_
* входные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка); 
* выходные данные: ok

**Получение информации пользователя**: _api/user/info_
* входные данные: id, publicKey (клиента);
* выходные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка)

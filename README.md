# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя**: _api/auth/signin_
* входные данные: userName, password; 
* выходные данные: accessToken, tokenType, role, user

**Регистрация пользователя**:_api/auth/signup_
* входные данные: userName, password, name, lastName, middleName; 
* выходные данные: ok
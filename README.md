# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя**: _api/auth/signin_
* входные данные: cipher (userName, password), publicKey; 
* выходные данные: message (accessToken, tokenType, role, user), publicKey 

**Добавление нового пользователя**: _api/admin/create/user_
* входные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка); 
* выходные данные: ok

**Получение информации пользователя**: _api/user/info_
* входные данные: id, publicKey (клиента);
* выходные данные: userName, password, name, lastName, middleName, role, department (Зашифрованные данные - строка)

**Получение открытого ключа**: _api/auth/public/key_
* входные данные: нет;
* выходные данные: строка

Подключение к БД:
-----------------------------------
1. Database -> + -> Data Source -> MySQL\
user: **root**\
password: **1234**
2. Запустить файл _createData_;
3. Запустить файл _createTable_;
4. Запустить файл _insertData_;\
Предупреждение! Не забудь добавить новую БД в настройки Data Source\
Database: _gazprom_data_
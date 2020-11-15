# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя (POST)**: _api/auth/signin_
* входные данные: cipher (userName, password), publicKey (клиента); 
* выходные данные: message (accessToken, tokenType, userId)

**Добавление нового пользователя (POST)**: _api/admin/create/user_
* входные данные: cipher (userName, password, name, lastName, middleName, role, department); 
* выходные данные: ok

**Получение информации пользователя (GET)**: _api/user/info_
* входные данные: id, publicKey (клиента);
* выходные данные: message(userName, name, lastName, middleName, role, department)

**Получение открытого ключа (GET)**: _api/auth/public/key_
* входные данные: нет;
* выходные данные: строка (publicKey сервера)

**Получение всех заявок (GET)**: _api/admin/all/request_
* входные данные: publicKey(клиента),
* выходные данные: message(список, где id, 
user(id, userName, name, lastName, middleName, role, department),system, validity, status, date)

**Создание новой заявки (POST)**: _api/user/add/request_
* входные данные: message(userId, system, validity, date[day, month, year])
* выходные данные: ok

**Получение заявок конкретного пользователя (GET)**: _api/user/request_
* входные данные: userId, publicKey (клиента)
* выходные данные: message(спиок - id, user[id, userName, name, lastName, middleName, role, department], 
system, validity, status, date[day, month, year])

**Изменить статус заявки (POST)**:
* входные данные: 
* выходные данные: 

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
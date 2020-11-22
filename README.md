# Информационная система для подачи заявок
Запросы:
-----------------------------------
**Авторизация пользователя (POST)**: _api/auth/signin_
* входные данные: cipher (userName, password), publicKey (клиента); 
* выходные данные: message (accessToken, tokenType, userId)

**Добавление нового пользователя (POST)**: _api/admin/create/user_
* входные данные: cipher (userName, password, name, lastName, middleName, roleId, departmentId); 
* выходные данные: ok

**Получение информации пользователя (GET)**: _api/user/info_
* входные данные: id, publicKey (клиента);
* выходные данные: message(userName, name, lastName, middleName, role, department)
* пример: api/user/info?id=1&publicKey=...

**Получение открытого ключа (GET)**: _api/auth/public/key_
* входные данные: нет;
* выходные данные: строка (publicKey сервера)

**Получение всех заявок (GET)**: _api/admin/all/request_
* входные данные: publicKey(клиента),
* выходные данные: message(список, где id, 
user(id, userName, name, lastName, middleName, role, department),system, validity, status, date)
* пример: api/admin/all/request?publicKey=...

**Создание новой заявки (POST)**: _api/user/add/request_
* входные данные: message(userId, system, validity, date[day, month, year])
* выходные данные: ok

**Получение заявок конкретного пользователя (GET)**: _api/user/request_
* входные данные: userId, publicKey (клиента)
* выходные данные: data(спиок - id, user[id, userName, name, lastName, middleName, role, department], 
system, validity, status, date[day, month, year])
* пример: api/user/request?userId=1publicKey=...

**Получить все отделы (GET)**: _api/user/unit_
* входные данные: publicKey (клиента)
* выходные данные: data(список - id, title)
* пример: api/user/unit?publicKey=...

**Получить все должности (GET)**: _api/admin/role_
* входные данные: publicKey (клиента)
* выходные данные: data(список - id, role)
* пример: api/admin/role?publicKey=...

**Получить все подразделения (GET)**: _api/user/department_
* входные данные: publicKey (клиента)
* выходные данные: data(список - id, title)
* пример: api/user/department?publicKey=...

**Изменить статус заявки (GET)**: _api/admin/update/status_
* входные данные: requestId, status*
* выходные данные: ok
* пример: api/admin/update/status?requestId=1&status=STATUS_ENABLE

_*status - нужно отправить одно из (STATUS_ENABLE, STATUS_DISABLE, STATUS_REFUSED, STATUS_SHIPPED)_

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
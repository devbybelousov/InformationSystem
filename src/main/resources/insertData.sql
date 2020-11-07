INSERT INTO gazprom_data.users (user_name, password, name, last_name, middle_name, role_id, department_id) VALUES ('admin', '$2a$10$GefgXaSmJB7PdWo1jwRRf.3cc5/RtTMqygMtqOL.uu9hBievORc8i', 'admin', 'admin', 'admin', null, null);
INSERT INTO gazprom_data.roles (name) VALUES ('ROLE_EMPLOYEE');
INSERT INTO gazprom_data.roles (name) VALUES ('ROLE_CHIEF');
INSERT INTO gazprom_data.roles (name) VALUES ('ROLE_ADMIN');
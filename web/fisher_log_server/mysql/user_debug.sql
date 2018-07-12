GRANT USAGE ON *.* TO fl_debug@localhost;
DROP USER fl_debug@localhost;
CREATE USER fl_debug@localhost IDENTIFIED BY 'password';
DROP DATABASE IF EXISTS fl_debug;
CREATE DATABASE fl_debug COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON fl_debug.* TO fl_debug@localhost IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

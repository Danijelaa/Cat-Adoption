DROP DATABASE IF EXISTS usvajanjeMaca;
CREATE DATABASE usvajanjeMaca DEFAULT CHARACTER SET utf8;

USE usvajanjeMaca;

GRANT ALL ON usvajanjeMaca.* TO 'daca'@'%' IDENTIFIED BY 'daca';

FLUSH PRIVILEGES;
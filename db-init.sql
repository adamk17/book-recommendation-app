-- db-init.sql
CREATE DATABASE books_db;
CREATE DATABASE userservice_db;
CREATE DATABASE reviewservice_db;
GRANT ALL PRIVILEGES ON DATABASE books_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE userservice_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE reviewservice_db TO postgres;

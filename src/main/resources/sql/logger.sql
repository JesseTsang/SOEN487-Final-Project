CREATE DATABASE IF NOT EXISTS soen487_w18_team07_logging;
USE soen487_w18_team07_logging;

CREATE TABLE IF NOT EXISTS Log(
	Id int NOT NULL AUTO_INCREMENT,
	log_class varchar(255),
        log_level varchar(10),
        log_message varchar(255),
        log_timestamp timestamp DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (Id));

CREATE TABLE IF NOT EXISTS Invoice(
	customerRef int NOT NULL AUTO_INCREMENT,
	amount_due double,
        payment_status boolean,
        last_update timestamp DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (CustomerRef));
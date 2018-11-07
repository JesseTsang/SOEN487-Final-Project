CREATE DATABASE IF NOT EXISTS soen487_w18_team07;
USE soen487_w18_team07;

CREATE TABLE IF NOT EXISTS Manufacturer(
	Id int NOT NULL AUTO_INCREMENT,
	Manufacturer_name varchar(255),
	PRIMARY KEY (Id));
	
CREATE TABLE IF NOT EXISTS Product(
	Id int NOT NULL AUTO_INCREMENT,
	Product_Type varchar(255),
	Manufacturer_id int,
	Model varchar(255),
	Unit_Price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY(Id),
        FOREIGN KEY(Manufacturer_id) REFERENCES Manufacturer(Id),
	CONSTRAINT UNIQUE(Product_Type, Model, Manufacturer_id));

CREATE TABLE IF NOT EXISTS Warehouse(
	Id int NOT NULL AUTO_INCREMENT,
	Location varchar(255) NOT NULL,
	PRIMARY KEY (Id));
	
CREATE TABLE IF NOT EXISTS Retailer(
        Id int NOT NULL AUTO_INCREMENT,
	Retailer_id int NOT NULL,
	Product_Id int,
	Quantity int NOT NULL,
	Warehouse_id int,
        PRIMARY KEY (Id),
	FOREIGN KEY (Product_Id) REFERENCES Product(Id),
        FOREIGN KEY (Warehouse_id) REFERENCES Warehouse(Id),
	CONSTRAINT UNIQUE (Retailer_id, Product_Id));

CREATE TABLE IF NOT EXISTS Inventory(
        Inventory_id int NOT NULL AUTO_INCREMENT,
	Product_Id int,
	Warehouse_Id int,
        Quantity int NOT NULL,
        PRIMARY KEY (Inventory_id),
	FOREIGN KEY(Product_Id) REFERENCES Product(Id),
        FOREIGN KEY(Warehouse_Id) REFERENCES Warehouse(Id),
	CONSTRAINT UNIQUE (Product_Id, Warehouse_Id));
	
CREATE TABLE IF NOT EXISTS Purchase_Order(
	Order_id int NOT NULL AUTO_INCREMENT,
	Warehouse_Id int,
	Manufacturer_Id int,
	Product_Id int,
	Customer_Ref int,
	Quantity int NOT NULL,
	Unit_Price decimal(10,2) NOT NULL,
	Order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(Warehouse_Id) REFERENCES Warehouse(Id),
        FOREIGN KEY (Manufacturer_Id) REFERENCES Manufacturer(Id),
        FOREIGN KEY(Product_Id) REFERENCES Product(Id),
	PRIMARY KEY(Order_id));
CREATE TABLE Customer (
	id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(50),
	phone VARCHAR(15),
	PRIMARY KEY (id)
);

CREATE TABLE Purchase (
	id INT NOT NULL AUTO_INCREMENT,
	purchase_date DATE,
	customer_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (customer_id) REFERENCES Customer (id)
);

CREATE TABLE Supplier (
	id INT NOT NULL AUTO_INCREMENT,
	supplier_name VARCHAR(50),
	PRIMARY KEY (id)
);

CREATE TABLE Product (
	id INT NOT NULL AUTO_INCREMENT,
	product_name VARCHAR(50),
	price DECIMAL(7,2),
	supplier_id INT,
	stock INT,
	PRIMARY KEY (id),
	FOREIGN KEY (supplier_id) REFERENCES Supplier (id)
);

CREATE TABLE PurchasedProduct (
	purchase_id INT,
	product_id INT,
	amount INT,
	PRIMARY KEY (purchase_id, product_id),
	FOREIGN KEY (purchase_id) REFERENCES Purchase (id),
	FOREIGN KEY (product_id) REFERENCES Product (id)
);

CREATE TABLE ProductOrder (
	id INT NOT NULL AUTO_INCREMENT,
	order_date DATE,
	supplier_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (supplier_id) REFERENCES Supplier (id)
);


CREATE TABLE OrderedProduct (
	order_id INT,
	product_id INT,
	amount INT,
	PRIMARY KEY (order_id, product_id),
	FOREIGN KEY (order_id) REFERENCES ProductOrder (id),
	FOREIGN KEY (product_id) REFERENCES Product (id)
);

CREATE TABLE RentalItemType (
	id INT NOT NULL AUTO_INCREMENT,
	type_name VARCHAR(50),
	price_per_day DECIMAL(7,2),
	PRIMARY KEY (id)
);

CREATE TABLE RentalItem (
	id INT NOT NULL AUTO_INCREMENT,
	type_id INT,
	FOREIGN KEY (type_id) REFERENCES RentalItemType (id),
	PRIMARY KEY (id)
);

CREATE TABLE PurchasedRental(
	purchase_id INT,
	rental_item_id INT,
	pickup_date DATE,
	return_date DATE,
	PRIMARY KEY (purchase_id, rental_item_id),
	FOREIGN KEY (purchase_id) REFERENCES Purchase (id),
	FOREIGN KEY (rental_item_id) REFERENCES RentalItem (id)
);

CREATE TABLE Service (
	id INT NOT NULL AUTO_INCREMENT,
	service_name VARCHAR(50),
	price DECIMAL(7,2),
	PRIMARY KEY (id)
);

CREATE TABLE WorkOrder (
	purchase_id INT,
	service_id INT,
	due_date DATE,
	service_notes VARCHAR(300),
	PRIMARY KEY (purchase_id, service_id),
	FOREIGN KEY (purchase_id) REFERENCES Purchase (id),
	FOREIGN KEY (service_id) REFERENCES Service (id)
);
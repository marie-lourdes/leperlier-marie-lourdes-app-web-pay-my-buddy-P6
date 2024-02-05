use paymybuddy_test;

/*--------SHEMA BDD--------*/

CREATE TABLE user_app(
id int NOT NULL AUTO_INCREMENT  PRIMARY KEY,
email VARCHAR(150) NOT NULL,
first_name VARCHAR(100) ,
last_name VARCHAR(100) ,
password VARCHAR(100) ,
role VARCHAR(10) 
);

CREATE TABLE contacts (
 id_contact int NOT NULL  ,
user_id int NOT NULL,
  PRIMARY KEY ( id_contact,user_id),
  FOREIGN KEY ( id_contact) REFERENCES user_app(id) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (user_id ) REFERENCES user_app(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE account(
account_id BIGINT NOT NULL AUTO_INCREMENT,
user_id int NOT NULL,
balance DECIMAL NOT NULL,
account_type VARCHAR(15) NOT NULL,
creation DATETIME NOT NULL,
FOREIGN KEY(user_id) REFERENCES user_app(id) ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY(account_id,user_id )
);

ALTER TABLE account AUTO_INCREMENT = 76001;

CREATE TABLE transaction(
id_transaction BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
transaction_date_time DATETIME DEFAULT NOW() ,
beneficiary_user_id int  DEFAULT 0 ,
credit_user_id int DEFAULT 0,
description VARCHAR(250) NOT NULL,
amount int NOT NULL,
transaction_fees FLOAT DEFAULT 0.0 ,
FOREIGN KEY(credit_user_id ) REFERENCES user_app(id)  ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(beneficiary_user_id  ) REFERENCES user_app(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

ALTER TABLE transaction AUTO_INCREMENT = 1001;




CREATE TABLE user_app(
email VARCHAR(500) NOT NULL UNIQUE PRIMARY KEY,
name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
user_contact_id VARCHAR(500) UNIQUE 
FOREIGN KEY(user_contact_id ) REFERENCES user_app(email) ON DELETE SET NULL ON UPDATE CASCADE,
(email)
);

CREATE TABLE role(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
role_name VARCHAR(10) UNIQUE NOT NULL
);

INSERT INTO role(role_name) VALUES
("ROLE_USER"), ("ROLE_ADMIN");

CREATE TABLE user_app_role(
user_id VARCHAR(500) NOT NULL,
role_id INT NOT NULL,
FOREIGN KEY(user_id) REFERENCES user_app(email) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(role_id ) REFERENCES role(id ) ON DELETE RESTRICT ON UPDATE CASCADE,
PRIMARY KEY(user_id,role_id)
);

CREATE TABLE account(
id BIGINT NOT NULL AUTO_INCREMENT DEFAULT 760000,
user_id VARCHAR(500) NOT NULL,
soldes DECIMAL NOT NULL,
TYPE VARCHAR(100),
FOREIGN KEY(user_id) REFERENCES user_app(email) ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY(id,user_id )
);

CREATE TABLE transaction(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY DEFAULT 00000,
user_account_id  BIGINT NOT NULL,
credit_user_id VARCHAR(500) NOT NULL,
beneficiary_count_id BIGINT NOT NULL,
beneficiary_user_id VARCHAR(500) NOT NULL,
transaction_date DATE NOT NULL,
transaction_time TIME NOT NULL,
amount DECIMAL NOT NULL,
transaction_fees TIME NOT NULL,
FOREIGN KEY(user_account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(credit_user_id) REFERENCES account(user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(beneficiary_count_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(beneficiary_user_id) REFERENCES account(user_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

/* DEFAULT VALUES FOR PAYMYBUDDY_TEST DB */

INSERT INTO user_app(email ,first_name ,last_name,password ) VALUES
("testuser1@gmail.com", "firstnameuser1", "lastnameuser1", "codesablage");

INSERT INTO user_app(email ,first_name ,last_name,password,user_contact_id ) VALUES
("testuser2@gmail.com", "firstnameuser1", "lastnameuser1", "codesablage","testuser1@gmail.com");

INSERT INTO user_app_role(user_id,role_id) VALUES
("testuser1@gmail.com",1),
("testuser1@gmail.com",2),
("testuser2@gmail.com",1);
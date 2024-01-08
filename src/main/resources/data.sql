CREATE TABLE user_app(
email VARCHAR(500) NOT NULL UNIQUE PRIMARY KEY,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
role VARCHAR(10),
user_contact_id VARCHAR(500) , 
FOREIGN KEY(user_contact_id ) REFERENCES user_app(email) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE account(
id BIGINT NOT NULL AUTO_INCREMENT,
user_id VARCHAR(500) UNIQUE  NOT NULL,
soldes DECIMAL NOT NULL,
TYPE VARCHAR(100),
FOREIGN KEY(user_id) REFERENCES user_app(email) ON DELETE CASCADE ON UPDATE CASCADE,
PRIMARY KEY(id,user_id )
);

ALTER TABLE account AUTO_INCREMENT = 76001;

CREATE TABLE transaction(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
credit_account_id  BIGINT NOT NULL,
credit_user_id VARCHAR(500) NOT NULL,
beneficiary_count_id BIGINT NOT NULL,
beneficiary_user_id VARCHAR(500) NOT NULL,
transaction_date_time DATETIME NOT NULL,
amount DECIMAL NOT NULL,
transaction_fees FLOAT NOT NULL,
FOREIGN KEY(credit_account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(credit_user_id) REFERENCES account(user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(beneficiary_count_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
FOREIGN KEY(beneficiary_user_id) REFERENCES account(user_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

ALTER TABLE transaction AUTO_INCREMENT = 1001;

/* DEFAULT VALUES FOR PAYMYBUDDY_TEST DB */

INSERT INTO user_app(email, first_name, last_name, password , role) VALUES
("testuser1@gmail.com", "firstnameuser1", "lastnameuser1", "$2a$10$EGu79kZqYg4X8Ol99UCsBONWuUjL0M1ZgbjcUKBzR2VFeo50tIRbK","ADMIN");

INSERT INTO user_app(email, first_name, last_name, password, user_contact_id,role) VALUES
("testuser2@gmail.com", "firstnameuser1", "lastnameuser1", "$2a$10$loqM3iAxV3CxJJydEvsDK.Zq3H33VdV1vy5PmQsf7wFsTJRzWZeCG","testuser1@gmail.com","USER");

INSERT INTO account(user_id, soldes,TYPE ) VALUES
("testuser1@gmail.com", 45075.00, "buddy account");

INSERT INTO account(user_id, soldes,TYPE ) VALUES
("testuser2@gmail.com", 9825.00, "buddy account");

INSERT INTO transaction(credit_account_id, credit_user_id, beneficiary_count_id , beneficiary_user_id,transaction_date_time, amount, transaction_fees) VALUES
(76001,"testuser1@gmail.com", 76002,"testuser2@gmail.com", NOW(), 2388.00, 4.2);

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	user_id INT NOT NULL,
	PRIMARY KEY (user_id),
	
	firstname varchar(255) NOT NULL,
	lastname varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	fk_wallet_id INT,
	
	UNIQUE (email)
);



DROP TABLE IF EXISTS wallet;
CREATE TABLE wallet(
	wallet_id INT NOT NULL,
	PRIMARY KEY (wallet_id),
	
	balance FLOAT,
	fk_user_id INT,
	fk_bank_account_id INT,
		
	CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES user(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

ALTER TABLE user ADD CONSTRAINT fk_wallet_id FOREIGN KEY (fk_wallet_id) REFERENCES wallet(wallet_id) ON DELETE CASCADE ON UPDATE NO ACTION;


DROP TABLE IF EXISTS bank_account;
CREATE TABLE bank_account(
	bank_account_id INT NOT NULL,
	PRIMARY KEY (bank_account_id),
	
	iban VARCHAR(34),
	bic VARCHAR(11),
	fk_wallet_id INT NOT NULL
	

);

ALTER TABLE wallet ADD CONSTRAINT fk_bank_account_id FOREIGN KEY (fk_bank_account_id) REFERENCES bank_account(bank_account_id) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE bank_account ADD CONSTRAINT fk_wallet_id FOREIGN KEY (fk_wallet_id) REFERENCES wallet(wallet_id) ON DELETE NO ACTION ON UPDATE NO ACTION;


DROP TABLE IF EXISTS money_transaction;
CREATE TABLE money_transaction(
	transaction_id int NOT NULL,
	PRIMARY KEY (transaction_id),
	
 	wallet_creditor_id int,
	wallet_debtor_id int,
	taxe FLOAT NOT NULL,
	transaction_date DATE NOT NULL,
	amount FLOAT NOT NULL,
	is_bank_transaction BOOLEAN NOT NULL,
	

	CONSTRAINT wallet_creditor_id FOREIGN KEY (wallet_creditor_id) REFERENCES wallet (wallet_id) ON DELETE SET NULL ON UPDATE NO ACTION,
	CONSTRAINT wallet_debtor_id FOREIGN KEY (wallet_debtor_id) REFERENCES wallet (wallet_id) ON DELETE  SET NULL ON UPDATE NO ACTION
);



DROP TABLE IF EXISTS contact;
CREATE TABLE contact(
  fk_user_id int NOT NULL,
  fk_user_contact_id int NOT NULL,
  PRIMARY KEY (fk_user_id,fk_user_contact_id)

);
ALTER TABLE contact ADD CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES user(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE contact ADD CONSTRAINT fk_user_contact_id FOREIGN KEY (fk_user_contact_id) REFERENCES user(user_id) ON DELETE NO ACTION ON UPDATE NO ACTION;
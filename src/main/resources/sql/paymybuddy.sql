DROP DATABASE pay_my_buddy;
CREATE DATABASE pay_my_buddy;
USE pay_my_buddy;

CREATE TABLE user
(
    user_id      INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    firstname    VARCHAR(255)                       NOT NULL,
    lastname     VARCHAR(255)                       NOT NULL,
    email        VARCHAR(255)                       NOT NULL UNIQUE,
    password     VARCHAR(255)                       NOT NULL,
    fk_wallet_id INTEGER
);

CREATE TABLE wallet
(
    wallet_id          INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    balance            FLOAT                              NOT NULL,
    fk_user_id         INTEGER,
    fk_bank_account_id INTEGER,
    FOREIGN KEY (fk_user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE user
    ADD FOREIGN KEY (fk_wallet_id) REFERENCES wallet (wallet_id) ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE TABLE bank_account
(
    bank_account_id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    iban            VARCHAR(255)                       NOT NULL,
    bic             VARCHAR(255)                       NOT NULL,
    fk_wallet_id    INTEGER                            NOT NULL,
    FOREIGN KEY (fk_wallet_id) REFERENCES wallet (wallet_id)
);

ALTER TABLE wallet
    ADD FOREIGN KEY (fk_bank_account_id) REFERENCES bank_account (bank_account_id) ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE TABLE contact
(
    user_contact_id    INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    fk_user_id         INTEGER,
    fk_user_contact_id INTEGER,
    FOREIGN KEY (fk_user_id) REFERENCES user (user_id) ON DELETE SET NULL ON UPDATE NO ACTION,
    FOREIGN KEY (fk_user_contact_id) REFERENCES user (user_id) ON DELETE SET NULL ON UPDATE NO ACTION
);

CREATE TABLE money_transaction
(
    transaction_id      INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    wallet_creditor_id  INTEGER,
    wallet_debtor_id    INTEGER,
    taxe                FLOAT(10, 2)                       Not NULL,
    transaction_date    DATE                               NOT NULL,
    amount              FLOAT(10, 2)                       Not NULL,
    is_bank_transaction BOOLEAN,
    description         VARCHAR(255)                       NOT NULL,
    FOREIGN KEY (wallet_creditor_id) REFERENCES wallet (wallet_id) ON DELETE SET NULL ON UPDATE NO ACTION,
    FOREIGN KEY (wallet_debtor_id) REFERENCES wallet (wallet_id) ON DELETE SET NULL ON UPDATE NO ACTION
);

-- mdp : password
INSERT INTO user (firstname, lastname, email, password, user_id)
VALUES ('john', 'snow', 'js@mail.fr', '$2a$10$6nTIkoHQqEGXAx8DfdM/3.KpuFDFj/RnOoMD4RJkeGaqDnucfP3Fe', 1);

-- mdp : password
INSERT INTO user (firstname, lastname, email, password, user_id)
VALUES ('dark', 'vador', 'dv@mail.fr', '$2a$10$6nTIkoHQqEGXAx8DfdM/3.KpuFDFj/RnOoMD4RJkeGaqDnucfP3Fe', 2);

INSERT INTO wallet (balance, fk_user_id, wallet_id)
VALUES (0, 1, 1);

INSERT INTO wallet (balance, fk_user_id, wallet_id)
VALUES (0, 2, 2);

UPDATE user
SET fk_wallet_id = 1
WHERE user_id = 1;

UPDATE user
SET fk_wallet_id = 2
WHERE user_id = 2;
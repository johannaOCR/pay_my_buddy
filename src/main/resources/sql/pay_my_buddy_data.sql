use pay_my_buddy;
INSERT INTO user(user_id, firstname, lastname, email,password) VALUES
                        (1,'johanna','tristan','johanna.tristan@gmail.com','chutchut'),
                        (2,'theo','pointet','theo.pointet@gmail.com','theochut'),
                        (3,'manon','pointet','manon.pointet@gmail.com','manonchut')
;
INSERT INTO wallet(wallet_id, balance, fk_user_id, fk_bank_account_id) VALUES
                          (1,30.6,1,1),
                          (2,152.2,2,2),
                          (3,658.02,3,3)
;
INSERT INTO bank_account(bank_account_id, iban, bic, fk_wallet_id) VALUES
                                (1,'125445488','45654548JML',1),
                                (2,'12544THEO','456545THEO',2),
                                (3,'125MANON','4565MANON',3)
;
INSERT INTO contact(fk_user_id, fk_user_contact_id) VALUES -- Johanna a manon et théo en contact, théo a manon, manon a théo
                           (1,2),
                           (1,3),
                           (2,3),
                           (3,2)
;
INSERT INTO money_transaction(transaction_id, wallet_creditor_id, wallet_debtor_id, taxe, transaction_date, amount, is_bank_transaction) VALUES
                                     (1,1,2,0.5,'2023-02-24',50,false), -- versement de johanna vers théo de 50 €
                                     (2,1,3,0.5,'2023-02-24',80,false), -- versement de johanna vers manon de 80€
                                     (3,1,1,0.5,'2023-02-24',10,true) -- versement de johanna vers son compte de 10€
;

INSERT INTO user(fk_wallet_id) VALUES
                                                                   (1),
                                                                   (2),
                                                                   (3)
;
UPDATE user
SET fk_wallet_id = 1
WHERE user_id = 1;

UPDATE user
SET fk_wallet_id = 2
WHERE user_id = 2;

UPDATE user
SET fk_wallet_id = 3
WHERE user_id = 3;

/*tariffs*/
INSERT INTO tariffs (name, cost) VALUES ('cheap', 100);
INSERT INTO tariffs (name, cost) VALUES ('little', 95);
INSERT INTO tariffs (name, cost) VALUES ('xxl', 80);
INSERT INTO tariffs (name, cost) VALUES ('big', 75);
INSERT INTO tariffs (name, cost) VALUES ('expensive', 70);
INSERT INTO tariffs (name, cost) VALUES ('simple', 65);
INSERT INTO tariffs (name, cost) VALUES ('elementary', 60);
INSERT INTO tariffs (name, cost) VALUES ('mean', 55);
INSERT INTO tariffs (name, cost) VALUES ('short', 50);
INSERT INTO tariffs (name, cost) VALUES ('long', 45);
INSERT INTO tariffs (name, cost) VALUES ('medium', 40);
INSERT INTO tariffs (name, cost) VALUES ('quiet', 35);
INSERT INTO tariffs (name, cost) VALUES ('soft', 30);
INSERT INTO tariffs (name, cost) VALUES ('hard', 25);
INSERT INTO tariffs (name, cost) VALUES ('slow', 20);

/*Users*/
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Jon', 'Smith', 'August', 'cheap', 100, '375291111111', 1);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Bill', 'Van', 'Andreevich', 'little', 95, '375292222222', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Max', 'Gog', 'Petrovich', 'xxl', 80, '375293333333', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Igor', 'Kuzmin', 'Sergeevich', 'big', 75.2, '375294444444', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Barbara', 'Straizand', 'Adamovna', 'expensive', 69, '375295555555', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Tom', 'Gray', 'Maximovich', 'simple', 65, '375297539146', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Jarry', 'Cherry', 'Pickovich', 'elementary', 60, '375331592383', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Sam', 'Lol', 'Kekavich', 'mean', 55, '375294532648', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Garry', 'Snail', 'Bobovich', 'short', 50, '375334261535', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Bob', 'Biggest', 'Ivanovich', 'long', 45, '375297598486', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Jack', 'Master', 'Popovich', 'medium', 40, '375339535621', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Marta', 'Stuart', 'Little', 'quiet', 35, '375298264686', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Andrey', 'Kuzmin', 'Yurevich', 'soft', 30, '375297412596', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Mike', 'Mazovski', 'Olegovich', 'hard', 25, '375331238283', 0);
INSERT INTO users (firstName, lastName, secondName, tariff_name, balance, phoneNumber, role) VALUES ('Clark', 'Kent', 'Vladimirovich', 'slow', 25, '375294512668', 0);

/*Statistics*/
INSERT INTO statistics (name, description) VALUES ('number of received calls', '');
INSERT INTO statistics (name, description) VALUES ('number of successful normalization of numbers', '');
INSERT INTO statistics (name, description) VALUES ('number of failed normalization of numbers', '');
INSERT INTO statistics (name, description) VALUES ('number of calls successfully processed by the service', '');
INSERT INTO statistics (name, description) VALUES ('number of calls unsuccessfully processed by the service', '');


/*Configurations*/
INSERT INTO configurations (name, description) VALUES ('timer', '10000');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 1','^\\+37529([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 2','^\\+37533([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 3','^\\+37544([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 4','^8029([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 5','^8033([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 6','^8044([0-9]{7})$');
INSERT INTO configurations (name, description) VALUES ('normalization pattern 7','^[0-9]{7}$');

/*Autorization*/
INSERT INTO authorization (id, password, phoneNumber) VALUES (1, '12345678', '375291111111');
INSERT INTO authorization (id, password, phoneNumber) VALUES (2, '11111111', '375292222222');
INSERT INTO authorization (id, password, phoneNumber) VALUES (3, '11111111', '375293333333');
INSERT INTO authorization (id, password, phoneNumber) VALUES (4, '11111111', '375294444444');


/*Black List*/
INSERT INTO blacklist (id, phoneNumber) VALUES (1, '375292222222');
INSERT INTO blacklist (id, phoneNumber) VALUES (2, '375293333333');
INSERT INTO blacklist (id, phoneNumber) VALUES (3, '375294444444');

/*Black_list Users*/
INSERT INTO blacklist_user (blacklist_id, user_id_in_blacklist) VALUES  (1,'375293333333');
INSERT INTO blacklist_user (blacklist_id, user_id_in_blacklist) VALUES  (1,'375294444444');





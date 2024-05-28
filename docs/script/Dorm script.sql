DROP DATABASE IF EXISTS gruppe_7;
CREATE DATABASE gruppe_7;
USE gruppe_7;

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';
GRANT SELECT,  INSERT,  UPDATE,  DELETE ON * . * TO 'admin'@'localhost';
FLUSH PRIVILEGES;


CREATE TABLE profile(
id int PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
admin BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE room (
id int PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL,
bio VARCHAR(255)
);

CREATE TABLE room_profile (
    room_id INT,
    profile_id INT,
	admin BOOLEAN NOT NULL DEFAULT 0,
    PRIMARY KEY (room_id, profile_id),
    FOREIGN KEY (room_id) REFERENCES room(id), -- lav constraints
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE post (
id int PRIMARY KEY AUTO_INCREMENT,
profile_id int NOT NULL,
room_id int NOT NULL,
text VARCHAR(300) NOT NULL,
creation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
likes INT DEFAULT 0,
FOREIGN KEY (profile_id) REFERENCES profile(id),
FOREIGN KEY (room_id) REFERENCES room(id)
);

CREATE TABLE comment (
id int PRIMARY KEY AUTO_INCREMENT,
post_id INT NOT NULL,
profile_id INT NOT NULL,
text VARCHAR(255) NOT NULL,
FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE post_likes (
    post_id INT,
    profile_id INT,
    PRIMARY KEY (post_id, profile_id),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);

INSERT INTO room (name) VALUES ('1. sal');
INSERT INTO room (name) VALUES ('2. sal');
INSERT INTO room (name) VALUES ('3. sal');
INSERT INTO room (name) VALUES ('4. sal');
INSERT INTO room (name) VALUES ('5. sal');
INSERT INTO room (name) VALUES ('6. sal');
INSERT INTO profile (first_name, last_name, email, password, admin) VALUES ('Admin', 'User', 'admin@user.com', '123', '1');
INSERT INTO profile (first_name, last_name, email, password, admin) VALUES ('john', 'Doe', 'johndoe@gmail.com', 'password', '0');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('1', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('2', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('3', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('4', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('5', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('6', '1', '1');
INSERT INTO room_profile (room_id, profile_id, admin) VALUES ('1', '2', '0');

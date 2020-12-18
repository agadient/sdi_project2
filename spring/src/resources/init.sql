CREATE TABLE locations (location_id serial, name text NOT NULL, PRIMARY KEY (location_id));
INSERT INTO locations(name) VALUES ('Kirtland');
INSERT INTO locations(name) VALUES ('Edwards');
INSERT INTO locations(name) VALUES ('Scranton');
INSERT INTO locations(name) VALUES ('Moscow');


CREATE TABLE users (user_id serial, username varchar(255) UNIQUE NOT NULL, password_hash text NOT NULL,
salt text, age int NOT NULL, location_id BIGINT UNSIGNED NOT NULL, PRIMARY KEY(user_id), FOREIGN KEY (location_id) REFERENCES locations(location_id));

INSERT INTO users(username, password_hash, salt, age, location_id) VALUES ('fighterPilotXOXO', 'asdf', 'asdf', 28, 1);
INSERT INTO users(username, password_hash, salt, age, location_id) VALUES ('crustyColonel', 'asdf', 'asdf', 55, 2);
INSERT INTO users(username, password_hash, salt, age, location_id) VALUES ('MichaelScarn', 'asdf', 'asdf', 45, 3);
INSERT INTO users(username, password_hash, salt, age, location_id) VALUES ('KarinaNotASpie', 'asdf', 'asdf', 20, 4);


CREATE TABLE hobbies (hobby_id serial, name text NOT NULL, PRIMARY KEY(hobby_id));

INSERT INTO hobbies(name) VALUES ('Flying');
INSERT INTO hobbies(name) VALUES ('Yelling');
INSERT INTO hobbies(name) VALUES ('Improv');
INSERT INTO hobbies(name) VALUES ('Spying');

CREATE TABLE conversations(conversation_id serial, chat_log text NOT NULL, PRIMARY KEY(conversation_id));

INSERT INTO conversations(chat_log) VALUES ('KarinaNotASpie: Hello handsome airmen. I like long walks on the beach and electronic schematics for the F-15E Strike Eagle. Wanna hang out?\nfighterPilotXOXO: Hey baby sure thing ever rode in an A-10 before?\n');
INSERT INTO conversations(chat_log) VALUES ('crustyColonel: Better cut your hair airmen!\n MichaelScarn: Yeesh\n');


CREATE TABLE user_conversations (user_conversation_id serial, user_id BIGINT UNSIGNED NOT NULL, conversation_id BIGINT UNSIGNED NOT NULL,
PRIMARY KEY(user_conversation_id), FOREIGN KEY (conversation_id) REFERENCES conversations(conversation_id),
FOREIGN KEY (user_id) REFERENCES users(user_id));

INSERT INTO user_conversations (user_id, conversation_id) VALUES (1, 1);
INSERT INTO user_conversations (user_id, conversation_id) VALUES (4, 1);
INSERT INTO user_conversations (user_id, conversation_id) VALUES (2, 2);
INSERT INTO user_conversations (user_id, conversation_id) VALUES (3, 2);

CREATE TABLE user_hobbies(user_hobby_id serial, hobby_id BIGINT UNSIGNED NOT NULL, user_id BIGINT UNSIGNED NOT NULL,
PRIMARY KEY (user_hobby_id), FOREIGN KEY (hobby_id) REFERENCES hobbies(hobby_id),
FOREIGN KEY (user_id) REFERENCES users(user_id));

INSERT INTO user_hobbies (hobby_id, user_id) VALUES (1, 1);
INSERT INTO user_hobbies (hobby_id, user_id) VALUES (2, 2);
INSERT INTO user_hobbies (hobby_id, user_id) VALUES (3, 3);
INSERT INTO user_hobbies (hobby_id, user_id) VALUES (4, 4);
CREATE TABLE Locations (locationID serial, name text NOT NULL, PRIMARY KEY (locationID));
INSERT INTO Locations(name) VALUES ('Kirtland');
INSERT INTO Locations(name) VALUES ('Edwards');
INSERT INTO Locations(name) VALUES ('Scranton');
INSERT INTO Locations(name) VALUES ('Moscow');


CREATE TABLE Users (userID serial, username varchar(255) UNIQUE NOT NULL, passwordHash text NOT NULL,
salt text, age int NOT NULL, locationID BIGINT UNSIGNED NOT NULL, PRIMARY KEY(userID), FOREIGN KEY (locationID) REFERENCES Locations(locationID));

INSERT INTO Users(username, passwordHash, salt, age, locationID) VALUES ('fighterPilotXOXO', 'asdf', 'asdf', 28, 1);
INSERT INTO Users(username, passwordHash, salt, age, locationID) VALUES ('crustyColonel', 'asdf', 'asdf', 55, 2);
INSERT INTO Users(username, passwordHash, salt, age, locationID) VALUES ('MichaelScarn', 'asdf', 'asdf', 45, 3);
INSERT INTO Users(username, passwordHash, salt, age, locationID) VALUES ('KarinaNotASpie', 'asdf', 'asdf', 20, 4);


CREATE TABLE Hobbies (hobbyID serial, name text NOT NULL, PRIMARY KEY(hobbyID));

INSERT INTO Hobbies(name) VALUES ('Flying');
INSERT INTO Hobbies(name) VALUES ('Yelling');
INSERT INTO Hobbies(name) VALUES ('Improv');
INSERT INTO Hobbies(name) VALUES ('Spying');

CREATE TABLE Conversations(conversationID serial, chatLog text NOT NULL, PRIMARY KEY(conversationID));

INSERT INTO Conversations(chatLog) VALUES ('KarinaNotASpie: Hello handsome airmen. I like long walks on the beach and electronic schematics for the F-15E Strike Eagle. Wanna hang out?\r\n\r\nfighterPilotXOXO: Hey baby sure thing ever rode in an A-10 before?');
INSERT INTO Conversations(chatLog) VALUES ('crustyColonel: Better cut your hair airmen!\r\n\r\n MichaelScarn: Yeesh');


CREATE TABLE UserConversations (userConversationID serial, userID BIGINT UNSIGNED NOT NULL, conversationID BIGINT UNSIGNED NOT NULL,
PRIMARY KEY(userConversationID), FOREIGN KEY (conversationID) REFERENCES Conversations(conversationID),
FOREIGN KEY (userID) REFERENCES Users(userID));

INSERT INTO UserConversations (userID, conversationID) VALUES (1, 1);
INSERT INTO UserConversations (userID, conversationID) VALUES (4, 1);
INSERT INTO UserConversations (userID, conversationID) VALUES (2, 2);
INSERT INTO UserConversations (userID, conversationID) VALUES (3, 2);

CREATE TABLE UserHobbies(userHobbyID serial, hobbyID BIGINT UNSIGNED NOT NULL, userID BIGINT UNSIGNED NOT NULL,
PRIMARY KEY (userHobbyID), FOREIGN KEY (hobbyID) REFERENCES Hobbies(hobbyID),
FOREIGN KEY (userID) REFERENCES Users(userID));

INSERT INTO UserHobbies (hobbyID, userID) VALUES (1, 1);
INSERT INTO UserHobbies (hobbyID, userID) VALUES (2, 2);
INSERT INTO UserHobbies (hobbyID, userID) VALUES (3, 3);
INSERT INTO UserHobbies (hobbyID, userID) VALUES (4, 4);

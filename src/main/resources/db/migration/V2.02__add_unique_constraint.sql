ALTER TABLE users CHANGE email email VARCHAR(255) NOT NULL UNIQUE;
ALTER TABLE users CHANGE username username VARCHAR(60) NOT NULL UNIQUE;


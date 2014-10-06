INSERT INTO users(username,password,enabled)
VALUES ('admin','$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.', TRUE);
INSERT INTO users(username,password,enabled)
VALUES ('user','$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.', TRUE);
 
INSERT INTO user_roles (username, ROLE)
VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles (username, ROLE)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, ROLE)
VALUES ('user', 'ROLE_USER');
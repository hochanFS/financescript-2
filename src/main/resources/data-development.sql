-- Data to be loaded for "development" environment

INSERT INTO `role` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `role` (`name`) VALUES ('ROLE_MEMBER');

INSERT INTO `user` (`creation_time`, `username`, `email`, `password`)
    VALUES (TO_DATE('27/12/2019', 'DD/MM/YYYY'), 'ADMIN', 'hochan.lee@financescript.com',
    '$2a$10$3ua90fuhMEuC7TcTnov2muV9U6n0dUdUmMIDyRMvqrrQQGBeVI4ga');
-- password = PassWord!123
-- The password will be only valid for development environment
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1)


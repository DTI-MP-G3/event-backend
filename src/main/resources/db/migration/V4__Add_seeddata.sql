
INSERT INTO montrack_be.users (password, name, email, pin)
VALUES
('$2a$12$U9OtiQjkO0Eo9b2DWeC7CuSKFsKkRvq2XYIBGaEngMma1yRRNmyKK', 'John Doe', 'john@example.com', 123456),
('$2a$12$U9OtiQjkO0Eo9b2DWeC7CuSKFsKkRvq2XYIBGaEngMma1yRRNmyKK', 'Jane Smith', 'jane@example.com', 654321);



INSERT INTO montrack_be.user_roles (user_id, role_id)
VALUES
  ((SELECT id FROM montrack_be.users WHERE name = 'John Doe'),
   (SELECT role_id FROM montrack_be.roles WHERE name = 'ADMIN')),

  ((SELECT id FROM montrack_be.users WHERE name = 'Jane Smith'),
   (SELECT role_id FROM montrack_be.roles WHERE name = 'USER'));
;

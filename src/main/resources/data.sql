INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('admin@email.com', '$2a$10$CLPDxx7v/AqbyW8uwXV3TetgQ8W3d6WRngInrb4LMoPdBIAeZRJAq', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('guest@email.com', '$2a$10$AAfEe2djMMyR1usWRdFgX.CwQgDFQShujAbF2kewC5wmayUnP8J0e', 1);
 
INSERT INTO sec_role (roleName)
VALUES ('ROLE_ADMIN'), ('ROLE_MEMBER'), ('ROLE_GUEST');

INSERT INTO user_role (userId, roleId)
VALUES (1, 1), (2, 3);

INSERT INTO contact (name, phone, address, email, role) VALUES
('Lacey-Mai Maxwell', 6471001000, '1000 Main Road', 'user1000@email.com', 'Admin'),
('Vicki Mansell', 6471001001, '1001 Main Road', 'user1001@email.com', 'Admin'),
('Terrence Bone', 6471001002, '1002 Main Road', 'user1002@email.com', 'Admin'),
('Emmanuella OConnor', 6471001003, '1003 Main Road', 'user1003@email.com', 'Admin'),
('Isabelle Salinas', 6471001004, '1004 Main Road', 'user1004@email.com', 'Member'),
('Aminah Stevenson', 6471001005, '1005 Main Road', 'user1005@email.com', 'Member'),
('Kaitlan Mcgregor', 6471001006, '1006 Main Road', 'user1006@email.com', 'Member'),
('Donell Mccormick', 6471001007, '1007 Main Road', 'user1007@email.com', 'Member'),
('Nial Rigby', 6471001008, '1008 Main Road', 'user1008@email.com', 'Guest'),
('Marnie Amos', 6471001009, '1009 Main Road', 'user1009@email.com', 'Guest'),
('Zaynah Daly', 6471001010, '1010 Main Road', 'user1010@email.com', 'Guest'),
('Anita Finney', 6471001011, '1011 Main Road', 'user1011@email.com', 'Guest');
INSERT INTO users (username, email, full_name, password) VALUES
('admin', 'admin@example.com', 'Admin Admin', '$2b$12$wBUexpVhNNWdfUCS3.H98uFGxNEUpTuFJmlTmLqvetokFutD.D4qe'),
('alice', 'alice@example.com', 'Alice Johnson', '$2b$12$d.6wPNh9cU5.MyaDJX17z.efBBWlkHm6UsEoggrwzGPCimgBi.aLK'),
('bob', 'bob@example.com', 'Bob Smith', '$2b$12$boYRd/JBp3uswGp.bDA2b.fCn5sbpcSMK9ww/sBDS67XofxISqBky'),
('carol', 'carol@example.com', 'Carol White', '$2b$12$n0F1.ieUfwnvMCAJbijKo.v4IMPRyv72h1s6I0/JjwgoUI373jBJi'),
('dave', 'dave@example.com', 'Dave Black', '$2b$12$J/mc6IO.ilh0bNjRHWAh6OfHXITAxoY5OHGmSnZFxBchYVgxw4DNG'),
('eve', 'eve@example.com', 'Eve Brown', '$2b$12$dr.ykfnIFvv9HwvNJv/KUOBlJ1W9QleaBu9M64x5tIJjPkgRrYuvG');


INSERT INTO roles (name) VALUES
('admin'),
('user');

INSERT INTO user_roles (user_id, roles_id) VALUES
(1,1),
(2,1),
(2,2),
(3,2),
(4,2),
(5,2),
(6,1),
(6,2);


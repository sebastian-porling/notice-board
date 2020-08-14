INSERT INTO users (username, password) VALUES
    ('DragonSlayer1337', 'password'),
    ('mamma', 'easy'),
    ('admin', 'root'),
    ('president_trump', 'whitehouse');

INSERT INTO notices (author_id, content, date) VALUES
    (1, 'Just so everyone knows, I slayed about 20 dragons last month!', '2020-05-11'),
    (4, 'The Democrats better watch themselves...', '2019-05-12'),
    (2, 'Today I baked some cookies!', '2018-11-11'),
    (3, 'I will be removing some notices next week!', '2013-01-20'),
    (1, 'Damn... Why does the princess live and the knight die?', '2020-08-11');

INSERT INTO comments (author_id, text) VALUES
    (2, 'Good job!'),
    (4, 'You are a great citizen!'),
    (3, 'You better watch yourself trump!'),
    (1, 'Made out of dragon blood?');

INSERT INTO notices_comments (notices_id, comments_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4);
-- Insert sample users
-- password for exemplary users is ex
INSERT INTO app_user (nickname, first_name, last_name, email, password, gender, day_of_birth, description, image, account_locked, enabled)
VALUES
    ('CrazyJohnny', 'John', 'Doe', 'JohnDoe12@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '2001-01-01', 'Loves adventure sports and video games', null, false, true),
    ('JustJane777', 'Jane', 'Smith', 'janejanejane@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1992-10-02', 'Avid reader and coffee enthusiast', null, false, true),
    ('placeholder3', 'Michael', 'Johnson', 'placeholder3@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '2005-03-10', 'Future software developer and tech geek', null, false, true),
    ('ksjcnjfiuowurjh', 'Emily', 'Williams', 'Emilly.Williams@icloud.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm@dew', 'Female', '1994-04-01', 'Aspiring artist with a passion for painting', null, false, true),
    ('TooHotToHandle', 'Daniel', 'Brown', 'WhooperMAXXX@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1995-05-05', 'Fitness fanatic and personal trainer', null, false, true),
    ('SpeedyGonzales', 'Gonzales', 'Rodriguez', 'speedy@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1996-01-06', 'Competitive runner and sports enthusiast', null, false, true),
    ('MissyMiss', 'Melissa', 'Garcia', 'missymiss@hotmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1997-07-08', 'Passionate about fashion and design', null, false, true),
    ('RockingRobin', 'Robert', 'Robinson', 'rockinrobin@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1998-08-04', 'Guitarist in a local band', null, false, true),
    ('DancingQueen', 'Olivia', 'Martinez', 'dancequeen@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1999-09-01', 'Loves dancing and choreography', null, false, true),
    ('StarryNight', 'Vincent', 'Van Gogh', 'starrynight@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '2000-04-10', 'Art lover and museum frequent visitor', null, false, true),
    ('FancyPants', 'Frank', 'White', 'fancypants@icloud.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1988-08-04', 'Loves luxury fashion and high-end accessories', null, false, true),
    ('ShiningStar', 'Samantha', 'Johnson', 'shiningstar@icloud.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1986-04-06', 'Volunteer and community service advocate', null, false, true),
    ('MightyMouse', 'Mark', 'Taylor', 'mightymouse@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1984-04-04', 'Cartoon lover and comic book collector', null, false, true),
    ('SparklingSoul', 'Sara', 'Anderson', 'sparklingsoul@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1982-10-02', 'Yoga instructor and wellness coach', null, false, true),
    ('GoldenHeart', 'Gary', 'Brown', 'goldenheart@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1989-10-08', 'Philanthropist with a heart of gold', null, false, true),
    ('SilverLining', 'Samantha', 'White', 'silverlining@icloud.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1978-10-08', 'Positive thinker and motivational speaker', null, false, true),
    ('RockStar', 'Robert', 'Johnson', 'rockstar@outlook.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1996-08-10', 'Aspiring musician and rock enthusiast', null, false, true),
    ('DivaQueen', 'Danielle', 'Davis', 'divaqueen@outlook.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1974-04-10', 'Loves singing and performing on stage', null, false, true),
    ('TechieGuy', 'Thomas', 'Taylor', 'techieguy@outlook.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '2009-10-02', 'Gadget lover and tech wizard', null, false, true),
    ('Fashionista', 'Fiona', 'Fisher', 'fashionista@outlook.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1971-04-08', 'Fashion blogger and trendsetter', null, false, true),
    ('ArtisticAmy', 'Amy', 'Adams', 'amyadams@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1990-11-01', 'Graphic designer and digital artist', null, false, true),
    ('TechGuru', 'Jake', 'Jackson', 'jakej@gmail.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1985-12-02', 'IT consultant and tech guru', null, false, true),
    ('TravelerTom', 'Tom', 'Hanks', 'traveltom@yahoo.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1975-09-12', 'World traveler and adventure seeker', null, false, true),
    ('NatureNancy', 'Nancy', 'Green', 'nancygreen@icloud.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Female', '1988-02-28', 'Nature enthusiast and wildlife photographer', null, false, true),
    ('FitnessFreak', 'Chris', 'Brown', 'chrisb@outlook.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1991-06-15', 'Gym enthusiast and fitness coach', null, false, true),
    ('MusicMan', 'David', 'Bowie', 'davidb@music.com', '$2a$10$U2QArxDtM0o6tzpIdlbWoe0LArso5yqv9PEy9Sbt/.2G7ZZKSxGBm', 'Male', '1990-07-07', 'Musician and avid concert-goer', null, false, true);

-- Insert sample tags
INSERT INTO role (id, name)
VALUES
    (1, 'USER');

INSERT INTO app_user_roles (roles_id, users_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (1, 15),
    (1, 16),
    (1, 17),
    (1, 18),
    (1, 19),
    (1, 20),
    (1, 21),
    (1, 22),
    (1, 23),
    (1, 24),
    (1, 25),
    (1, 26);

-- Insert sample tags
INSERT INTO tag (name)
VALUES
    ('travel'),
    ('food'),
    ('lifestyle'),
    ('nature'),
    ('technology'),
    ('fitness'),
    ('music'),
    ('art');

-- Insert sample posts
INSERT INTO post (user_id, caption, image_url, category, description, is_private)
VALUES
    (1, 'A beautiful sunset!', NULL, 'Nature', 'Sunset at the beach', FALSE),
    (2, 'Delicious meal!', NULL, 'Food', 'Homemade pasta', FALSE),
    (3, 'Exploring the mountains!', NULL, 'Travel', 'Hiking in the Alps', TRUE),
    (4, 'A day at the beach', NULL, 'Lifestyle', 'Relaxing on the sand', FALSE),
    (5, 'New tech gadget', NULL, 'Technology', 'Latest smartphone review', TRUE),
    (6, 'Morning workout', NULL, 'Fitness', 'Gym session', FALSE),
    (7, 'Rock concert!', NULL, 'Music', 'Live performance', TRUE),
    (8, 'Art exhibition', NULL, 'Art', 'Modern art gallery', FALSE),
    (9, 'City skyline', NULL, 'Nature', 'View from skyscraper', FALSE),
    (10, 'Healthy breakfast', NULL, 'Food', 'Oatmeal and fruits', FALSE),
    (11, 'Hiking adventure', NULL, 'Travel', 'Trail in the forest', TRUE),
    (12, 'Relaxing at home', NULL, 'Lifestyle', 'Reading a book', FALSE),
    (13, 'Latest smartphone', NULL, 'Technology', 'Unboxing video', TRUE),
    (14, 'Evening yoga', NULL, 'Fitness', 'Sunset yoga session', FALSE),
    (15, 'Jazz night', NULL, 'Music', 'Jazz club performance', TRUE),
    (16, 'Painting class', NULL, 'Art', 'Learning to paint', FALSE),
    (17, 'Forest trail', NULL, 'Nature', 'Walking in the woods', FALSE),
    (18, 'Gourmet dinner', NULL, 'Food', 'Five-course meal', TRUE),
    (19, 'Traveling by train', NULL, 'Travel', 'Cross-country trip', TRUE),
    (1, 'Chilling in the park', NULL, 'Lifestyle', 'Afternoon at the park', FALSE),
    (1, 'Tech conference', NULL, 'Technology', 'Attending a tech conference', TRUE),
    (1, 'Gym session', NULL, 'Fitness', 'Weight training', FALSE),
    (1, 'Classical concert', NULL, 'Music', 'Symphony orchestra', TRUE),
    (1, 'Sculpture exhibit', NULL, 'Art', 'Sculpture gallery', FALSE),
    (1, 'Desert adventure', NULL, 'Nature', 'Exploring the desert', TRUE);

-- Insert tags for posts
INSERT INTO post_tag (post_id, tag_id)
VALUES
    (1, (SELECT id FROM tag WHERE name = 'travel')),
    (1, (SELECT id FROM tag WHERE name = 'nature')),
    (2, (SELECT id FROM tag WHERE name = 'food')),
    (3, (SELECT id FROM tag WHERE name = 'travel')),
    (3, (SELECT id FROM tag WHERE name = 'nature')),
    (4, (SELECT id FROM tag WHERE name = 'lifestyle')),
    (5, (SELECT id FROM tag WHERE name = 'technology')),
    (6, (SELECT id FROM tag WHERE name = 'fitness')),
    (7, (SELECT id FROM tag WHERE name = 'music')),
    (8, (SELECT id FROM tag WHERE name = 'art')),
    (9, (SELECT id FROM tag WHERE name = 'nature')),
    (10, (SELECT id FROM tag WHERE name = 'food')),
    (11, (SELECT id FROM tag WHERE name = 'travel')),
    (12, (SELECT id FROM tag WHERE name = 'lifestyle')),
    (13, (SELECT id FROM tag WHERE name = 'technology')),
    (14, (SELECT id FROM tag WHERE name = 'fitness')),
    (15, (SELECT id FROM tag WHERE name = 'music')),
    (16, (SELECT id FROM tag WHERE name = 'art')),
    (17, (SELECT id FROM tag WHERE name = 'nature')),
    (18, (SELECT id FROM tag WHERE name = 'food')),
    (19, (SELECT id FROM tag WHERE name = 'travel')),
    (20, (SELECT id FROM tag WHERE name = 'lifestyle')),
    (21, (SELECT id FROM tag WHERE name = 'technology')),
    (22, (SELECT id FROM tag WHERE name = 'fitness')),
    (23, (SELECT id FROM tag WHERE name = 'music')),
    (24, (SELECT id FROM tag WHERE name = 'art')),
    (25, (SELECT id FROM tag WHERE name = 'nature'));

-- Insert sample comments
INSERT INTO comment (post_id, user_id, text)
VALUES
    (1, 2, 'Amazing view!'),
    (1, 1, 'Looks delicious!'),
    (1, 4, 'Love the mountains!'),
    (1, 3, 'Beach days are the best'),
    (1, 6, 'Need to get this gadget!'),
    (6, 5, 'Great workout routine!'),
    (7, 8, 'Rock on!'),
    (8, 7, 'Beautiful artwork!'),
    (9, 10, 'Stunning skyline!'),
    (10, 9, 'Healthy and tasty!'),
    (11, 12, 'Adventure awaits!'),
    (12, 11, 'Home sweet home!'),
    (13, 14, 'Must buy this!'),
    (14, 13, 'Feeling zen'),
    (15, 16, 'Amazing performance!'),
    (16, 15, 'Love this class!'),
    (17, 18, 'Nature at its best'),
    (18, 17, 'Gourmet indeed!'),
    (19, 20, 'Trains are the best way to travel'),
    (20, 19, 'Park life!'),
    (21, 22, 'Learned so much at the conference'),
    (22, 21, 'Pumped up!'),
    (23, 24, 'Classical music is timeless'),
    (24, 23, 'Sculptures are fascinating'),
    (25, 2, 'Desert adventures are thrilling');

-- Insert sample reactions
INSERT INTO post_reaction (post_id, user_id, reaction)
VALUES
    (1, 2, 'like'),
    (2, 1, 'like'),
    (1, 3, 'sad'),
    (2, 4, 'funny'),
    (3, 5, 'sad'),
    (4, 6, 'like'),
    (5, 7, 'wow'),
    (6, 8, 'like'),
    (7, 9, 'wow'),
    (8, 10, 'wow'),
    (9, 11, 'like'),
    (10, 12, 'sad'),
    (11, 13, 'funny'),
    (12, 14, 'like'),
    (13, 15, 'wow'),
    (14, 16, 'like'),
    (15, 17, 'haha'),
    (16, 18, 'love'),
    (17, 19, 'like'),
    (18, 20, 'sad'),
    (19, 21, 'funny'),
    (20, 22, 'like'),
    (21, 23, 'wow'),
    (22, 24, 'like'),
    (23, 25, 'haha'),
    (24, 1, 'like'),
    (25, 2, 'like');
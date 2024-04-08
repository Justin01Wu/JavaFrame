
-- spring boot will automatically run this sql when it has spring-boot-starter-data-jpa dependency
 
INSERT INTO user (name, email, position) VALUES
  ('Justin', 'justin@gmail.com', 'D'),
  ('Rita', 'rita@hotmail.com', 'M'),
  ('Yan', 'None', 'Q');
  
INSERT INTO TestBean (indOrientation, gender) VALUES
  ('L', 10),
  ('P', 20);
  
INSERT INTO contract2 (id, programId, name, type, eventLimit, parentId ) VALUES
  (1, 398, 'L1A', null, 2000, null ),
  (2, 398, 'L2A', 'C', 	3000, 1),
  (3, 398, 'L2B', 'I', 	3500, 1),
  (4, 398, 'L3A', 'I', 	4000, 2);
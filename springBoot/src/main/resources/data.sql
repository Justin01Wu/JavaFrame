
-- spring boot will automatically run this sql when it has spring-boot-starter-data-jpa dependency
 
INSERT INTO user (name, email, position) VALUES
  ('Justin', 'justin@gmail.com', 'D'),
  ('Rita', 'rita@hotmail.com', 'M'),
  ('Yan', 'None', 'Q');
  
INSERT INTO test_bean (ind_orientation, gender) VALUES
  ('L', 10),
  ('P', 20);
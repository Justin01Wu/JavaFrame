
-- spring boot will automatically run this sql when it has spring-boot-starter-data-jpa dependency

DROP TABLE IF EXISTS user;
 
CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  position VARCHAR(25) NULL,
  email VARCHAR(250) NOT NULL  
);


DROP TABLE IF EXISTS test_bean;
 
CREATE TABLE test_bean (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  ind_orientation VARCHAR(25) NULL
);

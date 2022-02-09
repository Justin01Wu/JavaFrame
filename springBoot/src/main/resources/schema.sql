
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
  ind_orientation VARCHAR(25) NULL,
  gender int NULL
);

DROP TABLE IF EXISTS contract2;

CREATE TABLE contract2 (
  id INT PRIMARY KEY,
  program_id INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  parent_id INT,
  type char(1),
  event_limit float
);

--  recursive way to get contract full name
-- from https://stackoverflow.com/questions/10176610/how-to-show-recursive-parentid-in-a-single-column-in-sql
DROP TABLE IF EXISTS VW_CONTRACT;

CREATE VIEW VW_CONTRACT 
AS 
  WITH conTree (id, name, parent_id, fullName) as 
(
  SELECT id,	name,	parent_id,	name as fullName
  FROM contract2
  WHERE parent_id IS NULL

  UNION ALL

  SELECT t2.id,	t2.name,	t2.parent_id,           
		 CASE  
			WHEN t2.type ='C' THEN t2.name || ' Component OF ' || conTree.fullName  
			WHEN t2.type ='I' THEN t2.name || ' Inure to ' || conTree.fullName  
	        ELSE 'UNKNOWN' 
		END AS fullName
  FROM contract2 t2 
     INNER JOIN conTree ON conTree.id = t2.parent_id
)
SELECT * FROM conTree;

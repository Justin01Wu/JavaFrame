

-- (1) it will stop main operation if related trigger fails, like trigger can't find target column name

-- (2) correct way to get value before update in trigger: 

-- sql server trigger to get value before update
CREATE TRIGGER trigger_update_department ON  department AFTER UPDATE
AS 
  INSERT INTO departmentHistory(id,name,logDate )
  SELECT  d.id, d.name, GETDATE() FROM Deleted d
  INNER JOIN Inserted i ON i.id = d.id
  -- have to use this way to get value before updating

GO

-- (3) trigger for deleting
CREATE TRIGGER trigger_delete_department ON  department AFTER DELETE
AS 
  INSERT INTO departmentHistory (id, name,logDate)
  SELECT     id,     name,  GETDATE()   FROM Deleted
GO



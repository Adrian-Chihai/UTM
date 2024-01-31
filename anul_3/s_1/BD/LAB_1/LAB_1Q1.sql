USE AdventureWorks2022;
SELECT * FROM HumanResources.Department;
-- verificarea accesului la modificarea datelor din tabel
EXEC sp_rename 'HumanResources.Department.Name', 'Speciality', 'COLUMN';



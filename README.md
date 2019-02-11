# test2015
Write a simple desktop MVC application in Java, using SQLite database. Create a database with these tables:

Company – id (int), name (varchar)
Emloyee – id (int), id_company (int), name (varchar), salary (int)

In GUI, create an editor to add/edit/delete companies and employees.

Show table with three columns – company name, employee count and average salary, order by average salary descending.  When you click on company in the table, show dialog with maximum salary in this company. Table should be updated with every change of databse.

Use singleton design pattern for database connection.

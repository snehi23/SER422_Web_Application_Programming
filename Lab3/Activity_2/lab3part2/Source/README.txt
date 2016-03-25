To maintain many-to-many relationship I have provided 3 tables namely
1. AUTHORS
2. BOOKS
3. AUTHORS_BOOKS 

AUTHORS_BOOKS Table maintains 3rd normalise form of relation for Tables AUTHORS and BOOKS by storing their foreign keys in the form of associations. 

3 model objects and 3 DAOs are provided for 3 tables mentioned above with their respective RDBMS implementations.

All DAO implementations are storage independent which can be seen as part of 3rd activity.

Author and Book CRUD provide 3 main functionalities :

1. add : to add author/book if it is not present
2. update : 
	a. author : to update the record values
	b. book : to update record values and author association too.
3. delete : 
	a. author : delete record with book associations
	b. book : delete record with author associations

4. Hard delete (YES or NO):
	a. author : It will delete book association as well as entry from DB
	b. book : It will delete author association as well as entry from DB
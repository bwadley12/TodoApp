
# TodoTracker

Todo Tracker in Spring Boot for interview with NG

Requirements:
Develop a Java microservice using Spring Boot that implements a RESTful API for a simple to-do list application. The
API should allow a user to:

1. Create a new to-do item with a title and description.
2. Retrieve a list of all to-do items.
3. Retrieve a specific to-do item by its ID.
4. Update the title and description of a to-do item.
5. Delete a to-do item by its ID.

Use an in-memory database (such as H2) for data persistence. The implementation should also include proper
error handling and validation for inputs.

----------------------------------------------------------------------------------------------------------------------------

Helpful api commands:
CREATE:         curl -X POST -H "Content-Type: application/json" -d '{"title":"testTitle", "description":"testdescription"}' http://localhost:8080/create
READ ALL:       curl -X GET http://localhost:8080/getAll
READ BY ID:     curl -X GET http://localhost:8080/find/?id=1
PATCH:          curl -X PATCH -H "Content-Type: application/json" -d '{"title":"updatedTitle"}' http://localhost:8080/update/?id=1
DELETE:         curl -X DELETE http://localhost:8080/delete/?id=1
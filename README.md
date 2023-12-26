This project implements a User Management API with the following features:

Get Random User Details API

Endpoint: GET http://localhost:8080/api
Retrieves random user details such as gender, nationality, name, and age.
Get Nationality API

Endpoint: GET http://localhost:8080/nationality/{name}
Retrieves the nationality of a user by providing their name.
Get Gender API

Endpoint: GET http://localhost:8080/gender/{name}
Retrieves the gender of a user by providing their name.
Create Users API

Endpoint: POST http://localhost:8080/users
Creates a specified number of random users (limited to a maximum of 5) based on the provided size in the request payload.
Get Users API

Endpoint: GET http://localhost:8080/users
Retrieves a list of users based on query parameters for sorting, sorting order, limit, and offset.
Validation and Error Handling
Input parameters are validated using custom validators to ensure the correctness of user inputs.
Strategy design pattern is employed for sorting users based on sort type and sort order.
Web client configuration is implemented for external API calls with specified connection, read, and write timeouts.
Error messages are customized for a better understanding of issues.
Configuration
Web client configuration properties:

connection_timeout: 2000 (milliseconds)
read_time_out: 2000 (milliseconds)
write_time_out: 2000 (milliseconds)

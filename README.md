This project is an API testing suite using **RestAssured** and **TestNG** for automated testing of HTTP requests. It interacts with a dummy API (`https://dummyjson.com/`) to perform the following operations on user data:

- **POST Request**: Adds a new user by sending a JSON payload from a file (`Data.json`).
- **GET Request**: Retrieves an existing user by ID, using a data provider to test multiple user records.
- **PUT Request**: Updates an existing user's data.
- **PATCH Request**: Partially updates an existing user's data.
- **DELETE Request**: Deletes a user and validates the deletion.

The tests assert the appropriate status codes and response content for each API operation.

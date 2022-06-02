# oracle-api-challenge
Hello there! This is the ORACLE API CHALLENGE project, a Java based exercise designed *to provide a general overview to the candidate of the daily duties
that it would be performing in his day to day at Oracle*.

### Problem Statement
Assume that you are developing a system to manage the user information of all the Oracle
Employees, such as names, roles, and addresses. And the system needs to be scalable to support
hundreds or thousands of requests per second, so be prepared to explain how you can escalate
your solution once you need to explain it to the developer team.

### About the Technical Requirements
The next are the technologies needed in order to run the project locally:

1. Java 17
2. Apache Maven 3.x.x
3. Docker v. 20

A MongoDB instance will be embedded at Docker when project running, but isn't required to be installed locally. 

### Application Execution
Assuming the software listed previously is installed on your machine, and Docker service is already running, the steps to run **oracle-api-challenge** application are described below. 

a) Compile and create the project JAR file using your preferred IDE, or using Maven from CMD.

    mvn clean install

b) Open a new CMD on your machine and update the **MongoDB** image.

	docker pull mongo:latest

c) Same, on CMD, navigate to the project folder and create the *oracle-api-challenge* project image.

	docker build -t oracle-api-challenge:1.0.0 .

d) Then, initialize the project instance.

	docker-compose up

The application will run on your local under 8080 port. Also, a 

For your convenience, this project implements Swagger UI, so you may easily validate all API specifications at http://localhost:8080/oracle-employees/swagger-ui.html

### API Specifications
| URL                                       | Operation | Description                                                                                                         |
|-------------------------------------------|-----------|---------------------------------------------------------------------------------------------------------------------|
| /oracle-employees/v1/users/{user-Id}      | GET       | Returns the user information by the ID provided as a Path Param                                                     |
| /oracle-employees/v1/users                | GET       | Returns a list of all the Users in The Database                                                                     |
| /oracle-employees/v1/users/role/{role-id} | GET       | Returns a list of all Users that has the role-id received as a path param (that can only be ARCHITECT or DEVELOPER) |
| /oracle-employees/v1/users                | POST      | Creates a New User, according with the User Specification                                                           |
| /oracle-employees/v1/users/{user-Id}      | PUT       | Updates all the information of the User                                                                             |
| /oracle-employees/v1/users/{user-Id}      | PATCH     | Updates only 1 attribute of the user, it can be anyone of the User Specification                                    |
| /oracle-employees/v1/users/{user-Id}      | DELETE    | Deletes a User Record by its Id                                                                                     |

### API Request Examples

Let's consider the next as the **user data structure**:
```
{
 "userId": 1234567890,
 "firstName": "Bob",
 "lastName": "Miner",
 "role": "DEVELOPER",
 "address": {
   "street": "4120 Network Cir",
   "county": "Agnew",
   "city": "Santa Clara",
   "state": "CA",
   "postalCode": 95054,
   "country": "United States",
   "phoneDetails": {
     "countryCode": 408,
     "phone": 2760000
   }
 }
}
```

**Get user by ID** 

    http://localhost:8080/oracle-employees/v1/users/62982ace931ffed5f76a06bf

**Get all users**

    http://localhost:8080/oracle-employees/v1/users

**Get users by Role**

    http://localhost:8080/oracle-employees/v1/users/role/DEVELOPER
    http://localhost:8080/oracle-employees/v1/users/role/ARCHITECT

**Create new user**

    http://localhost:8080/oracle-employees/v1/users

This one should be made using a **POST** method request. The JSON request is:
```
{
  "city": "string",
  "country": "string",
  "county": "string",
  "firstName": "string",
  "lastName": "string",
  "phoneCountryCode": 0,
  "phoneNumber": 0,
  "postalCode": 0,
  "role": "DEVELOPER",
  "state": "string",
  "street": "string"
}
```

**Update user**


    http://localhost:8080/oracle-employees/v1/users/62982ace931ffed5f76a06bf

This one should be made using a **PUT** method request. The JSON request is:
```
{
  "city": "string",
  "country": "string",
  "county": "string",
  "firstName": "string",
  "lastName": "string",
  "phoneCountryCode": 0,
  "phoneNumber": 0,
  "postalCode": 0,
  "role": "DEVELOPER",
  "state": "string",
  "street": "string"
}
```

**Update user field**

    http://localhost:8080/oracle-employees/v1/users/62982ace931ffed5f76a06bf

This one should be made using a **PATCH** method request. The JSON request is:
```
{
    "op":"replace",
    "path":"/address/street",
    "value":"San Valentín No. 501"
}
```
The path value should make match with a field on the **user data structure**.

**Delete user**

    http://localhost:8080/oracle-employees/v1/users/62982ace931ffed5f76a06bf

This one should be made using a **DELETE** method request.
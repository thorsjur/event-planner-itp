[nav](../../docs/nav.md)

# Group 25 - REST module

## Rest module content

- [ServerApplication](./src/main/java/eventplanner/rest/RestServiceApplication.java)
- [Controllers](./src/main/java/eventplanner/rest/)

## README content
- [Usage](#usage)
- [Enhancement](#enhancement---api-key)
- [Rest module architecture](#rest-module-architecture)
- [Generating reports](#generating-reports)
- [API Documentation](#api-documentation)

<br>

## Usage
### Serverapplication

The serverapplication can be found [here](/eventplanner/rest/src/main/java/eventplanner/rest/RestServiceApplication.java), and launches the server locally on path localhost, port 8080.

The server needs to run in order for the fxui-application to connect with RemoteDataAccess. If the server is not running, the fxui-application will run using LocalDataAccess. Note the server has to be started before the application, to ensure the proper DataAccess is used.

You can start the server from the `eventplanner/rest/` directory with the command:
 ```
mvn exec:java
```

It should be noted that all api-requests are ran locally even though a rest-server is used. This is the consequence of running the server on localhost. ideally the server would be hosted on a remote server, where it could be accesed by anyone with an internet connection. 

In this project, a local api-rest server is sufficient to prove that this application also would work with an remote hosted api-rest server. 

<br>

### User and Event Controllers

The controllers can be found [here](./src/main/java/eventplanner/rest/), and contains handler methods for mapping a HTTP request to a given handler.

A rest-api can be accessed by sending http-request to the server. In this project we mainly rely on the local service path `http//:localhost:8080`

The server will then get this request, and send back information depending on the controllers. Every request has a path, and every request with path starting with:
 ```
http//:localhost:8080/user
```
Will be sent to the corresponding controller. In this case, the UserController.

#### EventController
The Eventcontroller class can be found [here](./src/main/java/eventplanner/rest/EventController.java).

This controller handles all requests with base:
 ```
http//:localhost:8080/event
```
This class implements methods for:
- Creating events
- Retrieve all events
- Updating events
- Deleting events

#### Usercontroller
The Usercontroller class can be found [here](./src/main/java/eventplanner/rest/UserControllerjava), and handles all requests starting with:
 ```
http//:localhost:8080/user
```
This class implements methods for:
- Creating users
- Retrieving users
- Deleting users

Note it is not possible to update a user through the REST API.

<br>

## Enhancement - API-key
As of now, the api-server is accesible by anyone. This is generally a bad practice for api-servers.

Ideally we would add functionality for sending request with an api-key. The key would then be verified at the api-server. The response would depend on whether the given key is associated with access to the requested data.

We will not implement api-keys, but if the server were to run on an remote server, this is a functionality we would implement.

<br>

## Rest module Architecture
![Architecture diagram](../../docs/diagrams/rest_architecture.png)

**Legend:**
 - A red, dotted line from *A* to *B* depicts that *B* is a dependency of *A*.
    - Arrows from a module indicate that all layers in the module has the dependency
 - The "box" named *eventplanner* represents the collection of local modules.
 - The components with a symbol in the top-right corner represents modules, such as *jackson* and *core*.
 - The folders represent the current packages or layers of the modules.

<br>

## Generating reports

Spotbugs and checkstyle reports are generated on

 ```
mvn site
```

All reports can be found at each respective modules `target/site` folder.

spotbugs.html;
checkstyle.html;  
<br>

## API Documentation
### **GET event?id={id}**
Retrieves an event by the event id.

| Parameter | Type | Description |
|-----------|------|-------------|
|**id** | String | the UUID of the event you want to retrieve.

#### **Example**
<details>
  <summary>http://localhost:8080/event?id=4a575ea8-0211-4814-9e1d-042ecc75a557</summary>
  
  ```json
  {
      "id" : "4a575ea8-0211-4814-9e1d-042ecc75a557",
      "type" : "QUIZ",
      "name" : "Name",
      "start-time" : "2023-07-23T12:20",
      "end-time" : "2023-07-23T22:20",
      "location" : "Location",
      "author" : "admin@samfundet.no",
      "description" : "No description available",
      "users" : [ "test1@example.com", "test2@example.com", "test3@example.com" ]
   }

  ```
</details>

#### **Response**
Returns the JSON representation of the event conforming to the projects event schema.

<br>

### **GET event/all**

Retrieves a all saved events.

#### **Example**
<details>
  <summary>http://localhost:8080/event/all</summary>
  
  ```json
  [ {
      "id" : "123e4567-e89b-12d3-a456-256642440000",
      "type" : "CONCERT",
      "name" : "UNDERGRUNN",
      "start-time" : "2022-12-30T21:00",
      "end-time" : "2022-12-30T23:59",
      "location" : "Storsalen",
      "author" : "SAMFUNDET@samf.no",
      "description" : "Rapkometene Undergrunn slapp sitt nye album UNDERGRUNN den 6. april. I   over 10 låter utforsker ungguttene en rekke sjangere samtidig som de leverer tekster på  skyhøyt nivå. Over lekne beats fra Loverboy og Rikpappa maler undergrunn bilder om et liv i full fart der flex, sex og rus går hånd i hånd med kjærlighet, selvransakelse og  melankoli.",
      "users" : [ "test@example.com" ]
      }, {
      "id" : "123e4567-e89b-12d3-a456-156642440000",
      "type" : "COURSE",
      "name" : "IT-course",
      "start-time" : "2023-01-10T12:15",
      "end-time" : "2023-01-10T18:00",
      "location" : "Realfagsbygget",
      "author" : "NTNU@ntnu.no",
      "description" : "Course in OOP for first year students in datatek",
      "users" : [ ]
      }, {
      "id" : "123e4567-e89b-12d3-a456-956642440000",
      "type" : "CONCERT",
      "name" : "Emma Steinbakken",
      "start-time" : "2023-08-17T22:00",
      "end-time" : "2023-08-18T02:00",
      "location" : "Storsalen",
      "author" : "SAMFUNDET@samf.no",
      "description" : "Emma Steinbakken gjester Storsalen! Vi gleder oss.",
      "users" : [ ]
} ]

  ```
  
</details>

#### **Response**
Returns the JSON representation of the event conforming to the projects event schema.

<br>

### **PUT event/update**

Updates the users of a provided event.
The request must have the header `content-type` set to `application/json`, and the payload must conform to the projects event serialization schema.

#### **Example**
<details>
  <summary>http://localhost:8080/event/update</summary>
  
  ```java
  true
  ```
</details>

#### **Response**
Returns a boolean value indicating whether the event was successfully updated.

<br>

### **POST event/create**

Creates a new event.
The request must have the header `content-type` set to `application/json`, and the payload must conform to the projects event serialization schema.

#### **Example**
<details>
  <summary>http://localhost:8080/event/create</summary>
  
  ```java
  true
  ```
</details>

#### **Response**
Returns a boolean value indicating whether the event was successfully created.

<br>

### **DELETE event/{id}**

Deletes an event provided its id.

| Parameter | Type | Description |
|-----------|------|-------------|
|**id** | String | the UUID of the event you want to delete.

#### **Example**
<details>
  <summary>http://localhost:8080/event/{id}</summary>
  
  ```java
  false
  ```
</details>

#### **Response**
Returns a boolean value indicating whether the event was successfully deleted.

<br>

### **GET user/get?email={email}**

Retrieves an user given an email.

| Parameter | Type | Description |
|-----------|------|-------------|
|**email** | String | the email of the user you want to retrieve.

#### **Example**
<details>
  <summary>http://localhost:8080/user/get?email=test@example.com</summary>
  
  ```json
  {
   "email" : "test@example.com",
   "password" : "passwordh",
   "above18" : false
   }

  ```
</details>

#### **Response**
Returns the JSON representation of the user conforming to the projects user schema.

<br>

### **POST user/create**

Creates a new user.
The request must have the header `content-type` set to `application/json`, and the payload must conform to the projects serialization schema for a user.

#### **Example**
<details>
  <summary>http://localhost:8080/user/create</summary>
  
  ```java
  true
  ```
</details>

#### **Response**
Returns a boolean value indicating whether the user was successfully created.

<br>

### **DELETE user/{email}**

Deletes an user provided its email.

| Parameter | Type | Description |
|-----------|------|-------------|
|**email** | String | the email of the user you want to delete.

#### **Example**
<details>
  <summary>http://localhost:8080/user/{id}</summary>
  
  ```java
  true
  ```
</details>

#### **Response**
Returns a boolean value indicating whether the user was successfully deleted.
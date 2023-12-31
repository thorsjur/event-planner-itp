[nav](../../docs/nav.md)

# REST module

## Rest module content

- [ServerApplication](./src/main/java/eventplanner/rest/RestServiceApplication.java)
- [Controllers](./src/main/java/eventplanner/rest/)
- [Controller tests](./src/test/java/eventplanner/rest/)

<br>

## README content
- [Usage](#usage)
- [Enhancement](#enhancement)
- [Rest module architecture](#rest-module-architecture)
- [Rest module Class Diagram](#rest-module-class-diagram)
- [Generating reports](#generating-reports)
- [API Documentation](#api-documentation)

<br>

## Usage
### **Serverapplication**

The serverapplication can be found [here](/eventplanner/rest/src/main/java/eventplanner/rest/RestServiceApplication.java), and launches the server locally using localhost on port 8080.

The server needs to run in order for the fxui-application to connect with RemoteDataAccess. If the server is not running, the fxui-application will run offline using LocalDataAccess. Note the server has to be started before the application, to ensure the proper DataAccess derivative is used.

You can start the server from the `eventplanner/rest/` directory with the command:
 ```
mvn exec:java
```

It should be noted that all API requests are made locally, even though a REST server is being used. This is the consequence of running the server on localhost. ideally the server would be hosted on a remote server, where it could be accesed by anyone with an internet connection and proper authentication. 

In this project, a local REST server hosted on localhost is sufficient to demonstrate that this application works with a remote hosted REST server. 

<br>

### **User and Event Controllers**

The controllers can be found [here](./src/main/java/eventplanner/rest/), and contains handler methods for mapping a HTTP request to a given handler.

A REST API can generally be accessed by sending a HTTP request to the server. In this project we mainly rely on the local service path `http//:localhost:8080`

The server will then recieve the request, and send back a response depending on the handler method in the respective controller. By resolving a URI (Uniform Resource Identifier) with the service path you can request a resource. For example, by resolving the URI `/event` to the service path you get the base URI for the Event controller. Now we have the path to
 ```
http//:localhost:8080/event
```
and by resolving the URI with the URI `/all`, you can send a GET request and recieve a JSON representation of all events. The API documentation describing all supported REST requests can be found at the bottom of this readme.

<br>

#### **EventController**
The controller handling all requests dealing with events can be found [here](./src/main/java/eventplanner/rest/EventController.java).

The controller has the URI base:
 ```
http//:localhost:8080/event
```
The Event controller implements handler methods to:
- Create events
- Retrieve all events
- Update events
- Delete events

#### **Usercontroller**
The controller handling all requests dealing with users is to be found [here](./src/main/java/eventplanner/rest/UserController.java), and handles all requests with the URI base:
 ```
http//:localhost:8080/user
```
The class implements methods for handling:
- Creating users
- Retrieving users
- Deleting users

*Note it is not possible to update a user through the REST API.*

<br>

## Enhancement
As of now, the REST API is accessible by anyone. This is generally a bad practice for handling requests to a server. To circumvent this, additional authentication would have to be implemented.

Ideally we would add functionality for sending requests containing an API key. The key would then be verified at the server. The response would depend on whether the given key is a valid access token, and if the key proves sufficient clearance for the requested operation. E.g. a delete operation might require admin rights for a given REST API.

We will not implement API keys for this project, however if the server was to run on a remote server, this is a functionality we would have to implement to prevent potentially harmful requests.

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

## Rest module Class Diagram
![Rest Module Class Diagram](../../docs/diagrams/rest_class_diagram.png)

**Legend:**
 - Lines from *A* to *B* depicts an association between *A* and *B*. 
    - An arrow from *A* to *B* means that *A* has access to *B* but *B* does not have acces to *A*
    - A line between *A* and *B* represents a relationship between *A* and *B*
 - The icons to the left of fields and methods represent visibility:
 ![Visibility icons description](../../docs/diagrams/visibility_icons.png)
 - The "folders" named *eventplanner.rest*, *eventplanner.core* and *eventplanner.json* represent relevant modules in the project.

<br>

## Generating reports

Spotbugs and checkstyle reports will be generated by running the command

 ```
mvn site
```

Both reports can be located at the module's `target/site` folder, aptly named `spotbugs.html` and `checkstyle.html`.
 
<br>

## API Documentation
The API documentation serves to provide information about accessing the REST API, such as providing the URI for requesting a specific resource, provide a description of the request, response and provide an example.

**NOTE: by clicking the arrow next to an example request, you will display an example response for the given request.**

#### **Request URIs:**
 - [GET event?id={id}](#get-eventidid)
 - [GET event/all](#get-eventall)
 - [PUT event/update](#put-eventupdate)
 - [POST event/create](#post-eventcreate)
 - [DELETE event/{id}](#delete-eventid)
 - [GET user/get?email={email}](#get-usergetemailemail)
 - [POST user/create](#post-usercreate)
 - [DELETE user/{email}](#delete-useremail)


<br>

<br>

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

Retrieves a user given an email.

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

Deletes a user provided its email.

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
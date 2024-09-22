# Getting Started

## Setup

1. Install Maven3, JDK17, MySQL8.0.29
2. Run MySQL server with username=root, password=password.
3. In case your mysql user & password is different, change username, password in
   application.properties
4. Compile the package using `mvn clean compile`

## Using the Service and APIs

1. First setup the database. Run all the queries in create_db.sql file on your local MySQL server.
2. Run EventApplication
3. Try out the APIs using postman collection provided under resources.
4. Refer below APIs

## APIs

### 1. Create an Event

API: `POST http://localhost:8080/api/v1/events`

Request Body
```json
{
   "name": "Test Event",
   "eventType": "MOVIE",
   "startDateTime": "2024-12-12 00:00:00",
   "endDateTime": "2024-12-20 23:59:59",
   "availableTickets": 100,
   "venue": "Gurgaon",
   "pricePerTicket": 1000
}
```

Response:

HTTP Code: 200 (OK)

Body:
```json
{
   "eventId": "ef2bb596-a21b-48e7-89c6-5f7e66501639",
   "name": "Test Event",
   "eventType": "MOVIE",
   "startDateTime": "2024-12-12 00:00:00",
   "endDateTime": "2024-12-20 23:59:59",
   "availableTickets": 100,
   "venue": "Gurgaon",
   "pricePerTicket": 1000
}
```

### 2. List Events

API: `GET http://localhost:8080/api/v1/events`

Response:

HTTP Code: 200 (OK)

Body:
```json
[
   {
      "eventId": "ef2bb596-a21b-48e7-89c6-5f7e66501639",
      "name": "Test Event",
      "eventType": "MOVIE",
      "startDateTime": "2024-12-12 00:00:00",
      "endDateTime": "2024-12-20 23:59:59",
      "availableTickets": 100,
      "venue": "Gurgaon",
      "pricePerTicket": 1000
   }
]
```

### 3. Read an Event

API: `GET http://localhost:8080/api/v1/events/{id}`

Example:
`GET http://localhost:8080/api/v1/events/ef2bb596-a21b-48e7-89c6-5f7e66501639`

Response:

HTTP Code: 200 (OK)

Body:
```json
{
   "eventId": "ef2bb596-a21b-48e7-89c6-5f7e66501639",
   "name": "Test Event",
   "eventType": "MOVIE",
   "startDateTime": "2024-12-12 00:00:00",
   "endDateTime": "2024-12-20 23:59:59",
   "availableTickets": 100,
   "venue": "Gurgaon",
   "pricePerTicket": 1000
}
```

### 4. Update an Event

API: `PUT http://localhost:8080/api/v1/events/{id}`

Example:
`PUT http://localhost:8080/api/v1/events/ef2bb596-a21b-48e7-89c6-5f7e66501639`

Request Body

```json
{
   "eventId": "ef2bb596-a21b-48e7-89c6-5f7e66501639",
   "name": "Test Event 1",
   "eventType": "MOVIE",
   "startDateTime": "2024-12-12 00:00:00",
   "endDateTime": "2024-12-20 23:59:59",
   "availableTickets": 100,
   "venue": "Gurgaon",
   "pricePerTicket": 1000
}
```

Response:

HTTP Code: 200 (OK)

Response Body:
```json
{
   "eventId": "ef2bb596-a21b-48e7-89c6-5f7e66501639",
   "name": "Test Event 1",
   "eventType": "MOVIE",
   "startDateTime": "2024-12-12 00:00:00",
   "endDateTime": "2024-12-20 23:59:59",
   "availableTickets": 100,
   "venue": "Gurgaon",
   "pricePerTicket": 1000
}
```

### 5. Delete an Event
API: `DELETE http://localhost:8080/api/v1/events/{id}`

Example:
`DELETE http://localhost:8080/api/v1/events/ef2bb596-a21b-48e7-89c6-5f7e66501639`

Response:

HTTP Code: 200 (OK)

### 6. Check Available tickets for an event
API: `GET http://localhost:8080/api/v1/events/{id}/available-tickets`

Example:
`GET http://localhost:8080/api/v1/events/426e1286-ce1e-4e25-8c80-859ba1c622cb/available-tickets`

Response:

HTTP Code: 200 (OK)

Response Body:
```json
1000
```

### 7. Buy tickets for an event
API: `POST http://localhost:8080/api/v1/events/{id}/buy-tickets`

Example:
`POST http://localhost:8080/api/v1/events/426e1286-ce1e-4e25-8c80-859ba1c622cb/buy-tickets`

Request Body
```json
{
  "userId": "426e1286-ce1e-4e25-8c80-859ba1c622cb",
  "numOfTickets": 1
}
```

Response:

HTTP Code: 200 (OK)

Response Body:
```json
{
  "bookings": [
    {
      "bookingId": "3c464a00-53b6-4f98-82b8-0f9cadc2890a",
      "userId": "426e1286-ce1e-4e25-8c80-859ba1c622cb",
      "eventId": "426e1286-ce1e-4e25-8c80-859ba1c622cb",
      "bookedAt": "2024-09-23T00:59:19.494778+05:30",
      "seat": 100,
      "price": 1000
    }
  ]
}
```

### 8. Get total tickets sold
API: `GET http://localhost:8080/api/v1/events/sold-tickets`

Response:

HTTP Code: 200 (OK)

Response Body:
```json
10
```

### 9. Get tickets sold per event
API: `GET http://localhost:8080/api/v1/events/{id}/sold-tickets`

Example:
`GET http://localhost:8080/api/v1/events/426e1286-ce1e-4e25-8c80-859ba1c622cb/sold-tickets`

Response:

HTTP Code: 200 (OK)

Response Body:
```json
10
```

### 10. Get total revenue

API: `GET http://localhost:8080/api/v1/events/revenue`

Response:

HTTP Code: 200 (OK)

Response Body:
```json
1000
```

### 11. Get revenue per event

API: `GET http://localhost:8080/api/v1/events/{id}/revenue`

Example:
`GET http://localhost:8080/api/v1/events/426e1286-ce1e-4e25-8c80-859ba1c622cb/revenue`

Response:

HTTP Code: 200 (OK)

Response Body:
```json
100
```

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>`
and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove
those overrides.


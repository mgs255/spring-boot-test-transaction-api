
## Basic Test Spring-Boot + Single REST controller

To build & test the application you will need Java 8+ and Maven 3.3+:

`mvn package test`

To run, use either:

`mvn spring-boot:run`

or 

`java -jar target/TransactionApi-0.0.1-SNAPSHOT.jar`


### Usage

REST API Documentation/Test Usage via Swagger Documentation available under 
`http://localhost:8080/swagger-ui.html`

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '[ \ 
   { \ 
     "currency": "GBP", \ 
     "date": "10-05-2018", \ 
     "destAccount": "ABC", \ 
     "sourceAccount": "DEF", \ 
     "value":  423.00 \ 
   }, \ 
   { \ 
     "currency": "USD", \ 
     "date": "11-03-2017", \ 
     "destAccount": "ABC", \ 
     "sourceAccount": "GHI", \ 
     "value":  12.0 \ 
   } \ 
 ]' 'http://localhost:8080/api/transactions/sort?sortField={amount|date}&sortOrder={descend|ascend}'

```



### Limitations:

* There is no security - should be run using Adding some sort of authorisation, e.g: JWT, and over HTTPS  
* Customised error handling so that internal implementation details do not leak
* Additional input validation for non date and amount fields.
* Serialisation of currency to a JSON number is questionable
* Customise location of swagger-ui to be under /api endpoint
 

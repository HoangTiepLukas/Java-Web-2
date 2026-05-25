# Java Web Spring Boot 2
**This project is a Spring Boot REST API using PostgreSQL, Flyway, Spring Data JPA, and Lombok.**

![Data Model](documentation/datamodel.svg)

**The application contains 3 main relational tables:**
- Customer
- Supplier
- Offer
## Technologies
- RESTful API architecture
- PostgreSQL database
- Flyway database migrations
- Spring Data JPA
- DTO mapping
- Maven
- Lombok
## Features

### Customer & Supplier Management
- Create customers with name, email, and password
- Create suppliers with name, email, and password

### Offer Management
- Create offers with:
    - title
    - description
    - price

### Offer Workflow
Customers can accept offers.

Offer statuses:
- CREATED
- ACCEPTED
- IN_PROGRESS
- DELIVERED
- DELIVERY_ACCEPTED
- INVOICED
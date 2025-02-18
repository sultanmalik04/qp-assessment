# Grocery Shop API

A RESTful API for managing a grocery shop with user and admin functionalities. Built with Spring Boot and secured with JWT authentication.

## Features

- User Authentication and Authorization (JWT)
- Role-based access control (Admin/User)
- Grocery item management
- Order processing
- Inventory management

## Prerequisites

- Java 21
- Maven 3.x
- Postman (or any API testing tool)

## Test cases

1. **Register Admin User**
   POST http://localhost:8080/api/auth/register
   Content-Type: application/json
   body:
   {
   "username": "admin",
   "password": "admin123",
   "role": "ADMIN"
   }

2. **Register Regular User**
   POST http://localhost:8080/api/auth/register
   Content-Type: application/json
   body:
   {
   "username": "user",
   "password": "user123",
   "role": "USER"
   }

3. **Authenticate Admin (Get Token)**
   POST http://localhost:8080/api/auth/authenticate
   Content-Type: application/json
   body:
   {
   "username": "admin",
   "password": "admin123"
   }

4. **Add Grocery Item (Admin)**
   POST http://localhost:8080/api/admin/items
   Content-Type: application/json
   Authorization: Bearer YOUR_ADMIN_TOKEN
   body:
   {
   "name": "Apple",
   "price": 1.99,
   "inventory": 100
   }

5. **Add Multiple Grocery Items (Admin)**
   POST http://localhost:8080/api/admin/items
   Content-Type: application/json
   Authorization: Bearer YOUR_ADMIN_TOKEN
   body:
   {
   "name": "Banana",
   "price": 0.99,
   "inventory": 150
   }

6. **Get all grocery items**
   GET http://localhost:8080/api/admin/items
   Content-Type: application/json
   Authorization: Bearer YOUR_ADMIN_TOKEN/

7. **Update Item (Admin)**
   PUT http://localhost:8080/api/admin/items/1
   Content-Type: application/json
   Authorization: Bearer YOUR_ADMIN_TOKEN
   body:
   {
   "name": "Green Apple",
   "price": 2.49,
   "inventory": 75
   }

8. **Delete Item (Admin)**
   DELETE http://localhost:8080/api/admin/items/2
   Authorization: Bearer YOUR_ADMIN_TOKEN

9. **Authenticate User (Get Token)**
   POST http://localhost:8080/api/auth/authenticate
   Content-Type: application/json
   body:
   {
   "username": "user",
   "password": "user123"
   }

10. **View Available Items (User)**
    GET http://localhost:8080/api/user/items
    Authorization: Bearer YOUR_USER_TOKEN

11. **Create Order (User)**
    POST http://localhost:8080/api/user/orders
    Content-Type: application/json
    Authorization: Bearer YOUR_USER_TOKEN
    body:
    {
    "itemIds": [id1, id2]
    }

12. **View User Orders (User)**
    GET http://localhost:8080/api/user/orders
    Authorization: Bearer YOUR_USER_TOKEN

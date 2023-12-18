# Online Book Store is greeting you!

---

This project is designed to demonstrate the basic functionality that can be implemented in an online store
(authorization by jwt, using different levels of access ("ADMIN", "USER"), creation of an assortment of products,
products management, creation of shopping cart items list and placing an order). 

The structure of the project is built according to the principles of REST, 
which makes it possible to quickly scale, supplement and develop the project in the future.

##### You can read more about the project and functionality below.

---

# Project Description

## Technologies:

<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZqRFNAis0vxGXeQDFA2thujnilvYO8eqTKDX5QgJ5APGtLTNQu0-d6rTkb8oSWOdyRyY&usqp=CAU" width="25"/> Java

<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" width="25" /> Spring Boot

<img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" width="25"/> Spring Security

<img src="https://www.baeldung.com/wp-content/uploads/2021/02/lsd-module-icon-1.png" width="25"/> Spring Data JPA

<img src="https://www.freepnglogos.com/uploads/logo-mysql-png/logo-mysql-mysql-logo-png-images-are-download-crazypng-21.png" width="25"/> MySQL

<img src="https://www.liquibase.org/wp-content/themes/liquibase/assets/img/cta-icon.svg" width="30" height="25"/> Liquibase </summary>

<img src="https://oddblogger.com/wp-content/uploads/2021/03/swagger-logo-2.png" width="25"/> Swager

<img src="https://user-images.githubusercontent.com/1204509/79262490-b2012a80-7e91-11ea-82fa-e791f8b4d177.jpg" width="25"/> Lombok

<img src="https://1.bp.blogspot.com/-C5lGqSQuCic/WX39mN-OhdI/AAAAAAAAALU/qUZQdUPTvmInwGSKAYfcZ-QA_PXxhXCXwCLcBGAs/s1600/mapstruct.png" width="25"/> Mapstruct


## Database Architecture

![Database Architecture](https://github.com/Alexsandr-Yablunovskyi/book-store/blob/master/db_architecture.png)

## App abilities

### How can USER interact with app
- **Authentication:**
  - Safely join the store.
  - Log in to discover and buy books.
- **Book management:**
  - Explore the complete collection of available books.
  - Examine detailed information about individual books.
- **Category management:**
  - Navigate through diverse book categories.
  - Review books within a specific category.
- **Shopping Cart management:**
  - Add selected books to shopping cart.
  - Monitor and organize items within the cart.
  - Remove unwanted books from the cart.
- **Order process management:**
  - Complete the purchase of all items in the cart securely.
  - Retrieve historical information on past orders.
- **Order information management:**
  - Review all items included in a particular order.
  - Access detailed views of specific items within an order.
### How can ADMIN interact with app
- **Book management:**
  - Add, update, or delete books from the store's inventory.
- **Category management:**
  - Create, update, or delete book categories.
- **Order management:**
  - Update order status.

## API documentation:
---

### **Authentication Controller:**

| **HTTP method** | **Endpoint**  | **Role** | **Function** |
|:----------------:|:--------------:|:--------:|:-------------|
| POST | /register | ALL | Allows new users to register a new account. |
| POST | /login | ALL | Get JWT tokens for authentication by valid registered user credentials.   |

---

### **Book Controller:** _(CRUD operation for books)_

| **HTTP method** | **Endpoint**  | **Role** | **Function**                       |
|:---------------:|:-------------:|:--------:|:-----------------------------------|
|       GET       |    /books     |   USER   | Get a list of all available books from the DB.     |
|       GET       |  /books/{id}  |   USER   | Get a book by id from the DB.  |
|       PUT       | /{books}/{id} |  ADMIN   | Update a book by id in the DB. |
|      POST       |   /{books}    |  ADMIN   | Create a new book in the DB.   |
|     DELETE      | /{books}/{id} |  ADMIN   | Delete a book by id from DB.   |

---

### **Category Controller:** _(CRUD operation for categories)_

| **HTTP method** |   **Endpoint**    | **Role** | **Function**                         |
|:--------------:|:-----------------:|:--------:|:--------------------------------------|
|      POST      |     /category     |  ADMIN   | Create new category in the DB.        |
|       GET      |     /category     |   USER   | Get all categories from the DB.       |
|       GET      |  /category/{id}   |   USER   | Get a category by id from the DB.       |
|       PUT      |  /category/{id}   |  ADMIN   | Update a category by id in the DB.    |
|    DELETE      |  /category/{id}   |  ADMIN   | Delete a category by id from the DB.     |

---

### **Cart Controller:** _(CRUD operation for shopping cart)_

| **HTTP method** |    **Endpoint**     | **Role** | **Function**                                   |
|:---------------:|:-------------------:|:--------:|:-----------------------------------------------|
|       GET       |        /cart        |   USER   | Get information about items from the logged-up user cart.  |
|      POST       |        /cart        |   USER   | Add a book to user cart.                   |
|       PUT       |  /cart-items/{id}   |   USER   | Update information (book quantity) of the specific cart item. |
|     DELETE      |  /cart-items/{id}   |   USER   | Delete cart item from the logged-up user shopping cart.           |


---

### **Order Controller:** _(CRUD operation for orders)_

| **HTTP method** |           **Endpoint**          | **Role** | **Function**                         |
|:--------------:|:--------------------------------:|:--------:|:-------------------------------------|
|      POST      |             /orders              |   USER   | Create an order from the logged-up user cart items.        |
|       PUT      |        /orders/{orderId}         |  ADMIN   | Update information about order status by orders' id. |
|       GET      |             /orders              |   USER   | Get orders history.                   |
|       GET      | /orders/{orderId}/items/{itemId} |   USER   | Get a list with information about specific order items.                 |
|       GET      |     /orders/{orderId}/items      |   USER   | Get an information about specific item by id from order.            |

---

### Project Setup with Docker

1. Make sure you have JDK
2. Clone [the repository from the GitHub](https://github.com/Alexsandr-Yablunovskyi/book-store)
3. Create an `.env` file with the necessary environment variables (as an example for filling - `.env.sample`). 
4. Run `mvn clean package` command
5. [Install Docker](https://docs.docker.com/get-docker)
6. Run `docker-compose build && docker-compose up` command to build and start the Docker containers
7. The application should be running at http://localhost:8081
8. Swagger is available for testing at http://localhost:8081/api/swagger-ui/index.html#/
   
### Contact

For additional information or inquiries, please contact:
- Email: yablunovskyi.oleksandr@gmail.com
- [Telegram](https://t.me/Alexander_Yablunovskyi)
- [LinkedIn](https://www.linkedin.com/in/oleksandr-yablunovskyi-09b376299/)

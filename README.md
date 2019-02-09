# Cat-Adoption
## Description
This application enables: 
- all users to list cats up for adoption;
- all users to search cats up for adoption by following criteria: color, sex, location, breed and age;
- all users to create an account;
- logged user to add data about cats he/she is giving up for adoption;
- logged user to update data about cats he/she has added;
- logged user to delete data about cats he/she has added;
- logged user to list cats he/she has added;
- admin to approve/delete added/updated content about cats;

Backend of the application is developed in **Spring Boot** and **Spring Security** frameworks using **Hibernate** in 
combination to **Spring Data** and frontend by using **HTML**, **CSS (Bootstrap)** and **AngularJS**. 
Application uses MySQL database to store data.
## Installation
1. Start MySQL server.
2. Create database named _cat_adoption_ by command `CREATE DATABASE cat_adoption`.
2. Clone or download the project and unpack the folder. Navigate to file _application.properties_ and set username, password, 
IP address and port according to your configuration of MySQL server.
3. Navigate to project where pom.xml file is. Start the application using Maven command:
`mvn spring-boot:run`.
4. Open browser and connect to: `localhost:8080`.
5. Create your account or log in as admin using credentials: `admin` for username and `admin` for password.  
If you want to log in already created user account, you can type in one of the following credentials for username and password, respectively:  
`username1`  `password1`  
`username2`  `password2`  
`username3`  `password3`

## REST api
| Method | URL |Request Body (JSON)|Request Parameter(s)| Description | Authority |
|--------|-----|-------------------|--------------------|-------------|-----------|
|GET|`localhost:8080/api/cats`||_colorId_, _sex_, _locationId_, _breedId_, _ageId_, _page_<br>(None of the parameters is required.)|Paginated listing of all cats (approved by admin). Listed parameters enable search by color, sex, location, breed, age and page number, respectively.||
|GET|`localhost:8080/api/cats/{id}/info`<br> _id_-type Numeric (long)|||Retreiving additional data about cat (approved by admin) with specified ID.||
|POST  |`localhost:8080/api/users/login`|user-login*||Login to application.||
|GET  |`localhost:8080/api/users/logout`|||Logout from application.||
|POST  |`localhost:8080/api/users/register`|user-register*||Create an account.||
|GET  |`localhost:8080/api/users/cats`|||Retrieving data about cats created by logged user.|user|
|PUT  |`localhost:8080/api/users/cats/{id}`<br> _id_-type Numeric (long)|cat-update*||Updating data about cat with specified ID.|user|
|POST  |`localhost:8080/api/users/cats`|cat-create*||Creating new cat.|user|
|DELETE  |`localhost:8080/api/users/cats/{id}`<br> _id_-type Numeric (long)|||Deleting cat with specified ID.|admin, user|
|GET  |`localhost:8080/api/users/cats/unapproved-content`|||Retreiving new/updated data about cats.|admin|
|PUT  |`localhost:8080/api/users/cats/{id}/approve`<br> _id_-type Numeric (long)|||Approving new/updated data about cat with specified ID.|admin|
|GET|`localhost:8080/api/ages`|||Retreiving all ages of cats.||
|GET|`localhost:8080/api/breeds`|||Retreiving all breeds of cats.||
|GET|`localhost:8080/api/colors`|||Retreiving all colors of cats.||
|GET|`localhost:8080/api/locations`|||Retreiving all locations of cats.||

#### Formats of JSON objects required in request body
*user-login 

| Field | Type |
|-------|------|
|username|String|
|password|String|

*user-register 

| Field | Type |
|-------|------|
|username|String|
|password|String|
|phone|Numeric (long)|

*cat-update

| Field | Type |
|-------|------|
|id|Numeric (long)|
|name|String|
|sex|String (_male_ or _female_ values)|
|colorId|Numeric (long)|
|locationId|Numeric (long)|
|breedId|Numeric (long)|
|ageId|Numeric (long)|

*cat-create

| Field | Type |
|-------|------|
|name|String|
|sex|String (_male_ or _female_ values)|
|colorId|Numeric (long)|
|locationId|Numeric (long)|
|breedId|Numeric (long)|
|ageId|Numeric (long)|
|image|byte[]|
|description|String|



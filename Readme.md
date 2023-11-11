**Project Description** :

Backend Service For Order List/Take/Place.

**Technology**:

Java,SpringBoot,MySQL,Docker

**Prerequisite** :

Docker Installed .

Google Maps Account and valid Api Key (You need to put this API key in src/main/java/com/project/order/util/OrderUtil.java class in API_KEY String value (currently empty string)).Doing so
will automatically append it in api string.(Otherwise empty string will result in api request denied)

**Run** :

./start.sh

Above command will trigger  docker-compose up --build -d if docker is installed in environment , otherwise message to install the same. if docker is installed , it will refer to docker-compose.yml
file and first pull mysql db and install and create db schema and then build the whole project and start the backend service (with mysql as dependency). Since we are using ORM , at start up 
necessary tables will be automatically created. 

**API** :
Service running on default port 8080

As per document(backend.md), following endpoints and necessary methods have been implemented in the API.

1. /orders (POST Call) :  this will create a new order and return response with id(UUID String),distance(calculated using Google Maps distance API), status(Unassigned) . Necessary checks are in place
like validating input coordinates etc.
2. /orders/{id} (PATCH Call) :  this will assign any order(given order id) and mark and return success status. Race Condition is being kept in mind while writing the business logic. Incase of
unsuccessful assignment , proper error messages displayed.
3. /orders?page=:page&limit=:limit (GET Call) : returns list of present orders in system in paginated way. min page value 1 and default value 1 . Limit min value 1 and dafault 10. Other checks are in place to validate 
page and limit value and type. In case of no orders in system , returns empty array

Only the endpoints and necessary methods specified in the backend.md document have been implemented in the API 
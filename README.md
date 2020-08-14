# Notice Board
  
  This solution is made by Sebastian Porling.
  
  ## Project
  
  This project uses maven for getting dependencies and packaging.
  You can import this project and compile/run with maven.
  
  ## Execution
  
  You can find this project up on:
  
  https://notice-board-sp.herokuapp.com/
  
  or by using your IDE or executing the following while in the root directory:
  
  ```bash
  java -jar session-1.jar
  ```
  
  ## Motivation and explanation
  
  This project utilizes **H2 SQL** in memory database, 
  we use JPA to create the database and tables and use
  the **data.sql** file in resources to generate the **Notice**, **Comment** and **User** tables.
  Spring Boot finds the **data.sql** file at the start of execution and executes it.
  
  The 'backend' have three controllers, one repository and one data object/entity.
  It is the entity that decides how the tables **Notice**, **Comment** and **User** will look like.
  They have the following properties:
  
  **User:**
  
  |name|type|property|
  |----|----|----|
  |id|Long|primary|
  |username|String|unique|
  |password|String|non null|
  
  **Notice:**
  
  |name|type|property|
  |----|----|----|
  |id|Long|primary|
  |author|User|non null|
  |content|String|non null|
  |comments|Comments||
  |date|Date|non null|
  
  **Comment:**
  
  |name|type|property|
  |----|----|----|
  |id|Long|primary|
  |text|String|non null|
  |author|User|non null|
  
  The **WebController** handles the request for the index page.
  The **BasicErrorController** handles 404, 500 and some other errors and displays a page.
  
  The two controller that does the most work and is the interesting of the bunch is 
  **NoticeBoardController** which takes care of all requests for the notice board. 
  And the **UserController** which handles authentication and sessions. The sessions 
  are kept in the singleton instance **SessionKeeper** which has a reference to the session and the user id.
  
  |name|method|return|comment|
  |----|----|----|----|
  |"/"|GET|index.html|Just returns the user to the index page|
  |"/login"|POST|{null, message}|registers the session and returns cookies|
  |"/register|POST|{null, message}|registers the user and session and returns cookies|
  |"/logout"|POST|{null, message}|removes session and invalidates the session|
  |"/notice/all"|GET|{notices/null, message}|Returns all the notices|
  |"/notice/{id}"|GET|{notice/null, message}|Gets notice by id|
  |"/notice/update|PATCH|{notice/null, message}|Modifies notice|
  |"/notice/create"|POST|{notice/null, message}|Creates new notice|
  |"/notice/comment/{id}"|POST|{notice/null, message}|adds comment to notice|
  |"/notice/{id}|DELETE|{null, message}|deletes notice by id|
  
  The update, create, comment and delete requires that the user has a registered session in the **SessionKeeper**.
  Otherwise they will recieve null!
  
  For the 'frontend' we generate the index page with thymeleaf the first time it is loaded.
  All the actions happens through javascript after that.
  The file **fetch_api.js** does most of the work.
  Adds a new row to the table and the database. Most through jQuery Ajax requests.
  So everything happens asynchronously which gives a more smooth experience.
  The forms are in the **add modal**, **edit modal**, **comment modal**, **register modal** and **login modal**. 
  A modal is a fancy popup through bootstrap.

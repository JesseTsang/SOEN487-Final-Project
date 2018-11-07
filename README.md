# SOEN487-Final-Project

## PM 4 Deliverable

### Installation Steps:

**!! IMPORTANT !!**

**Make Sure Clone/Download the Project under Your User Home Directory**
**MySql server is required for this project, make sure it runs properly**

* For DB setup
	* Please have local MySql server instance running
      * open MySql in terminal and create a new database: `create database soen487_w18_team07_logging;`
	* Open IDE (NetBeans/OpenESB)
     * Navigate to `src/main/resources/sql/` run `logger.sql`, select new database connection
      * Driver -> MySQL (Connector/J driver), click next
      * `Database` -> soen487_w18_team07_logging, `Password` -> `your password for MySQL server`
       * Test Connection, if it says "Connection Succeeded", click Next
       * Click Next -> Finish
     * Navigate to `src/main/resources/db.properties` change the `USER` and `PASSWORD` attributes to your mysql user and password.
     
* To run Project
    * Run GlassFish server and clean build the project.
    * Clean & Build the project.
    * Run project, url should be <http://localhost:8080/soen487-w18-team07/>

* Documentation
 * PDF version located in doc/soen487-w18-team07_doc.pdf


WGU C195 Performance Assessment


- Appointment Scheduling System Application (Java) - 

The purpose of this application is for the scheduling and tracking of appointments within a company by the user. Appointments are all scheduled according to company business hours (8:00am to 10:00pm EST) and are displayed according to the users’ time zone. Each appointment is saved to SQL database where it is retrieved and displayed on the applications primary page either by current week, month, or all appointments. Customer information can similarly be created and displayed using the database. Both Customer and Appointment data can be updated and/or removed from the database. Three distinct reports can be run using the information in the database, their results can be viewed in the application.

Author: Keegan Brewster
Contact: kbrews5@wgu.edu
Version: 1.0.0.0
Date: 10/10/2021
IDE: IntelliJ IDEA 2021.2(Community Edition)
JDK: Java SE 15.0.2
JavaFX: JavaFX-SDK-11.0.2
JDBC: mysql-connector-java-8.0.25


- How to Run -
*Assumes use of IntelliJ IDEA*

Ensure the MySQL database is installed and the time setting for the db is set to UTC
Follow these steps to ensure the UTC is set correctly if the db is installed locally:
    1. Edit my.ini file usually found here C:\ProgramData\MySQL\MySQL Server8.0\my.ini
    2. Find [mysqld] section
    3. Add the following line, including the quotations
        default-time-zone="+00:00"
    4. Restart DB server
Create the database tables and insert data using the provided documents.
    C195_db_ddl.txt
    C195_db_dml.txt
        Note: Edit the Start & End dates for appointments to more logically reflect current date/times.
Ensure SDK 11.0.2 & JDK 15.0.2 are downloaded on the local machine and added to the Libraries in Project Structure.
Open the project in IntelliJ and update the paths for JavaFX and the MySQL connector.
Edit Run Configuration to include the path of the local SDK in the VM Options.
String entered for the path should look similar to the example below.
    --module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
Set main class to "view.SchedulingSystem"
Verify that you’re able to connect to the MySQL database using the connection information in “/src/db/Database.java”
Run the project with "SchedulingSystem/src/view/SchedulingSystem" as the main class; or compile and run the program using command line.


- Additional report for part A3f - 

The third report allows the user to select a country from the countries present in the database.
Running the report will display each existing appointment in the database that is associated with a customer with an address in the specified country.
The report effectively displays all appointments for a selected country.
If the database is not populated with multiple customers and appointments per country, the report will appear to only display appointments for a single customer.




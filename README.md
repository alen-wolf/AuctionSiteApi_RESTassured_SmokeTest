# AuctionSiteApi_RESTassured_SmokeTest

- This app was designed to test a emualted user flow of the https://abh-auction.herokuapp.com/ app that was made for the AtlantBh Internship with RESTassured api test libraries. It follows a similar path to the postman collection tests. Using the Service Object pattern and Assertions to assure full api service coverage.  

- In order to run the tests you will need to have the following installed: Maven, JDK 8

- Maven and JDK 8 have to be configured in the PATH directory and a MAVEN_HOME and JAVA_HOME system variable applied that link to the folder containing their respective bin folders
- If you encounter an error such as: YOU MUST BE USING JRE > JDK. Than you need to move the JDK location path in the PATH directory before the oracle/JRE one.

# Run Commands:

Following commands work both in linux and windows if all the criteria above has been met:

  1. CD inot the project folder 
  2. mvn test -DsuiteXmlFile=testng.xml -DtestNames=smoke
  3. cd \target\surfire-reports
  4. index.html or cat index.html for linux 
  

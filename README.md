
A banking Webapp  for managing transactions between account of users and contacts subscribed .

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
- Java 17
- Maven 3.9.4
- SpringBoot 6.1.1
- Spring Tools 4

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://java.tutorials24x7.com/blog/how-to-install-java-17-on-windows

2.Install Maven:

https://maven.apache.org/install.html

3.Install Spring Tools for Eclipse

https://www.eclipse.org/community/eclipse_newsletter/2018/february/springboot.php

4.Install MySQL WorkBench or other BDD

https://www.mysql.com/products/workbench/

5. Upload structure BDD of file Data.sql in SQL BDD and  then add your info connexion BDD in environment variable of your local system 


### Running App

Post installation of Java, Maven and Spring Tools 4, and BDD you will have to run app
 with your CLI or with dashboard of Spring Tools.
 Then move the JAR  generated at root of project , Spring will  run with properties in folder /config for ENV "prod" and finally  run in you CLI: java -jar webapp-1.0.0.jar
 for app PROD 

Finally, you will be ready to  use webapp and request 
The link homepage  is :http://localhost:8080/home

### MORE INFORMATIONS


#### Information data and structure of BDD PayMyBuddy

https://github.com/marie-lourdes/leperlier-marie-lourdes-app-web-pay-my-buddy-P6/blob/09fd20ce6eeaba5388da45664ddc375d33330f03/Documents%20annexe/Model-Physique-Donnes-SQL.png

#### Class Diagram  application

https://github.com/marie-lourdes/leperlier-marie-lourdes-app-web-pay-my-buddy-P6/blob/6c084a19f0f7b4e6516ffd56c72738d924d1c14e/Documents%20annexe/Unbenannt.png
### Testing

 For testing application use:
`run as` , then, 'Maven test' for unit test and maven verify for integration test

 For testing request:
See the file of logging in folder log of project or use Postman after running application

### Debugging
 For debbuging
Use  Maven CLI 'mvn test or mvn verify  


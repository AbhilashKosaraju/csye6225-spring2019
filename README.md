# CSYE 6225 - Spring 2019

## Team Information

| Name | NEU ID | Email Address |
| --- | --- | --- |
|Keyur Donga|001220693|donga.k@husky.neu.edu|
|Abhilash Choudary Kosaraju |001205393|kosaraju.a@husky.neu.edu|
|Shruti Krishnaji Kulkarni|001852741|kulkarni.shru@husky.neu.edu|
|Vrushali Muralidhar Patil|001200648|patil.vr@husky.neu.edu|

## Technology Stack

| Operating System | Programming Language | Database | Framework | UI Framework |
| --- | --- | --- | --- | --- |
| Linux Based Operating System: Ubuntu | JAVA | MySQL, AWS S3, AWS RDS | Spring MVC, Hibernate | None |

## Build Instructions

Web Application:
The noteapp web API is being developed and build in the Intellij IDE. 
Postman tool is being used as API client for testing the web API 
developed. 
The user management aspect of the noteapp has been availed.
The CRUD operations on the noteapp has been created.
Adding attachements to the created notes has been enabled.
Different profiles to run the application on local memory and on AWS S3 bucket has been established.

Infrastructure as Code:
A new cloud formation template in json format has been used to setup application resources.
A shell script has been employed to create and configure required application resources using AWS CloudFormation.
A shell script to delete the application CloudFormation stack has been created.
The application stack includes, EC2 instance, DynamoDB Table, S3 Bucket and RDS Instance with the specified configurations to support the REST API deployment. 
Security groups for Web servers and DB Servers have been created as per the requirements. 

## Deploy Instructions


## Running Tests

Unit tests in the JUnit framework have been developed to verify various 
working functionalities( REST endpoints) of the noteapp Web API. 

## CI/CD



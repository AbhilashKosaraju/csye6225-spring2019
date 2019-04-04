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
Packer build template has been updated to install the Unified CloudWatch Agent in the AMIs.
systemd service has been employed to start, stop and restart the CloudWatch agent.
Amazon Simple Notification Service (Amazon SNS) topic creation and Authentication and Access Control for AWS Lambda to manage access to the Lambda API has been added to the net.
Auto Scaling Group have been configured and AutoScaling Policies have been added to support auto scaling of EC2 instances.
Application Load Balancer has been added to the cloud formation template to load balance the ECS instances in the auto-scaling group.
Load balacer has been configured to use the Secure Socket Layer certificate to protect the web application.
AWS WAF has been deployed to the application load balancer. 

Steps : 
- Run the "csye6225-aws-cf-policies.sh", a bash script as a part of cloudformation service for establishing appropriate policies to the user roles in AWS IAM. 
- Run the "csye6225-aws-cf-create-stack.sh", a bash script as a part of cloudformation service to provision network resources for the web applicaiton. 
- Run the "csye6225-aws-cf-create-application-stack.sh", a bash script as a part of cloudformation service to creation application specific resources on the cloud for the web application.

## Deploy Instructions
The note app developed is being deployed with AWS CodeDeploy. Code Deploy Agent from us-east-1 has been added to the centos-ami-template.json to install the agent in the AMI being built. AWS CodeDeploy appspec.yml file has been created and placed in the root of the noteapp repository to enable the deployment of the web application on EC2 instances. An S3 bucket with the specified naming conventions has been created in AWS to hold the deployed files on cloud.

## Running Tests

Unit tests in the JUnit framework have been developed to verify various working functionalities( REST endpoints) of the noteapp Web API. 
Steps:
- Navigate to the Junit test file in the application hierarchy.
- Run the available Junit test cases on Junit framework.

Penetration tests spaning various security aspects have been carried out with three attack vectors on the kali Linux distrubution.
Steps: 
- Login to the kali Linux distribution.
- Select the required Penetration testing tool/technique from the applications.
- Carryout the test case in terminal or with the GUI of the penetration testing tool.

## CI/CD
Continuous integration and continuous delivery is being achieved with CircleCI platform. 
Appropriate user roles with necessary permissions and policies have been attached to circleci user in AWS. 
The Noteapp repository, a repository for the AMI repository have been linked to CircleCI for triggering automatic builds with curl commands. 
The AWS credentials and the bucket name for sending the builds have been added to the repository environemnt variables.  

Steps: 
- Naigate the csye6225-spring2019 repo. 
- Execute the curl command to initiate the builds on the Circle CI.

curl -u 0a1d67cdO_PERSONAL_OR_PROJECT_TOKEN_cdbc356b0f5 \
    -d build_parameters[CIRCLE_JOB]=build \
    https://circleci.com/api/v1.1/project/github/<Github Username>/csye6225-spring2019/tree/master

- Run the curl command from the other repositories to initiate the builds necessary for supporting all the functionalites in the Noteapp.
 










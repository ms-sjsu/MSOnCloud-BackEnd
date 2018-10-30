MS On Cloud – The NextGen Storage Service

Details
Project 1: MS On Cloud – The NextGen Cloud Storage
University Name: San Jose State University
Course: Cloud Technologies
Professor: Sanjay Garje
ISA: Anushri Srinath Aithal
Student: Mukesh R Sahay

Introduction
MS On Cloud, is a 3-tier web application that allows users to upload, store and download their files on AWS cloud.
After visiting the website https://msoncloud.com, user will have to register using their Email ID to use the storage service provided by MS On Cloud. Once registered the user can login to the website using his Email Id and password. Also a user can sign in using the social logins – Facebook and Google.
MS On Cloud is a highly available, reliable and scalable 3-tier web application utilizing the services provided by AWS.
Supported user actions and handling of those actions by the application:
1.	Upload the files (less than 10MB) – The application will upload and save the file to a S3 bucket.
2.	Download the files – The application will be using Cloud Front to access and download the files.
3.	Delete the files – The file be internally deleted by the application from the S3 bucket where it was uploaded.
4.	Overwrite/Edit the files – User will have to upload a file with the same name that he wants to overwrite and the application will upload the newer version of the file to S3.
5.	Admin Panel – If a user is an administrator, he will have the access to the Admin Panel.
a.	View the list of all files uploaded by all the users.
b.	Delete/Download files – Administrator can keep monitoring the actions done by other users and can take necessary action to maintain the security and integrity of the application.
c.	Administrator will get a text message and email whenever the S3 bucket is updated using the SNS service. Also the transaction log will be saved in the CloudWatch.




Architecture Diagram:
 


List of supported features and their implementation:
1. Register User: User will have to register use the service provided by the application. User information is stored in AWS RDS MySQL DB with Email ID as the primary key. User with an Email ID can register only once.
2. Sign In: User can sign in to the application using the details provided during registration. User can also use their Facebook or Google sign in to login to the application. User with invalid Email ID/Password will not be allowed to login.
3. Admin Panel: Admin panel will be available to the user if he is an administrator. Application is saving the user role in the database in users table. Also there is a REST API support to create an admin user, which can be used by the owner to grant admin role to any user. This view will list all the files uploaded by all the users. Admin can download/delete any file from the system.
4. My Account: This is the Home page for any user. This will list all the files with file name, file description, file created time and file updated time stamps. User will be able to upload, delete and download the file. Also link to the admin panel will be available and will be accessible by the user only if he is an administrator.
5. File Upload: Files with the size less than 10MB will be allowed to be uploaded to the S3 bucket.
6. File Download: User can download the files which will be done via cloud front.
7. Update/Overwrite file: User will have to upload a file with the same name that he wants to overwrite and the application will upload the newer version of the file to S3.
8. File Delete: User can delete the selected file from the system.
9. Log Out: User can log out of the application using log out button.

AWS Components Utilized
1. EC2: For hosting the Web and Application server.
2. ELB: Elastic Load Balancer is used to handle the load and route it to different EC2 instances.
3. Lambda: Used the lambda function to send email on S3 bucket update and also to upload the logs to Cloud Watch.
4. Auto Scaling Group: Used to support the highly available and scalable web application.
5. CloudFront: Cloud Front is configured to connect to S3 and is used to download the files.
6. S3: S3 bucket is used to store the user files. 
7. S3 Transfer Acceleration: S3 Transfer acceleration is enabled for the faster file uploads to the S3 bucket.
8. R53: DNS to resolves the IP address for the application domain.
9. Cloud Watch: This is used to store the logs on S3 update.
10. SNS: Simple Notification Service is used to send text message on S3 update and Code Commit.
11. SES: Simple Email Service is used to send email on S3 bucket update.
12. Elastic Beanstalk: Used to deploy the application and used as the target for CI/CD pipeline.
13. Code pipeline: Used to deploy the changes to the system.
14. Code Commit: This is used as the version control and code repository (code is also uploaded to the GitHub).
15. Standard-IA and Amazon Glacier: Used as a part of the lifecycle policy configured for the S3 bucket - move the files older than 75 days to Standard-IA and to Glacier after 365 days.

Screenshots

Home Page
 
Register
 

 
Sign In
 
My Account
 

 
When non admin user tries to access the Admin Panel:
 

Admin Panel
 


GitHub Repository:
FrontEnd: https://github.com/ms-sjsu/MSOnCloud-FrontEnd
BackEnd: https://github.com/ms-sjsu/MSOnCloud-BackEnd
Database Query:
CREATE database msoncloud;
use msoncloud;
-- Create table
create table UserInfo
(EmailId VARCHAR(36) not null,
FirstName VARCHAR(36) not null,
LastName VARCHAR(36) not null,
UserRole VARCHAR(1) not null,
Password VARCHAR(128) not null,
PRIMARY KEY (EmailId)) ;

create table UserFiles
(EmailId VARCHAR(36) not null,
FirstName VARCHAR(36) not null,
LastName VARCHAR(36) not null,
FileName VARCHAR(128) not null,
FileDescription VARCHAR(128),
FileCreatedTime TIMESTAMP,
FileUpdatedTime TIMESTAMP) ;

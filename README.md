# Video Demo
Link [here](https://www.youtube.com/watch?v=C_L34J3gVt0)

# What is needed to run this project
- Postman
- MSSQL server
- Nextjs - can be installed with npm install
- You will need access to the frontend repo. Linked below.

The frontend will be stored on this repo [here](https://github.com/horiaomar25/SchoolStaffRecruitmentPlatform-Frontend)


# Step 1: Create database in MSSQL called SchoolStaffRecruitmentDB
![Image](https://github.com/user-attachments/assets/17b24bfb-546e-498d-9f34-3a527ae928aa)

# Step 2: Open up this SpringBoot application
Enter your credentials to connect the database to the project.

# Step 3: Run the SpringBoot project to create the tables of your database
Make sure the following is the same:
![Image](https://github.com/user-attachments/assets/ea299b15-c9c1-457d-b3f7-c1a78d50bea2)

Once you have created your tables, change it to update:
![image](https://github.com/user-attachments/assets/6c0fd632-79e5-4f8e-864e-285c121749eb)

# Step 4: Copy and paste the insert statements into MSSQL for your database
The Insert statements are in the InsertStatements.txt. Keep them in the same order.

# Step 4: Open up Postman
Create 3 POST Requests:
![image](https://github.com/user-attachments/assets/2377ac42-623a-4496-982b-964c25d3c7ce)

# Step 5: Register Request

This is the needed url : http://localhost:8080/api/v1/auth/register

In the ProfileJSONData.txt file you find the JSON object to paste and the URL needed.
![image](https://github.com/user-attachments/assets/56938530-514e-4d47-b9e6-5ef271bbeea2)

This is the expected results
![image](https://github.com/user-attachments/assets/690f8df9-2084-4412-99e4-da33e877b0fd)

# Step 5: Login Request to get the token to create our dummy profile from the json object
To login with our now registered user, you must enter this url: http://localhost:8080/api/v1/auth/login

with expected response:
![image](https://github.com/user-attachments/assets/d0402d8d-e769-46cd-94b8-37262afadd18)



# Step 6: Create our dummy profile with CreateProfileData POST request:
In the ProfileJSONData you will find the Profile json data:

![image](https://github.com/user-attachments/assets/94e16dd6-7aae-4ba5-980d-a77c2fd89e04)

Copy and paste this JSON data into the body of our CreateProfileData request with the url: http://localhost:8080/api/v1/profile/create
![image](https://github.com/user-attachments/assets/9967e398-3380-4e4a-b754-4e07a710bfb2)

# Step 7 Copy the token from the Login request and add to the authorization of the CreateProfileData request

You must copy the token in the response body of the login request excluding the quotation marks like below:
![image](https://github.com/user-attachments/assets/24fb3b96-2704-4056-a14c-77682d9e0427)

Go back to the CreateProfileRequest and paste the BearerToken option in the Authorization tab.
![image](https://github.com/user-attachments/assets/4012659c-8c9b-4404-a6af-c2dae8cfd5ed)

If done successfully you should recieve this response:
![image](https://github.com/user-attachments/assets/465564b2-7914-4022-9e36-92cdfe977383)

# Step 8 Now start up the Frontend
The frontend will be stored on this repo [here](https://github.com/horiaomar25/SchoolStaffRecruitmentPlatform-Frontend)
Instructions on how to run the frontend in the README

# apoc

To Create a repo

echo "# poc" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/sekharvy/poc.git
git push -u origin main


…or To push an existing repository from the command line
git remote add origin https://github.com/sekharvy/poc.git
git branch -M main
git push -u origin main


To Run Mocking Prject.

1. Import demo maven project into intelliJ 
2. Open Terminal and run the command to build the project > ./gradlew clean build 
3. Run the command to up the mocking services > ./gradlew bootRun   


To Run Test cases written in RestAssured using Testng unit testing framework.

1. Import Test maven project
2. Navigate to src/test/java/org/example folder
3. Open any Test classes written in testNG
4. Select any test class name ex - "FolderPermissionControllerTest" , right clik and select Run..., to run all test cases from that class
5. To run a specific class , select test case ex: "testAddPermissionSet" , right click and run the test case

   
Note : 
To select run the spark solution, Need to have AWS account and setup IAM policies to run Glue job and use specific S3 bucket for usage.
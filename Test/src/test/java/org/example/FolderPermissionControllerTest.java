package org.example;


import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FolderPermissionControllerTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/folders";
    }

    @Test
    public void testAddFolder() {
        String requestBody = "{\"name\": \"Folder A\"}";

        given()
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .body(equalTo("Folder added successfully"));
    }

    @Test
    public void testAddPermissionSet() {
        String requestBody = "{\"name\": \"Read Permissions\"}";

        given()
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/permissions")
                .then()
                .statusCode(201)
                .body(equalTo("Permission set added successfully"));
    }

    @Test
    public void testAssociatePermissionWithFolder() {
        // First, add a folder
        String folderRequestBody = "{\"name\": \"Folder B\"}";
        given()
                .body(folderRequestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/")
                .then()
                .statusCode(201);

        // Then, add a permission set
        String permissionSetRequestBody = "{\"name\": \"Editor Permissions\"}";
        given()
                .body(permissionSetRequestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/permissions")
                .then()
                .statusCode(201);

        // Finally, associate the permission set with the folder
        given()
                .header("Content-Type", "application/json")
                .when()
                .post("/1/permissions/1") // Assuming folder ID is 1 and permission set ID is 1
                .then()
                .statusCode(201)
                .body(equalTo("Permission set associated with folder successfully"));
    }

    @Test
    public void testAssociateNonExistingPermissionSetWithFolder() {
        String folderRequestBody = "{\"name\": \"Folder C\"}";
        given()
                .body(folderRequestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/")
                .then()
                .statusCode(201);

        // Attempting to associate a non-existing permission set with the folder
        given()
                .header("Content-Type", "application/json")
                .when()
                .post("/1/permissions/999") // Permission set ID 999 does not exist
                .then()
                .statusCode(404)
                .body(equalTo("Permission set not found"));
    }

    @Test
    public void testAssociatePermissionSetWithNonExistingFolder() {
        String permissionSetRequestBody = "{\"name\": \"Admin Permissions\"}";
        given()
                .body(permissionSetRequestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/permissions")
                .then()
                .statusCode(201);

        // Attempting to associate the permission set with a non-existing folder
        given()
                .header("Content-Type", "application/json")
                .when()
                .post("/999/permissions/1") // Folder ID 999 does not exist
                .then()
                .statusCode(404)
                .body(equalTo("Folder not found")); // This response needs to be added to your controller
    }
}
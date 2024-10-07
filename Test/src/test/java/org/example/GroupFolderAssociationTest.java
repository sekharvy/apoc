package org.example;


import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GroupFolderAssociationTest {

    @BeforeClass
    public void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080/api/folders";
    }

    // Test Group-Folder Association
    @Test
    public void testAssociateGroupWithFolder() {
        String requestBody = "{\"groupId\": 1, \"folderId\": 1}";

        given()
                .pathParam("folderId", 1)
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/{folderId}/groups")
                .then()
                .statusCode(201)
                .body(equalTo("Group associated with folder successfully"));
    }

    @Test
    public void testAssociateGroupWithAnotherFolder() {
        String requestBody = "{\"groupId\": 1, \"folderId\": 2}";

        given()
                .pathParam("folderId", 2)
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/{folderId}/groups")
                .then()
                .statusCode(400) // Expecting a 400 Bad Request
                .body(equalTo("A group can only be associated with one folder"));
    }


    @Test
    public void testUserMembershipInMultipleGroups() {
        String requestBody = "{\"userId\": 1, \"groupId\": 1}";

        given()
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/users/1/groups") // Assuming an endpoint to add users to groups
                .then()
                .statusCode(201)
                .body(equalTo("User added to group successfully"));

        // Check permissions based on group association
        verifyUserPermissions(1, "folderId", "read"); // Replace with appropriate logic to verify permissions
    }


    @Test
    public void testReadPermissions() {
        // Test Read Permissions
        given()
                .pathParam("userId", 1)
                .pathParam("folderId", 1)
                .when()
                .get("/users/{userId}/folders/{folderId}/files")
                .then()
                .statusCode(200)
                .body("canEdit", equalTo(false)); // Ensure they cannot edit or add files
    }

    @Test
    public void testEditorPermissions() {
        // Test Editor Permissions
        given()
                .pathParam("userId", 2)
                .pathParam("folderId", 1)
                .body("{\"fileName\": \"test.txt\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/users/{userId}/folders/{folderId}/files")
                .then()
                .statusCode(201)
                .body(equalTo("File added successfully"));

        // Ensure they cannot delete the folder
        given()
                .pathParam("userId", 2)
                .pathParam("folderId", 1)
                .when()
                .delete("/users/{userId}/folders/{folderId}")
                .then()
                .statusCode(403); // Expecting Forbidden
    }

    @Test
    public void testAdminPermissions() {
        // Admin Permissions
        given()
                .pathParam("userId", 3)
                .pathParam("folderId", 1)
                .body("{\"fileName\": \"delete.txt\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("/users/{userId}/folders/{folderId}/files")
                .then()
                .statusCode(201)
                .body(equalTo("File added successfully"));

        // delete file
        given()
                .pathParam("userId", 3)
                .pathParam("folderId", 1)
                .pathParam("fileId", 1) // Assuming fileId is known
                .when()
                .delete("/users/{userId}/folders/{folderId}/files/{fileId}")
                .then()
                .statusCode(204); // Successful deletion
    }

    // Access Control
    @Test
    public void testAccessControlBasedOnGroup() {
        // User in "read" group attempting to delete a file
        given()
                .pathParam("userId", 1)
                .pathParam("folderId", 1)
                .pathParam("fileId", 1)
                .when()
                .delete("/users/{userId}/folders/{folderId}/files/{fileId}")
                .then()
                .statusCode(403); // Expecting Forbidden

        // User in "admin" group attempting to delete a file
        given()
                .pathParam("userId", 3)
                .pathParam("folderId", 1)
                .pathParam("fileId", 1)
                .when()
                .delete("/users/{userId}/folders/{folderId}/files/{fileId}")
                .then()
                .statusCode(204); // Successful deletion
    }

    // TO Do
    private void verifyUserPermissions(int userId, String folderId, String expectedPermissionSet) {
        // Check user permissions based on group associations
        // This might involve calling an endpoint that checks user permissions
    }
}
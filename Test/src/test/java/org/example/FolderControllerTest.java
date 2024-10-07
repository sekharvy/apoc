package org.example;


import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FolderControllerTest {

    @BeforeClass
    public void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080/api/folders";
    }

    @Test
    public void testGetFolder() {
        RestAssured.given()
                .pathParam("folderId", 1)
                .when()
                .get("/{folderId}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Sample Folder"));
    }

    @Test
    public void testAssociateGroup() {
        String requestBody = "{\"id\": 1, \"name\": \"Editors\"}";

        RestAssured.given()
                .pathParam("folderId", 1)
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/{folderId}/groups")
                .then()
                .statusCode(201)
                .body(Matchers.equalTo("Group associated with folder successfully"));
    }

    @Test
    public void testRemoveGroup() {
        RestAssured.given()
                .pathParam("folderId", 1)
                .pathParam("groupId", 1)
                .when()
                .delete("/{folderId}/groups/{groupId}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSetGroupPermissions() {
        String requestBody = "{\"name\": \"editor\", \"actions\": [\"add\", \"delete\"]}";

        RestAssured.given()
                .pathParam("groupId", 1)
                .body(requestBody)
                .header("Content-Type", "application/json")
                .when()
                .post("/groups/{groupId}/permissions")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("Permission set for group"));
    }
}
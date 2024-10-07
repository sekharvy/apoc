package com.example.demo;

import com.example.demo.Folder;
import com.example.demo.PermissionSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/folders")
public class FolderPermissionController {

    // In-memory storage for folders and their associated permission sets
    private final Map<Integer, Folder> folders = new HashMap<>();
    private final Map<Integer, PermissionSet> permissionSets = new HashMap<>();
    private int folderIdCounter = 1;
    private int permissionSetIdCounter = 1;

    // Endpoint to add a new folder
    @PostMapping
    public ResponseEntity<String> addFolder(@RequestBody Folder folder) {
        folder.setId(folderIdCounter++);
        folders.put(folder.getId(), folder);
        return new ResponseEntity<>("Folder added successfully", HttpStatus.CREATED);
    }

    // Endpoint to add a new permission set
    @PostMapping("/permissions")
    public ResponseEntity<String> addPermissionSet(@RequestBody PermissionSet permissionSet) {
        permissionSet.setId(permissionSetIdCounter++);
        permissionSets.put(permissionSet.getId(), permissionSet);
        return new ResponseEntity<>("Permission set added successfully", HttpStatus.CREATED);
    }

    // Endpoint to associate a permission set with a folder
    @PostMapping("/{folderId}/permissions/{permissionSetId}")
    public ResponseEntity<String> associatePermissionWithFolder(@PathVariable int folderId, @PathVariable int permissionSetId) {
        Folder folder = folders.get(folderId);
        PermissionSet permissionSet = permissionSets.get(permissionSetId);

        if (folder == null) {
            return new ResponseEntity<>("Folder not found", HttpStatus.NOT_FOUND);
        }

        if (permissionSet == null) {
            return new ResponseEntity<>("Permission set not found", HttpStatus.NOT_FOUND);
        }

        folder.setPermissionSetId(permissionSetId);
        return new ResponseEntity<>("Permission set associated with folder successfully", HttpStatus.CREATED);
    }
}
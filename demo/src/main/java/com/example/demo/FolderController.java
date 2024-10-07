package com.example.demo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/folders")

public class FolderController {

        @GetMapping("/{folderId}")
        public ResponseEntity<Map<String, Object>> getFolder(@PathVariable int folderId) {
            Map<String, Object> folder = new HashMap<>();
            folder.put("id", folderId);
            folder.put("name", "Sample Folder");
            return new ResponseEntity<>(folder, HttpStatus.OK);
        }

        @PostMapping("/{folderId}/groups")
        public ResponseEntity<String> associateGroup(@PathVariable int folderId, @RequestBody Map<String, Object> group) {
            return new ResponseEntity<>("Group associated with folder successfully", HttpStatus.CREATED);
        }

        @DeleteMapping("/{folderId}/groups/{groupId}")
        public ResponseEntity<String> removeGroup(@PathVariable int folderId, @PathVariable int groupId) {
            return new ResponseEntity<>("Group removed from folder", HttpStatus.NO_CONTENT);
        }

        @PostMapping("/groups/{groupId}/permissions")
        public ResponseEntity<String> setGroupPermissions(@PathVariable int groupId, @RequestBody Map<String, Object> permissions) {
            return new ResponseEntity<>("Permission set for group", HttpStatus.OK);
        }
    }


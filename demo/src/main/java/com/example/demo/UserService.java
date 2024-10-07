package com.example.demo;

import com.example.demo.Folder;
import com.example.demo.Group;
import com.example.demo.PermissionSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Mock data storage
    private final List<Group> groups; // Assume this is populated
    private final List<Folder> folders; // Assume this is populated
    private final List<PermissionSet> permissionSets; // Assume this is populated

    public UserService(List<Group> groups, List<Folder> folders, List<PermissionSet> permissionSets) {
        this.groups = groups;
        this.folders = folders;
        this.permissionSets = permissionSets;
    }

    // Method to verify user permissions based on their group associations and folder permissions
    private void verifyUserPermissions(int userId, String folderId, String expectedPermissionSet) {
        // Find the folder by ID
        Optional<Folder> folderOptional = folders.stream()
                .filter(folder -> String.valueOf(folder.getId()).equals(folderId))
                .findFirst();

        if (folderOptional.isEmpty()) {
            throw new IllegalArgumentException("Folder not found");
        }

        Folder folder = folderOptional.get();
        Integer permissionSetId = folder.getPermissionSetId();

        if (permissionSetId == null) {
            throw new IllegalArgumentException("No permission set associated with this folder");
        }

        // Get the permission set associated with the folder
        Optional<PermissionSet> permissionSetOptional = permissionSets.stream()
                .filter(permissionSet -> permissionSet.getId() == permissionSetId)
                .findFirst();

        if (permissionSetOptional.isEmpty()) {
            throw new IllegalArgumentException("Permission set not found");
        }

        PermissionSet permissionSet = permissionSetOptional.get();

        // Check if the user belongs to a group associated with the folder
        boolean hasPermission = groups.stream()
                .anyMatch(group -> group.getUserIds().contains(userId) &&
                        group.getId() == permissionSet.getId() &&
                        permissionSet.getName().equals(expectedPermissionSet));

        if (!hasPermission) {
            throw new SecurityException("User does not have the required permissions for this folder");
        }
    }
}
package com.example.demo;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Folder {
    private int id;
    private String name;
    private Set<Integer> associatedGroups = new HashSet<>();
    private Integer permissionSetId;

    private List<Integer> groupIds; // List of associated group IDs

    // Constructors
    public Folder() {
    }

    public Folder(int id, String name, Integer permissionSetId, List<Integer> groupIds) {
        this.id = id;
        this.name = name;
        this.permissionSetId = permissionSetId;
        this.groupIds = groupIds;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getAssociatedGroups() {
        return associatedGroups;
    }

    public void setAssociatedGroups(Set<Integer> associatedGroups) {
        this.associatedGroups = associatedGroups;
    }

    public Integer getPermissionSetId() {
        return permissionSetId;
    }

    public void setPermissionSetId(Integer permissionSetId) {
        this.permissionSetId = permissionSetId;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissionSetId=" + permissionSetId +
                ", groupIds=" + groupIds +
                '}';
    }
}
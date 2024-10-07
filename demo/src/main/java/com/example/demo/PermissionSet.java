package com.example.demo;

public class PermissionSet {
    private int id;
    private String name;

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

    // toString() method
    @Override
    public String toString() {
        return "PermissionSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
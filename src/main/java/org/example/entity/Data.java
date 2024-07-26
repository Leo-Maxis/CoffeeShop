package org.example.entity;

public class Data {
    private static String username;
    private static String path;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Data.username = username;
    }

    public static String getPath() {
        return path;
    }

    public static String setPath(String path) {
        Data.path = path;
        return path;
    }
}

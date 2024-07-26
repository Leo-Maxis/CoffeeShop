package org.example.entity;

public class Data {
    private static String username;
    private static String path;
    private static String date;
    private static int id;

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

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Data.date = date;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Data.id = id;
    }
}

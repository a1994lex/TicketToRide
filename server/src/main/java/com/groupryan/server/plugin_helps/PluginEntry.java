package com.groupryan.server.plugin_helps;

public class PluginEntry {

    private String description;
    private String filePath;
    private String classPath;

    public PluginEntry(String description, String filePath, String classPath) {
        this.description = description;
        this.filePath = filePath;
        this.classPath = classPath;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getClassPath() {
        return classPath;
    }

    @Override
    public String toString() {
        return "PluginEntry{" +
                "description='" + description + '\'' +
                ", filePath='" + filePath + '\'' +
                ", classPath='" + classPath + '\'' +
                '}';
    }
}

package com.example.QLMT;

public interface IManagement {

    void exportToFile(String filePath) throws Exception;

    int importFromFile(String filePath) throws Exception;
}
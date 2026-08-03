package com.example.QLMT;

public interface IManagement {

    void exportToFile(String filePath) throws Exception;

    void importFromFile(String filePath) throws Exception;
}
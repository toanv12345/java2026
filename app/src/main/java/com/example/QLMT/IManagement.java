package com.example.QLMT;

import java.util.List;

public interface IManagement {

    void addComputer(Computer computer) throws DuplicateIdException;

    List<Computer> getAllComputers();

    Computer findById(String id) throws Exception;

    boolean deleteById(String id) throws Exception;

    boolean isIdExist(String id);

    void exportToFile(String filePath) throws Exception;

    void importFromFile(String filePath) throws Exception;
}
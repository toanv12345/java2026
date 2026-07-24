package com.example.QLMT;

import java.util.ArrayList;
import java.util.List;

public class ComputerManager {
    private List<Computer> list;

    public ComputerManager() {
        this.list = new ArrayList<>();
    }

    public boolean isIdExist(String id) {
        for (Computer c : list) {
            if (c.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public void addComputer(Computer computer) throws DuplicateIdException {
        if (isIdExist(computer.getId())) {
            throw new DuplicateIdException("Lỗi: Mã máy tính '" + computer.getId() + "' đã tồn tại!");
        }
        list.add(computer);
        System.out.println("-> Thêm thành công!");
    }

    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("Danh sách hiện đang trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH MÁY TÍNH ==========");
        for (Computer c : list) {
            c.displayInfo();
        }
    }

    public Computer findById(String id) throws Exception {
        for (Computer c : list) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        throw new Exception("Lỗi: Không tìm thấy máy tính có ID: " + id);
    }

    public boolean deleteById(String id) throws Exception {
        Computer computer = findById(id);
        list.remove(computer);
        return true;
    }

    public List<Computer> getList() {
        return list;
    }
}
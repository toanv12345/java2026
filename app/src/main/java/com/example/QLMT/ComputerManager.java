package com.example.QLMT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ComputerManager implements IComputer, IManagement {

    private final List<Computer> list;

    public ComputerManager() {
        this.list = new ArrayList<>();
    }

    @Override
    public boolean isIdExist(String id) {
        for (Computer c : list) {
            if (c.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addComputer(Computer computer) throws DuplicateIdException {
        if (isIdExist(computer.getId())) {
            throw new DuplicateIdException("Lỗi: Mã máy tính '" + computer.getId() + "' đã tồn tại!");
        }
        list.add(computer);
        System.out.println("-> Thêm vào danh sách thành công!");
    }

    @Override
    public List<Computer> getAllComputers() {
        return list;
    }

    @Override
    public Computer findById(String id) throws Exception {
        for (Computer c : list) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        throw new Exception("Lỗi: Không tìm thấy máy tính có ID: " + id);
    }

    @Override
    public boolean deleteById(String id) throws Exception {
        Computer computer = findById(id);
        list.remove(computer);
        return true;
    }

    @Override
    public void exportToFile(String filePath) throws Exception {
        if (list.isEmpty()) {
            throw new Exception("Danh sách rỗng, không có dữ liệu để xuất file!");
        }

        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(osw)) {

            for (Computer c : list) {
                if (c instanceof Laptop) {
                    Laptop l = (Laptop) c;
                    bw.write(String.format("LAPTOP,%s,%s,%s,%d,%.0f,%.2f,%d,%.1f",
                            l.getId(), l.getBrand(), l.getCpu(), l.getRam(), l.getPrice(),
                            l.getWeight(), l.getBatteryCapacity(), l.getScreenSize()));
                } else if (c instanceof Desktop) {
                    Desktop d = (Desktop) c;
                    bw.write(String.format("DESKTOP,%s,%s,%s,%d,%.0f,%d,%s",
                            d.getId(), d.getBrand(), d.getCpu(), d.getRam(), d.getPrice(),
                            d.getPowerSupply(), d.getCaseType()));
                }
                bw.newLine();
            }
            System.out.println("-> Xuất file thành công: " + filePath);
        } catch (Exception e) {
            throw new Exception("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public int importFromFile(String filePath) throws Exception {
        int importedCount = 0;
        int errorCount = 0;

        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 8) continue;

                String type = parts[0].trim().toUpperCase();
                String id = parts[1].trim();
                String brand = parts[2].trim();
                String cpu = parts[3].trim();
                int ram = Integer.parseInt(parts[4].trim());
                double price = Double.parseDouble(parts[5].trim());

                try {
                    if ("LAPTOP".equalsIgnoreCase(type) && parts.length >= 9) {
                        double weight = Double.parseDouble(parts[6].trim());
                        int battery = Integer.parseInt(parts[7].trim());
                        double screenSize = Double.parseDouble(parts[8].trim());

                        Laptop laptop = new Laptop(id, brand, cpu, ram, price, weight, battery, screenSize);
                        addComputer(laptop);
                        importedCount++;

                    } else if ("DESKTOP".equalsIgnoreCase(type) && parts.length >= 8) {
                        int powerSupply = Integer.parseInt(parts[6].trim());
                        String caseType = parts[7].trim();

                        Desktop desktop = new Desktop(id, brand, cpu, ram, price, powerSupply, caseType);
                        addComputer(desktop);
                        importedCount++;
                    }
                } catch (DuplicateIdException e) {
                    System.err.println("Bỏ qua ID trùng trong file: " + id);
                    errorCount++;
                } catch (Exception e) {
                    System.err.println("Bỏ qua dòng lỗi định dạng: " + line);
                    errorCount++;
                }
            }
        } catch (Exception e) {
            throw new Exception("Lỗi đọc file: " + e.getMessage());
        }

        System.out.println("-> Hoàn tất nhập file! Thành công: " + importedCount + " | Bỏ qua/Lỗi: " + errorCount);
        return importedCount;
    }
}
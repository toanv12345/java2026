package com.example.QLMT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ComputerManager implements IManagement {

    private List<Computer> list;

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
    public boolean updateComputer(String id, Computer updated) throws Exception {
        Computer existing = findById(id);

        // Cập nhật các trường chung
        if (updated.getBrand() != null && !updated.getBrand().isEmpty()) {
            existing.setBrand(updated.getBrand());
        }
        if (updated.getCpu() != null && !updated.getCpu().isEmpty()) {
            existing.setCpu(updated.getCpu());
        }
        if (updated.getRam() > 0) {
            existing.setRam(updated.getRam());
        }
        if (updated.getPrice() > 0) {
            existing.setPrice(updated.getPrice());
        }

        // Cập nhật các trường riêng theo kiểu máy
        if (existing instanceof Laptop && updated instanceof Laptop) {
            Laptop existingLaptop = (Laptop) existing;
            Laptop updatedLaptop = (Laptop) updated;
            if (updatedLaptop.getWeight() > 0) {
                existingLaptop.setWeight(updatedLaptop.getWeight());
            }
            if (updatedLaptop.getBatteryCapacity() > 0) {
                existingLaptop.setBatteryCapacity(updatedLaptop.getBatteryCapacity());
            }
            if (updatedLaptop.getScreenSize() > 0) {
                existingLaptop.setScreenSize(updatedLaptop.getScreenSize());
            }
        } else if (existing instanceof Desktop && updated instanceof Desktop) {
            Desktop existingDesktop = (Desktop) existing;
            Desktop updatedDesktop = (Desktop) updated;
            if (updatedDesktop.getPowerSupply() > 0) {
                existingDesktop.setPowerSupply(updatedDesktop.getPowerSupply());
            }
            if (updatedDesktop.getCaseType() != null && !updatedDesktop.getCaseType().isEmpty()) {
                existingDesktop.setCaseType(updatedDesktop.getCaseType());
            }
        }

        return true;
    }

    private boolean isBinaryFile(String filePath) {
        return filePath.toLowerCase().endsWith(".bin");
    }

    @Override
    public void exportToFile(String filePath) throws Exception {
        if (list.isEmpty()) {
            throw new Exception("Danh sách rỗng, không có dữ liệu để xuất file!");
        }
        if (isBinaryFile(filePath)) {
            exportToBinaryFile(filePath);
        } else {
            exportToTextFile(filePath);
        }
    }

    @Override
    public void importFromFile(String filePath) throws Exception {
        if (isBinaryFile(filePath)) {
            importFromBinaryFile(filePath);
        } else {
            importFromTextFile(filePath);
        }
    }

    private void exportToTextFile(String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw)) {

            for (Computer c : list) {
                if (c instanceof Laptop) {
                    Laptop l = (Laptop) c;
                    String line = String.format(
                            "LAPTOP  | ID: %s | Brand: %s | CPU: %s | RAM: %dGB | Price: %.0f VND | Weight: %.1fkg | Battery: %dmAh | Screen: %.1f inch",
                            l.getId(), l.getBrand(), l.getCpu(), l.getRam(), l.getPrice(),
                            l.getWeight(), l.getBatteryCapacity(), l.getScreenSize());
                    bw.write(line);

                } else if (c instanceof Desktop) {
                    Desktop d = (Desktop) c;
                    String line = String.format(
                            "DESKTOP | ID: %s | Brand: %s | CPU: %s | RAM: %dGB | Price: %.0f VND | Power: %dW | Case: %s",
                            d.getId(), d.getBrand(), d.getCpu(), d.getRam(), d.getPrice(),
                            d.getPowerSupply(), d.getCaseType());
                    bw.write(line);
                }
                bw.newLine();
            }
            System.out.println("-> Xuất file văn bản thành công: " + filePath);
        } catch (Exception e) {
            throw new Exception("Lỗi khi ghi file văn bản: " + e.getMessage());
        }
    }

    private void importFromTextFile(String filePath) throws Exception {
        int importedCount = 0;
        int skippedCount = 0;

        try (FileInputStream fis = new FileInputStream(filePath);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                try {
                    String[] parts = line.split("\\|");
                    String type = parts[0].trim().toUpperCase();

                    if ("LAPTOP".equals(type) && parts.length >= 9) {
                        String id = getValue(parts[1], "ID:");
                        String brand = getValue(parts[2], "Brand:");
                        String cpu = getValue(parts[3], "CPU:");
                        int ram = Integer.parseInt(getValue(parts[4], "RAM:").replace("GB", ""));
                        double price = Double.parseDouble(getValue(parts[5], "Price:").replace("VND", ""));
                        double weight = Double.parseDouble(getValue(parts[6], "Weight:").replace("kg", ""));
                        int battery = Integer.parseInt(getValue(parts[7], "Battery:").replace("mAh", ""));
                        double screenSize = Double.parseDouble(getValue(parts[8], "Screen:").replace("inch", ""));

                        Laptop laptop = new Laptop(id, brand, cpu, ram, price, weight, battery, screenSize);

                        if (!isIdExist(id)) {
                            list.add(laptop);
                            importedCount++;
                        } else {
                            skippedCount++;
                        }

                    } else if ("DESKTOP".equals(type) && parts.length >= 7) {
                        String id = getValue(parts[1], "ID:");
                        String brand = getValue(parts[2], "Brand:");
                        String cpu = getValue(parts[3], "CPU:");
                        int ram = Integer.parseInt(getValue(parts[4], "RAM:").replace("GB", ""));
                        double price = Double.parseDouble(getValue(parts[5], "Price:").replace("VND", ""));
                        int powerSupply = Integer.parseInt(getValue(parts[6], "Power:").replace("W", ""));
                        String caseType = getValue(parts[7], "Case:");

                        Desktop desktop = new Desktop(id, brand, cpu, ram, price, powerSupply, caseType);

                        if (!isIdExist(id)) {
                            list.add(desktop);
                            importedCount++;
                        } else {
                            skippedCount++;
                        }
                    }
                } catch (Exception e) {
                    skippedCount++;
                }
            }

            System.out.println(
                    "-> Nhập dữ liệu hoàn tất! Thành công: " + importedCount + " máy | Bỏ qua/Lỗi: " + skippedCount);

        } catch (FileNotFoundException e) {
            throw new Exception("Không tìm thấy file: " + filePath);
        } catch (IOException e) {
            throw new Exception("Lỗi khi đọc file văn bản: " + e.getMessage());
        }
    }

    private void exportToBinaryFile(String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(list);
            System.out.println("-> Xuất file nhị phân thành công: " + filePath
                    + " (" + list.size() + " máy)");
        } catch (Exception e) {
            throw new Exception("Lỗi khi ghi file nhị phân: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void importFromBinaryFile(String filePath) throws Exception {
        int importedCount = 0;
        int skippedCount = 0;

        try (FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<Computer> imported = (List<Computer>) ois.readObject();

            for (Computer c : imported) {
                if (!isIdExist(c.getId())) {
                    list.add(c);
                    importedCount++;
                } else {
                    skippedCount++;
                }
            }

            System.out.println(
                    "-> Nhập dữ liệu hoàn tất! Thành công: " + importedCount
                            + " máy | Bỏ qua (trùng ID): " + skippedCount);

        } catch (FileNotFoundException e) {
            throw new Exception("Không tìm thấy file: " + filePath);
        } catch (ClassNotFoundException e) {
            throw new Exception("Định dạng file nhị phân không hợp lệ: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Lỗi khi đọc file nhị phân: " + e.getMessage());
        }
    }

    private String getValue(String field, String prefix) {
        String trimmed = field.trim();
        if (trimmed.startsWith(prefix)) {
            return trimmed.substring(prefix.length()).trim();
        }
        return trimmed;
    }
}
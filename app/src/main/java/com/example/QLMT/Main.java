package com.example.QLMT;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final ComputerManager manager = new ComputerManager();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = inputInt("Lựa chọn của bạn: ");
            switch (choice) {
                case 1:
                    addLaptopMenu();
                    break;
                case 2:
                    addDesktopMenu();
                    break;
                case 3:
                    displayAllComputers();
                    break;
                case 4:
                    searchComputer();
                    break;
                case 5:
                    deleteComputer();
                    break;
                case 6:
                    exportFileMenu();
                    break;
                case 7:
                    importFileMenu();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 0 - 7.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n================ QUẢN LÝ MÁY TÍNH ================");
        System.out.println("1. Thêm Laptop");
        System.out.println("2. Thêm Desktop");
        System.out.println("3. Hiển thị danh sách máy tính");
        System.out.println("4. Tìm kiếm máy tính theo ID");
        System.out.println("5. Xóa máy tính theo ID");
        System.out.println("6. Xuất danh sách máy tính ra File (.txt / .csv / .bin)");
        System.out.println("7. Nhập thông tin máy tính từ File (.txt / .csv / .bin)");
        System.out.println("0. Thoát");
        System.out.println("==================================================");
    }

    private static void addLaptopMenu() {
        System.out.println("\n--- NHẬP THÔNG TIN LAPTOP ---");
        String id = inputString("Nhập ID: ");
        String brand = inputString("Nhập Thương hiệu: ");
        String cpu = inputString("Nhập CPU: ");
        int ram = inputPositiveInt("Nhập RAM (GB): ");
        double price = inputPositiveDouble("Nhập Giá (VND): ");
        double weight = inputPositiveDouble("Nhập Trọng lượng (kg): ");
        int battery = inputPositiveInt("Nhập Dung lượng pin (mAh): ");
        double screenSize = inputPositiveDouble("Nhập Kích thước màn hình (inch): ");

        Laptop laptop = new Laptop(id, brand, cpu, ram, price, weight, battery, screenSize);
        try {
            manager.addComputer(laptop);
        } catch (DuplicateIdException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addDesktopMenu() {
        System.out.println("\n--- NHẬP THÔNG TIN DESKTOP ---");
        String id = inputString("Nhập ID: ");
        String brand = inputString("Nhập Thương hiệu: ");
        String cpu = inputString("Nhập CPU: ");
        int ram = inputPositiveInt("Nhập RAM (GB): ");
        double price = inputPositiveDouble("Nhập Giá (VND): ");
        int powerSupply = inputPositiveInt("Nhập Công suất nguồn (W): ");
        String caseType = inputString("Nhập Loại Case: ");

        Desktop desktop = new Desktop(id, brand, cpu, ram, price, powerSupply, caseType);
        try {
            manager.addComputer(desktop);
        } catch (DuplicateIdException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void displayAllComputers() {
        List<Computer> list = manager.getAllComputers();
        if (list.isEmpty()) {
            System.out.println("Danh sách hiện đang trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH MÁY TÍNH ==========");
        for (Computer c : list) {
            c.displayInfo();
        }
    }

    private static void searchComputer() {
        String id = inputString("Nhập ID cần tìm: ");
        try {
            Computer c = manager.findById(id);
            System.out.println("-> Tìm thấy:");
            c.displayInfo();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void deleteComputer() {
        String id = inputString("Nhập ID cần xóa: ");
        try {
            manager.deleteById(id);
            System.out.println("-> Xóa máy tính thành công!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void exportFileMenu() {
        String filePath = inputString("Nhập tên file/đường dẫn cần xuất (VD: computers.csv): ");
        try {
            manager.exportToFile(filePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void importFileMenu() {
        String filePath = inputString("Nhập tên file/đường dẫn cần đọc (VD: data_import.csv): ");
        try {
            manager.importFromFile(filePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static String inputString(String label) {
        while (true) {
            System.out.print(label);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty())
                return value;
            System.out.println("Lỗi: Không được để trống!");
        }
    }

    private static int inputInt(String label) {
        while (true) {
            try {
                System.out.print(label);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    private static int inputPositiveInt(String label) {
        while (true) {
            int val = inputInt(label);
            if (val > 0)
                return val;
            System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
        }
    }

    private static double inputDouble(String label) {
        while (true) {
            try {
                System.out.print(label);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số thực hợp lệ!");
            }
        }
    }

    private static double inputPositiveDouble(String label) {
        while (true) {
            double val = inputDouble(label);
            if (val > 0)
                return val;
            System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
        }
    }
}
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
                    editComputerMenu();
                    break;
                case 7:
                    exportFileMenu();
                    break;
                case 8:
                    importFileMenu();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 0 - 8.");
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
        System.out.println("6. Sửa thông tin máy tính theo ID");
        System.out.println("7. Xuất danh sách máy tính ra File (.txt / .csv / .bin)");
        System.out.println("8. Nhập thông tin máy tính từ File (.txt / .csv / .bin)");
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

    private static void editComputerMenu() {
        System.out.println("\n--- SỬA THÔNG TIN MÁY TÍNH ---");
        String id = inputString("Nhập ID máy tính cần sửa: ");
        Computer existing;
        try {
            existing = manager.findById(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("-> Thông tin hiện tại:");
        existing.displayInfo();
        System.out.println("\n(Nhấn Enter để giữ nguyên giá trị cũ)");

        if (existing instanceof Laptop) {
            Laptop old = (Laptop) existing;
            String brand   = inputOptionalString("Thương hiệu [" + old.getBrand() + "]: ");
            String cpu     = inputOptionalString("CPU [" + old.getCpu() + "]: ");
            int    ram     = inputOptionalPositiveInt("RAM (GB) [" + old.getRam() + "]: ");
            double price   = inputOptionalPositiveDouble("Giá (VND) [" + (long) old.getPrice() + "]: ");
            double weight  = inputOptionalPositiveDouble("Trọng lượng (kg) [" + old.getWeight() + "]: ");
            int    battery = inputOptionalPositiveInt("Pin (mAh) [" + old.getBatteryCapacity() + "]: ");
            double screen  = inputOptionalPositiveDouble("Màn hình (inch) [" + old.getScreenSize() + "]: ");

            Laptop updated = new Laptop(id,
                    brand.isEmpty()  ? old.getBrand()  : brand,
                    cpu.isEmpty()    ? old.getCpu()    : cpu,
                    ram   == 0       ? old.getRam()    : ram,
                    price == 0       ? old.getPrice()  : price,
                    weight == 0      ? old.getWeight() : weight,
                    battery == 0     ? old.getBatteryCapacity() : battery,
                    screen == 0      ? old.getScreenSize()      : screen);
            try {
                manager.updateComputer(id, updated);
                System.out.println("-> Cập nhật Laptop thành công!");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        } else if (existing instanceof Desktop) {
            Desktop old   = (Desktop) existing;
            String brand  = inputOptionalString("Thương hiệu [" + old.getBrand() + "]: ");
            String cpu    = inputOptionalString("CPU [" + old.getCpu() + "]: ");
            int    ram    = inputOptionalPositiveInt("RAM (GB) [" + old.getRam() + "]: ");
            double price  = inputOptionalPositiveDouble("Giá (VND) [" + (long) old.getPrice() + "]: ");
            int    power  = inputOptionalPositiveInt("Công suất nguồn (W) [" + old.getPowerSupply() + "]: ");
            String caseT  = inputOptionalString("Loại Case [" + old.getCaseType() + "]: ");

            Desktop updated = new Desktop(id,
                    brand.isEmpty()  ? old.getBrand()      : brand,
                    cpu.isEmpty()    ? old.getCpu()        : cpu,
                    ram   == 0       ? old.getRam()        : ram,
                    price == 0       ? old.getPrice()      : price,
                    power == 0       ? old.getPowerSupply(): power,
                    caseT.isEmpty()  ? old.getCaseType()   : caseT);
            try {
                manager.updateComputer(id, updated);
                System.out.println("-> Cập nhật Desktop thành công!");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
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

    /** Trả về chuỗi rỗng nếu người dùng bấm Enter (giữ nguyên giá trị cũ). */
    private static String inputOptionalString(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    /** Trả về 0 nếu người dùng bấm Enter (giữ nguyên giá trị cũ). */
    private static int inputOptionalPositiveInt(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return 0;
            try {
                int val = Integer.parseInt(input);
                if (val > 0) return val;
                System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    /** Trả về 0.0 nếu người dùng bấm Enter (giữ nguyên giá trị cũ). */
    private static double inputOptionalPositiveDouble(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return 0.0;
            try {
                double val = Double.parseDouble(input);
                if (val > 0) return val;
                System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số thực hợp lệ!");
            }
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
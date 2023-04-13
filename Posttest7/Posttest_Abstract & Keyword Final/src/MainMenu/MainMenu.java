
//package : mengelompokkan berbagai hal (kelas, interface, enum, dsb) yang memiliki fungsi berkaitan
package MainMenu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//public : pengaksesan suatu variabel instan diluar class
public class MainMenu {
    static String fileName;
    static ArrayList<String> todoLists;
    static boolean isEditing = false;
    static Scanner input;
    
    //static : sebuah property atau method diakses langsung tanpa object
    public static void main(String[] args) {
        // Abstract & Keyword Final
        Nama nama = new Nama();
        nama.Nama();
        nama.Boneka();
        nama.JenisBoneka();

        // inisialisasi
        do {            
            todoLists = new ArrayList<>();
            input = new Scanner(System.in);
        } while (isEditing);
        String filePath = System.console() == null ? "/src/crud.txt" : "/crud.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " + fileName);

        // run the program (main loop)
        while (true) {
            showMenu();
        }
    }

    static void clearScreen(){
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                // clear screen untuk Linux, Unix, Mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException | InterruptedException e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void showMenu() {
        //showMenu()
        System.out.println("");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("        Sistem Penjualan Boneka Lucu Toko Kawaii' Doll House      ");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        System.out.println("");
        System.out.println("(1) Tambah Boneka   ");
        System.out.println("(2) Lihat Boneka    ");
        System.out.println("(3) Edit Boneka     ");
        System.out.println("(4) Hapus Boneka    ");
        System.out.println("(5) Exit    ");
        System.out.println("");

        System.out.print("Pilih Menu : ");

    String selectedMenu = input.nextLine();

        switch (selectedMenu) {
            case "1":
                //Create
                System.out.println("Selamat Datang di Toko Boneka Lucu Ini!");
                addTodoList();
                break;
            case "2":
                //Read
                System.out.println("Selamat Datang di Toko Boneka Lucu Ini!");
                showTodoList();
                break;
            case "3":
                //Update
                System.out.println("Selamat Datang di Toko Boneka Lucu Ini!");
                System.out.println("Indeks diUpdate : ");
                editTodoList();
                break;
            case "4":
                //Delete
                System.out.println("Selamat Datang di Toko Boneka Lucu Ini!");
                System.out.println("Indeks diHapus : ");
                deleteTodoList();
                break;
            case "5":
                //Exit
                System.out.println("Terima Kasih! Silahkan Datang Lagi!");
                System.exit(5);
            default:
                System.out.println("Oops! Salah Pilih Menu!");
                backToMenu();
                break;
        }
    }

    String pilihanMenu = input.nextLine();

    static void backToMenu() {
        System.out.println("");
        System.out.print("Silahkan Tekan 'Enter' untuk Kembali!");
        input.nextLine();
        clearScreen();
    }

    static void readTodoList() {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            // load isi file ke dalam array todoLists
            todoLists.clear();
                while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                todoLists.add(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void showTodoList() {
        clearScreen();
        readTodoList();
        if (todoLists.size() > 0) {
            System.out.println("");
            System.out.println("Boneka yang Ditambahkan : ");
            int index = 0;
            for (String data : todoLists) {
                System.out.println(String.format("(%d) %s", index, data));
                index++;
            }
        } else {
            System.out.println("Sorry! No Data Here");
        }

        if (!isEditing) {
            backToMenu();
        }
    }

    static void addTodoList() {
        clearScreen();
        System.out.println(" ");
        System.out.println("Nama Boneka Impian Apa?");
        System.out.print("Jawab : ");
        String newTodoList = input.nextLine();

        try {
            try ( // tulis file
                FileWriter fileWriter = new FileWriter(fileName, true)) {
                    fileWriter.append(String.format("%s%n", newTodoList));
            }
            System.out.println(" ");
            System.out.println(" ---------------------------------- ");
            System.out.println("         Berhasil Ditambahkan!      ");
            System.out.println(" ---------------------------------- ");
        } catch (IOException e) {
            System.out.println("Oops! Kenali Error : " + e.getMessage());
        }
        backToMenu();
    }

    static void editTodoList() {
        isEditing = true;
        showTodoList();

        try {
            System.out.println(" ");
            System.out.print("Pilih Indeks : ");
            int index = Integer.parseInt(input.nextLine());

            if (index > todoLists.size()) {
                throw new IndexOutOfBoundsException("Data Kamu Salah!");
            } else {
                System.out.println("Nama Boneka Baru : ");
                String newData = input.nextLine();

                // update data
                String set = todoLists.set(index, newData);
                System.out.println(todoLists.toString());

                try {
                    // write new data
                    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
                        // write new data
                        for (String data : todoLists) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                    }

                    System.out.println("");
                    System.out.println(" ---------------------------------- ");
                    System.out.println("         Berhasil Diubahkan!        ");
                    System.out.println(" ---------------------------------- ");
                } catch (IOException e) {
                    System.out.println("Oops! Know the Error : " + e.getMessage());
                }
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }

    static void deleteTodoList() {
        isEditing = true;
        showTodoList();

        System.out.println(" ");
        System.out.println("Pilih Indeks : ");
        int index = Integer.parseInt(input.nextLine());

        try {
            if (index > todoLists.size()) {
                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
            } else {
                System.out.println("");
                System.out.println("Indeks yang Kamu Hapus Adalah Indeks : ");
                System.out.println(String.format("(%d) %s", index, todoLists.get(index)));
                System.out.println("");
                System.out.println("Adakah Kamu Yakin Hapus?");
                System.out.print("Jawab (Y/T) : ");
                String jawab = input.nextLine();

            if (jawab.equalsIgnoreCase("Y")) {
                // hapus data
                String remove = todoLists.remove(index);

                // tulis ulang file
                try {
                    // write new data
                    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
                        // write new data
                        for (String data : todoLists) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                    }
                    
                    System.out.println("");
                    System.out.println(" ---------------------------------- ");
                    System.out.println("          Berhasil Dihapus!         ");
                    System.out.println(" ---------------------------------- ");
                } catch (IOException e) {
                    System.out.println("Oops! Know the Error : " + e.getMessage());
                }
            }
        }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        //back
        isEditing = false;
        backToMenu();
    } 
}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private ArrayList<MenuItem> items = new ArrayList<>();
    private final String menuFile = "menu.txt";

    public Menu() {}

    public List<MenuItem> getItems() { return items; }

    public void tambahMenu(MenuItem item) { items.add(item); }

    public void hapusMenu(int index) throws IndexOutOfBoundsException { items.remove(index); }

    public void editHarga(int index, double hargaBaru) throws IndexOutOfBoundsException { items.get(index).setHarga(hargaBaru); }

    public MenuItem getByIndex(int index) throws IndexOutOfBoundsException { return items.get(index); }

    public MenuItem findByName(String nama) {
        for (MenuItem m : items) {
            if (m.getNama().equalsIgnoreCase(nama.trim())) return m;
        }
        return null;
    }

    public List<Diskon> getActiveDiscounts() {
        List<Diskon> ds = new ArrayList<>();
        for (MenuItem m : items) if (m instanceof Diskon) ds.add((Diskon) m);
        return ds;
    }

    public void tampilkanMenu() {
        System.out.println("\n--- Makanan ---");
        boolean adaMakanan = false;
        for (MenuItem m : items) {
            if (m instanceof Makanan) { m.tampilMenu(); adaMakanan = true; }
        }
        if (!adaMakanan) System.out.println("Tidak ada makanan.");

        System.out.println("\n--- Minuman ---");
        boolean adaMinuman = false;
        for (MenuItem m : items) {
            if (m instanceof Minuman) { m.tampilMenu(); adaMinuman = true; }
        }
        if (!adaMinuman) System.out.println("Tidak ada minuman.");

        System.out.println("\n--- Diskon (Promosi) ---");
        boolean adaDiskon = false;
        for (MenuItem m : items) {
            if (m instanceof Diskon) { m.tampilMenu(); adaDiskon = true; }
        }
        if (!adaDiskon) System.out.println("Tidak ada promosi saat ini.");
    }

    public void tampilkanMenuNumbered() {
        System.out.println("\nDaftar Menu:");
        for (int i = 0; i < items.size(); i++) {
            MenuItem m = items.get(i);
            System.out.printf("%d. [%s] %s - Rp %.0f%n", i + 1, m.getKategori(), m.getNama(), m.getHarga());
        }
    }

    public void saveToFile() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(menuFile))) {
            for (MenuItem m : items) {
                bw.write(m.toFileString());
                bw.newLine();
            }
        }
    }

    public void loadFromFile() throws IOException {
        items.clear();
        File f = new File(menuFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;
                String type = parts[0];
                String name = parts[1].replace("/", "|");
                double price = Double.parseDouble(parts[2]);
                if (type.equalsIgnoreCase("Makanan") && parts.length >= 5) {
                    String jenis = parts[4];
                    items.add(new Makanan(name, price, jenis));
                } else if (type.equalsIgnoreCase("Minuman") && parts.length >= 5) {
                    String jenis = parts[4];
                    items.add(new Minuman(name, price, jenis));
                } else if (type.equalsIgnoreCase("Diskon") && parts.length >= 7) {
                    double persen = Double.parseDouble(parts[4]);
                    double minSubtotal = Double.parseDouble(parts[5]);
                    String applicable = parts[6];
                    items.add(new Diskon(name, persen, minSubtotal, applicable));
                }
            }
        }
    }
}

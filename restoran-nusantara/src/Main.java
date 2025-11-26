import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = new Menu();
        try { menu.loadFromFile(); } catch (IOException e) { System.out.println("Tidak dapat memuat menu: " + e.getMessage()); }

        while (true) {
            System.out.println("\n=== SISTEM MANAJEMEN RESTORAN NUSANTARA ===");
            System.out.println("1. Tampilkan Menu Restoran");
            System.out.println("2. Tambah Item Menu");
            System.out.println("3. Ubah Harga Item");
            System.out.println("4. Hapus Item Menu");
            System.out.println("5. Pesan (Pelanggan)");
            System.out.println("6. Simpan Menu ke File");
            System.out.println("7. Muat Menu dari File");
            System.out.println("8. Keluar");
            System.out.print("Pilih (1-8): ");
            String pilih = sc.nextLine().trim();

            switch (pilih) {
                case "1":
                    menu.tampilkanMenu();
                    break;
                case "2":
                    tambahItemFlow(menu);
                    break;
                case "3":
                    ubahHargaFlow(menu);
                    break;
                case "4":
                    hapusItemFlow(menu);
                    break;
                case "5":
                    pesananFlow(menu);
                    break;
                case "6":
                    try {
                        menu.saveToFile();
                        System.out.println("Menu disimpan ke 'menu.txt'");
                    } catch (IOException e) { System.out.println("Gagal menyimpan menu: " + e.getMessage()); }
                    break;
                case "7":
                    try {
                        menu.loadFromFile();
                        System.out.println("Menu dimuat dari 'menu.txt'");
                    } catch (IOException e) { System.out.println("Gagal memuat menu: " + e.getMessage()); }
                    break;
                case "8":
                    System.out.println("Keluar. Terima kasih.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static void tambahItemFlow(Menu menu) {
        System.out.println("\n-- Tambah Item Menu --");
        System.out.print("Jenis (1=Makanan, 2=Minuman, 3=Diskon): ");
        String t = sc.nextLine().trim();
        switch (t) {
            case "1":
                System.out.print("Nama makanan: ");
                String nM = sc.nextLine();
                System.out.print("Harga: ");
                double hM = parseDoubleInput();
                System.out.print("Jenis makanan (mis. berat/ringan): ");
                String jm = sc.nextLine();
                menu.tambahMenu(new Makanan(nM, hM, jm));
                System.out.println("Makanan ditambahkan.");
                break;
            case "2":
                System.out.print("Nama minuman: ");
                String nMin = sc.nextLine();
                System.out.print("Harga: ");
                double hMin = parseDoubleInput();
                System.out.print("Jenis minuman (mis. panas/dingin/jus): ");
                String jmin = sc.nextLine();
                menu.tambahMenu(new Minuman(nMin, hMin, jmin));
                System.out.println("Minuman ditambahkan.");
                break;
            case "3":
                System.out.print("Nama diskon (label): ");
                String nd = sc.nextLine();
                System.out.print("Persentase diskon (contoh 10 untuk 10%): ");
                double persen = parseDoubleInput();
                System.out.print("Minimal subtotal (contoh 50000): ");
                double minSub = parseDoubleInput();
                System.out.print("Berlaku untuk (all/makanan/minuman): ");
                String app = sc.nextLine();
                if (!app.equalsIgnoreCase("all") && !app.equalsIgnoreCase("makanan") && !app.equalsIgnoreCase("minuman")) {
                    System.out.println("Kategori diskon tidak valid, di-set ke 'all'.");
                    app = "all";
                }
                menu.tambahMenu(new Diskon(nd, persen, minSub, app));
                System.out.println("Diskon ditambahkan.");
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    static void ubahHargaFlow(Menu menu) {
        System.out.println("\n-- Ubah Harga Item --");
        menu.tampilkanMenuNumbered();
        System.out.print("Masukkan nomor item: ");
        int idx = parseIntInput() - 1;
        try {
            MenuItem mi = menu.getByIndex(idx);
            System.out.println("Item: " + mi.getNama() + " (harga saat ini Rp " + mi.getHarga() + ")");
            System.out.print("Harga baru: ");
            double hbaru = parseDoubleInput();
            menu.editHarga(idx, hbaru);
            System.out.println("Harga berhasil diubah.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Nomor tidak valid.");
        }
    }

    static void hapusItemFlow(Menu menu) {
        System.out.println("\n-- Hapus Item --");
        menu.tampilkanMenuNumbered();
        System.out.print("Masukkan nomor item: ");
        int idx = parseIntInput() - 1;
        try {
            MenuItem mi = menu.getByIndex(idx);
            System.out.print("Yakin ingin menghapus '" + mi.getNama() + "' ? (Ya/Tidak): ");
            String c = sc.nextLine();
            if (c.equalsIgnoreCase("ya")) {
                menu.hapusMenu(idx);
                System.out.println("Item dihapus.");
            } else {
                System.out.println("Dibatalkan.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Nomor tidak valid.");
        }
    }

    static void pesananFlow(Menu menu) {
        Pesanan p = new Pesanan();
        System.out.println("\n--- Pesanan Pelanggan ---");
        menu.tampilkanMenu();
        System.out.println("Masukkan pesanan dalam format: Nama Menu = jumlah");
        System.out.println("Ketik 'selesai' jika sudah selesai memesan.");

        while (true) {
            System.out.print("Pesan: ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("selesai")) break;
            if (line.isEmpty()) { System.out.println("Input kosong."); continue; }

            String nama;
            int qty;
            if (line.contains("=")) {
                String[] part = line.split("=", 2);
                nama = part[0].trim();
                try { qty = Integer.parseInt(part[1].trim()); }
                catch (NumberFormatException e) { System.out.println("Jumlah tidak valid."); continue; }
            } else {
                int last = line.lastIndexOf(' ');
                if (last > 0) {
                    nama = line.substring(0, last).trim();
                    try { qty = Integer.parseInt(line.substring(last+1).trim()); }
                    catch (NumberFormatException e) { System.out.println("Jumlah tidak valid."); continue; }
                } else {
                    System.out.println("Format salah. Gunakan: Nama Menu = jumlah");
                    continue;
                }
            }

            MenuItem found = menu.findByName(nama);
            if (found == null) {
                System.out.println("Menu '" + nama + "' tidak ditemukan. Coba lagi.");
                continue;
            }
            if (found instanceof Diskon) {
                System.out.println("Diskon tidak bisa dipesan sebagai item. Diskon diterapkan otomatis bila memenuhi syarat.");
                continue;
            }
            if (qty <= 0) { System.out.println("Jumlah harus > 0."); continue; }
            p.addItem(found, qty);
            System.out.println("Ditambahkan: " + found.getNama() + " x" + qty);
        }

        if (p.isEmpty()) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        String filename = "struk_" + System.currentTimeMillis() + ".txt";
        try {
            p.printAndSaveReceipt(menu, filename);
            System.out.println("Struk disimpan di file: " + filename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }

    static int parseIntInput() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }
    static double parseDoubleInput() {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (Exception e) { return 0.0; }
    }
}

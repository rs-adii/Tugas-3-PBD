public abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori; // "makanan", "minuman", "diskon"

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori.toLowerCase();
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    // Polymorphic display
    public abstract void tampilMenu();

    // Serialize to file line
    public String toFileString() {
        return String.format("%s|%s|%s|%s",
                this.getClass().getSimpleName(),
                escape(getNama()),
                Double.toString(getHarga()),
                getKategori());
    }

    protected static String escape(String s) {
        return s.replace("|", "/"); // simple escaping
    }
}

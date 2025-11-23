public class Makanan extends MenuItem {
    private String jenisMakanan; // e.g. "berat", "ringan"

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "makanan");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenis) { this.jenisMakanan = jenis; }

    @Override
    public void tampilMenu() {
        System.out.printf("[Makanan] %s - Rp %.0f (Jenis: %s)%n", getNama(), getHarga(), jenisMakanan);
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + escape(jenisMakanan);
    }
}

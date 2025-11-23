public class Minuman extends MenuItem {
    private String jenisMinuman; // e.g. "dingin", "panas", "jus"

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "minuman");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenis) { this.jenisMinuman = jenis; }

    @Override
    public void tampilMenu() {
        System.out.printf("[Minuman] %s - Rp %.0f (Jenis: %s)%n", getNama(), getHarga(), jenisMinuman);
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + escape(jenisMinuman);
    }
}


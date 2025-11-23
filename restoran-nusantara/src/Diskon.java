public class Diskon extends MenuItem {
    private double persen; // percent, e.g. 10.0
    private double minSubtotal; // threshold
    private String applicableCategory; // "all", "makanan", "minuman"

    public Diskon(String nama, double persen, double minSubtotal, String applicableCategory) {
        super(nama, 0.0, "diskon");
        this.persen = persen;
        this.minSubtotal = minSubtotal;
        this.applicableCategory = applicableCategory.toLowerCase();
    }

    public double getPersen() { return persen; }
    public double getMinSubtotal() { return minSubtotal; }
    public String getApplicableCategory() { return applicableCategory; }

    @Override
    public void tampilMenu() {
        System.out.printf("[Diskon] %s - %.0f%% (Min Rp %.0f) berlaku untuk: %s%n",
                getNama(), persen, minSubtotal, applicableCategory);
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + Double.toString(persen) + "|" + Double.toString(minSubtotal) + "|" + applicableCategory;
    }
}


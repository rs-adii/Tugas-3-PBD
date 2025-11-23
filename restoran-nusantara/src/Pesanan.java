import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private ArrayList<MenuItem> orderedItems = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();

    private static final double TAX_RATE = 0.10;
    private static final double SERVICE_FEE = 20000.0;

    public void addItem(MenuItem item, int qty) {
        for (int i = 0; i < orderedItems.size(); i++) {
            if (orderedItems.get(i).getNama().equalsIgnoreCase(item.getNama())) {
                quantities.set(i, quantities.get(i) + qty);
                return;
            }
        }
        orderedItems.add(item);
        quantities.add(qty);
    }

    public boolean isEmpty() { return orderedItems.isEmpty(); }

    public double calcSubtotal(String categoryFilter) {
        double subtotal = 0;
        for (int i = 0; i < orderedItems.size(); i++) {
            MenuItem m = orderedItems.get(i);
            if (categoryFilter == null || m.getKategori().equalsIgnoreCase(categoryFilter))
                subtotal += m.getHarga() * quantities.get(i);
        }
        return subtotal;
    }

    public double calcSubtotalAll() { return calcSubtotal(null); }

    public double calcBestDiscount(Menu menu) {
        double best = 0;
        double subtotalAll = calcSubtotalAll();
        for (Diskon d : menu.getActiveDiscounts()) {
            double relevant;
            if (d.getApplicableCategory().equalsIgnoreCase("all")) relevant = subtotalAll;
            else relevant = calcSubtotal(d.getApplicableCategory());
            if (relevant >= d.getMinSubtotal()) {
                double pot = (d.getPersen() / 100.0) * relevant;
                if (pot > best) best = pot;
            }
        }
        return best;
    }

    public List<MenuItem> getOrderedItems() { return orderedItems; }
    public List<Integer> getQuantities() { return quantities; }

    public void printAndSaveReceipt(Menu menu, String filename) throws IOException {
        double subtotal = calcSubtotalAll();
        double discount = calcBestDiscount(menu);
        double afterDiscount = subtotal - discount;
        double tax = TAX_RATE * afterDiscount;
        double grandTotal = afterDiscount + tax + SERVICE_FEE;

        // print to console via ReceiptPrinter
        ReceiptPrinter.printToConsole(orderedItems, quantities, subtotal, discount, tax, SERVICE_FEE, grandTotal);

        // save to file
        ReceiptPrinter.saveToFile(orderedItems, quantities, subtotal, discount, tax, SERVICE_FEE, grandTotal, filename);
    }
}


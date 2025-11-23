import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ReceiptPrinter {

    public static void printToConsole(
            List<MenuItem> items,
            List<Integer> qtys,
            double subtotal,
            double discount,
            double tax,
            double service,
            double grandTotal
    ) {
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("\n========= STRUK PEMESANAN =========");
        System.out.printf("%-4s %-30s %-6s %-12s %-12s%n",
                "No", "Item", "Qty", "Harga/unit", "Total");

        for (int i = 0; i < items.size(); i++) {
            MenuItem m = items.get(i);
            int q = qtys.get(i);
            double total = m.getHarga() * q;
            System.out.printf("%-4d %-30s %-6d Rp %-10s Rp %-10s%n",
                    i + 1, m.getNama(), q, df.format(m.getHarga()), df.format(total));
        }

        System.out.println("-----------------------------------");
        System.out.printf("%-30s : Rp %s%n", "Subtotal", df.format(subtotal));
        System.out.printf("%-30s : Rp %s%n", "Diskon", df.format(discount));
        System.out.printf("%-30s : Rp %s%n", "Subtotal setelah diskon", df.format(subtotal - discount));
        System.out.printf("%-30s : Rp %s%n", "Pajak 10%", df.format(tax));
        System.out.printf("%-30s : Rp %s%n", "Biaya pelayanan", df.format((long) service));
        System.out.println("-----------------------------------");
        System.out.printf("%-30s : Rp %s%n", "GRAND TOTAL", df.format(grandTotal));
        System.out.println("===================================\n");
    }

    public static void saveToFile(
            List<MenuItem> items,
            List<Integer> qtys,
            double subtotal,
            double discount,
            double tax,
            double service,
            double grandTotal,
            String filename
    ) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("STRUK PEMESANAN\n");
            bw.write("-----------------------------------\n");
            for (int i = 0; i < items.size(); i++) {
                MenuItem m = items.get(i);
                int q = qtys.get(i);
                double total = m.getHarga() * q;
                bw.write(String.format("%d. %s x%d - Rp %.0f\n", i + 1, m.getNama(), q, total));
            }
            bw.write("-----------------------------------\n");
            bw.write(String.format("Subtotal: Rp %.0f\n", subtotal));
            bw.write(String.format("Diskon: Rp %.0f\n", discount));
            bw.write(String.format("Pajak 10%%: Rp %.0f\n", tax));
            bw.write(String.format("Biaya pelayanan: Rp %.0f\n", service));
            bw.write("-----------------------------------\n");
            bw.write(String.format("GRAND TOTAL: Rp %.0f\n", grandTotal));
        }
    }
}

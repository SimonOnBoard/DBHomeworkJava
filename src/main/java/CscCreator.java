import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.Collections;

public class CscCreator {
    public static void main(String[] args) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/Library/PostgreSQL/11/bin/pgbench_log.14662"));
            CSVWriter writer = new CSVWriter(new FileWriter("logs.csv"));
            String line = reader.readLine();
            writer.writeAll(Collections.singletonList(new String[]{"x", "y"}));
            int i = 1;
            while (line != null) {
                String[] strings = line.split(" ");
                System.out.println(""+ 1 + " " + strings[2]);
                writer.writeAll(Collections.singletonList(new String[]{"" + i, strings[2]}));
                i+=1;
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
package hashing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static Hashing<PCR> propertyHashing;

    public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException,
            IllegalAccessException, InterruptedException {
        propertyHashing = new Hashing<>("file.bin", 13);

        // mes ajouts

        // PCR hlpProperty = new PCR(10, "Jesais", "Kilo", "10/12/2000", "12/12/2022",
        // "15:12", true);
        for (int i = 0; i < 50; i++) {
            int id = ThreadLocalRandom.current().nextInt(2, 200 + 1);
            // int id = i;
            System.out.println("ID : " + id);
            PCR hlpProperty = new PCR(id, "ccc", "cccc", "12/34/5678", "9A/BC/DEFGH", "IK:" + id, false);
            propertyHashing.Insert(hlpProperty);
            propertyHashing.Find(id);
        }

    }

}

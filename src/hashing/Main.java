package hashing;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    private static Hashing<PCR> propertyHashing;

    public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException,
            IllegalAccessException, InterruptedException {
        propertyHashing = new Hashing<>("file.bin", 13);

        // mes ajouts

        PCR hlpProperty = new PCR(10, "Jesais", "Kilo", "10/12/2000", "12/12/2022", "15:12", true);
        propertyHashing.Insert(hlpProperty);

        propertyHashing.Find(10);
    }

}

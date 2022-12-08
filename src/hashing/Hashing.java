package hashing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hashing<T extends IData> {

    private int blockFactor;
    private RandomAccessFile file;

    public Hashing(String paFileName, int paBlockFactor) {
        this.blockFactor = paBlockFactor;
        try {
            this.file = new RandomAccessFile(paFileName, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T Find(int ID) {
        PCR findPCR = new PCR(ID);
        BitSet hash = findPCR.getHash();

        byte[] blockBytes;
        try {
            file.seek(hash.hashCode());
            blockBytes = new byte[file.readInt()];
            file.read(blockBytes);

            findPCR.FromByteArray(blockBytes);

            // Verif
            System.out.println(findPCR.getName());
            System.out.println(findPCR.getID());
            System.out.println(findPCR.getBorn());
            System.out.println(findPCR.getResult());

        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean Insert(T data) {

        System.out.println(data.getSize());
        BitSet hash = data.getHash();
        System.out.println(hash.hashCode());
        // file.(data.getHash());
        // file.read(block);
        try {
            file.seek(hash.hashCode());
            file.writeInt(data.getSize());
            file.write(data.ToByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // byte[] bytes;
        // PCR dataIn = new PCR();
        // try {
        // file.seek(hash.hashCode());
        // bytes = new byte[file.readInt()];
        // file.read(bytes);
        // dataIn.FromByteArray(bytes);
        // System.out.println(dataIn.getName());
        // System.out.println(dataIn.getID());
        // System.out.println(dataIn.getBorn());
        // System.out.println(dataIn.getResult());
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // file.read();

        // file.read(hash);

        // Block<T> b;
        // b = new Block<>(blockFactor, data.getClass());

        return true;
    }

    public boolean Delete(T data) {

        Block<T> b;
        b = new Block<>(blockFactor, data.getClass());

        return true;
    }

    public long FileSize() {
        try {
            return file.length();
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}

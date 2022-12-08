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
        PCR searchPCR = new PCR(ID);
        BitSet hash = searchPCR.getHash();

        byte[] blockBytes;
        boolean fist = true;
        long positionPointer = 0;
        do {
            try {
                if (fist == true)
                    positionPointer = hash.hashCode();
                file.seek(positionPointer);
                // System.out.println("Position pointeur 1: " + file.getFilePointer());
                char checkSize;
                int filesize;

                // System.out.println("Hcode : " + hash.hashCode());
                // System.out.println(positionPointer);
                do {
                    filesize = file.readInt();
                    checkSize = file.readChar();
                    file.seek(file.getFilePointer() - 5);
                    // System.out.println(checkSize);
                } while (checkSize != '¤');

                file.seek(file.getFilePointer() + 5);
                positionPointer = file.getFilePointer();

                blockBytes = new byte[filesize];
                // System.out.println("Position pointeur 2: " + file.getFilePointer());
                file.read(blockBytes);
                findPCR.FromByteArray(blockBytes);

                // Verif
                // System.out.println(findPCR.getName());
                // System.out.println("Voici ID : " + findPCR.getID());
                // System.out.println(findPCR.getBorn());
                // System.out.println(findPCR.getResult());

                file.read();

            } catch (IOException ex) {
                Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            fist = false;
        } while (findPCR.myEquals(searchPCR) != true);
        return null;

    }

    public boolean Insert(T data) {

        BitSet hash = data.getHash();
        // System.out.println("Taille enregistrement : " + data.getSize());
        // file.(data.getHash());
        // file.read(block);
        try {
            // file.seek(hash.hashCode());
            // byte[] check = new byte[data.getSize()];
            // System.out.println("check : " + file.read(check));

            // int checkResult = file.read(check);
            // if (checkResult == 0 || checkResult == -1) {
            // file.seek(hash.hashCode());
            // file.writeInt(data.getSize());
            // file.write(data.ToByteArray());
            // }

            int checkResult = -2;

            // System.out.println("Hcode insert : " + hash.hashCode());
            do {
                if (checkResult != -2) {
                    file.readByte();
                } else
                    file.seek(hash.hashCode());

                // System.out.println("Position pointeur 00: " + file.getFilePointer());
                byte[] check = new byte[data.getSize()];
                checkResult = file.read(check);

                // System.out.println("check :" + checkResult);
                if (checkResult == 0 || checkResult == -1) {

                    // System.out.println("Position pointeur 11: " + file.getFilePointer());
                    file.writeInt(data.getSize());
                    file.writeChar('¤');

                    // System.out.println("Position pointeur 22: " + file.getFilePointer());
                    file.write(data.ToByteArray());
                } else
                    file.seek(file.getFilePointer() - checkResult);
            } while (checkResult != 0 && checkResult != -1);

        } catch (

        IOException e) {
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

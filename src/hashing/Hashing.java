package hashing;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hashing<T extends IData> {

    private int blockFactor;
    private RandomAccessFile file;
    private final int INDEXCODE = 80;
    private int hRef;
    private int idRef;

    public Hashing(String paFileName, int paBlockFactor, int paIdsize) {
        this.blockFactor = paBlockFactor;
        this.idRef = paIdsize;
        try {
            this.file = new RandomAccessFile(paFileName, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }

        PCR refPCR = new PCR(idRef);
        BitSet hashref = refPCR.getHash();
        this.hRef = hashref.hashCode() * INDEXCODE - 100000;
    }

    public PCR Find(int ID) {
        PCR findPCR = new PCR(ID);
        PCR searchPCR = new PCR(ID);
        BitSet hash = searchPCR.getHash();

        byte[] blockBytes;
        boolean fist = true;
        long positionPointer = 0;
        char checkSize = 'a';
        try {
            do {
                try {
                    if (fist == true)
                        positionPointer = hash.hashCode() * INDEXCODE - hRef;
                    file.seek(positionPointer);
                    int filesize = 0;

                    do {
                        try {
                            filesize = file.readInt();
                            checkSize = file.readChar();
                        } catch (EOFException e) {
                            checkSize = '~';
                        }
                        file.seek(file.getFilePointer() - 5);
                    } while (checkSize != '¤' && checkSize != '~');
                    if (checkSize != '~') {
                        file.seek(file.getFilePointer() + 5);
                        positionPointer = file.getFilePointer();

                        blockBytes = new byte[filesize];
                        file.read(blockBytes);
                        findPCR.FromByteArray(blockBytes);

                        file.read();
                    }

                } catch (IOException ex) {
                    return null;
                }
                fist = false;
            } while (checkSize != '~' && findPCR.myEquals(searchPCR) != true);
        } catch (java.lang.NullPointerException e) {
        }
        if (checkSize == '~') {
            return null;
        } else
            return findPCR;

    }

    public boolean Insert(T data) {

        BitSet hash = data.getHash();
        PCR refPCR = new PCR(idRef);
        BitSet hashref = refPCR.getHash();
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
                    file.seek(hash.hashCode() * INDEXCODE - hRef);
                // System.out.println(hash.hashCode() * INDEXCODE - MINCODE);

                // System.out.println("Position pointeur 00: " + file.getFilePointer());
                byte[] check = new byte[data.getSize()];
                checkResult = file.read(check);

                if (checkResult != 0 || checkResult != -1) {
                    checkResult = 0;
                    for (int i = 0; i < data.getSize(); i++) {
                        if (check[i] != 0) {
                            checkResult = 1;
                            break;
                        }
                    }
                }

                // System.out.println(checkResult);

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

    public boolean Delete(int ID) {
        PCR findPCR = new PCR(ID);
        PCR searchPCR = new PCR(ID);
        PCR refPCR = new PCR(idRef);
        BitSet hashref = refPCR.getHash();
        BitSet hash = searchPCR.getHash();

        byte[] blockBytes;
        boolean fist = true;
        long positionPointer = 0;
        do {
            try {
                if (fist == true)
                    positionPointer = hash.hashCode() * INDEXCODE - hashref.hashCode() * INDEXCODE + 1;
                file.seek(positionPointer);
                char checkSize;
                int filesize;
                long startFilePointer;
                do {
                    startFilePointer = file.getFilePointer();
                    filesize = file.readInt();
                    checkSize = file.readChar();
                    file.seek(file.getFilePointer() - 5);
                } while (checkSize != '¤');

                file.seek(file.getFilePointer() + 5);
                positionPointer = file.getFilePointer();

                blockBytes = new byte[filesize];
                file.read(blockBytes);
                findPCR.FromByteArray(blockBytes);

                if (findPCR.myEquals(searchPCR) == true) {
                    byte[] blockDelete = new byte[filesize + 6];
                    file.seek(startFilePointer);
                    file.write(blockDelete);
                }

                file.read();

            } catch (IOException ex) {
                // Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            fist = false;
        } while (findPCR.myEquals(searchPCR) != true);

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

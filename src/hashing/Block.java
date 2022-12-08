package hashing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block<T extends IData> implements IRecord {

    private final int blockFactor;
    private final int ValidCount;
    private final ArrayList<T> records;
    private final Class<T> classType;

    public Block(int paBlockFactor, Class paClassType) {
        this.blockFactor = paBlockFactor;
        this.classType = paClassType;

        this.records = new ArrayList<T>(paBlockFactor);
        for (int i = 0; i < blockFactor; i++) {
            try {
                this.records.add((T) classType.newInstance().createClass());
            } catch (InstantiationException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ValidCount = 0;
    }

    public boolean insertRecord(T paNew) {

        return true;
    }

    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        // hlpOutStream.write(dummy.ToByteArray());
        return null;
    }

    @Override
    public void FromByteArray(byte[] paArray) {

        // +ValidCount
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray);
        DataInputStream hlpInStream = new DataInputStream(hlpByteArrayInputStream);

        for (int i = 0; i < blockFactor; i++) {
            byte[] n = Arrays.copyOfRange(paArray, i * records.get(i).getSize(), (i + 1) * records.get(i).getSize());
            records.get(i).FromByteArray(n);
        }
    }

    @Override
    public int getSize() {
        try {
            return classType.newInstance().getSize() * blockFactor + Integer.BYTES;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<T> getRecords() {
        return records;
    }

}

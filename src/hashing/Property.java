package hashing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class Property implements IData<Property> {

    private int ID;
    private String name;
    private String description;
    private final static int NAME_LENGTH = 4;
    private final static int DESCRIPTION_LENGTH = 20;

    public Property() {
        this.ID = 10;
        this.name = "Onva";
        this.description = "Je pense que c'est ca";
    }

    public String getName() {
        return name;
    }

    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            hlpOutStream.writeInt(ID);

            hlpOutStream.writeChars(name);
            hlpOutStream.writeChars(description);

            return hlpByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }

    @Override
    public void FromByteArray(byte[] paArray) {
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray);
        DataInputStream hlpInStream = new DataInputStream(hlpByteArrayInputStream);

        try {
            ID = hlpInStream.readInt() / 256;
            name = "";
            for (int i = 0; i < NAME_LENGTH; i++) {
                name += hlpInStream.readChar();
            }
            description = "";
            for (int j = 0; j < DESCRIPTION_LENGTH; j++) {
                description += hlpInStream.readChar();
            }

        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion from byte array.");
        }
    }

    @Override
    public int getSize() {
        return Character.BYTES * (DESCRIPTION_LENGTH + NAME_LENGTH) + Integer.BYTES;
    }

    @Override
    public BitSet getHash() {
        BitSet hash;
        hash = BitSet.valueOf(new long[] { this.ID });
        return hash;
    }

    @Override
    public Property createClass() {
        return new Property();
    }

    @Override
    public boolean myEquals(Property data) {
        return ID == data.ID;
    }
}

package hashing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class PCR implements IData<PCR> {

    private int ID;
    private String name;
    private String surname;
    private String born;
    private String date;
    private String hour;
    private Boolean result;
    private int name_LENGTH;
    private int surname_LENGTH;
    private final static int BORN_LENGTH = 8;
    private final static int DATE_LENGTH = 8;
    private final static int HOUR_LENGTH = 5;

    public PCR() {
        this.ID = -1;
        this.name = "";
        name_LENGTH = name.length();
        this.surname = "";
        surname_LENGTH = surname.length();
        this.born = "00/00/00";
        this.date = "00/00/00";
        this.hour = "00:00";
        this.result = false;
    }

    public PCR(int paID) {
        this.ID = paID;
        this.name = "";
        name_LENGTH = name.length();
        this.surname = "";
        surname_LENGTH = surname.length();
        this.born = "00/00/00";
        this.date = "00/00/00";
        this.hour = "00:00";
        this.result = false;
    }

    public PCR(int paID, String paName, String paSurname, String paBorn, String paDate, String paHour,
            Boolean paResult) {
        this.ID = paID;
        this.name = paName;
        name_LENGTH = paName.length();
        this.surname = paSurname;
        surname_LENGTH = paSurname.length();
        this.born = paBorn;
        this.date = paDate;
        this.hour = paHour;
        this.result = paResult;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBorn() {
        return born;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public Boolean getResult() {
        return result;
    }

    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            hlpOutStream.writeInt(ID);
            hlpOutStream.writeChar('*');

            hlpOutStream.writeInt(name_LENGTH);
            hlpOutStream.writeChars(name);
            hlpOutStream.writeInt(surname_LENGTH);
            hlpOutStream.writeChars(surname);
            hlpOutStream.writeChars(born);
            hlpOutStream.writeChars(date);
            hlpOutStream.writeChars(hour);
            hlpOutStream.writeBoolean(result);

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
            ID = hlpInStream.readInt();
            // System.out.println(ID);
            hlpInStream.readChar();
            // System.out.println("Retour char: " + hlpInStream.readChar());

            // System.out.println(hlpInStream.readInt());
            name_LENGTH = hlpInStream.readInt();
            // System.out.println("name : " + name_LENGTH);
            name = "";
            for (int i = 0; i < name_LENGTH; i++) {
                name += hlpInStream.readChar();
            }
            // System.out.println("surname : " + surname_LENGTH);
            surname_LENGTH = hlpInStream.readInt();
            surname = "";
            for (int i = 0; i < surname_LENGTH; i++) {
                surname += hlpInStream.readChar();
            }
            born = "";
            for (int i = 0; i < BORN_LENGTH; i++) {
                born += hlpInStream.readChar();
            }
            date = "";
            for (int i = 0; i < DATE_LENGTH; i++) {
                date += hlpInStream.readChar();
            }
            hour = "";
            for (int i = 0; i < HOUR_LENGTH; i++) {
                hour += hlpInStream.readChar();
            }
            result = hlpInStream.readBoolean();

        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion from byte array.");
        }
    }

    @Override
    public int getSize() {
        return Character.BYTES * (name_LENGTH + surname_LENGTH + BORN_LENGTH + DATE_LENGTH + HOUR_LENGTH + 1)
                + Integer.BYTES * 3 + 1;
    }

    @Override
    public BitSet getHash() {
        BitSet hash;
        hash = BitSet.valueOf(new long[] { this.ID });
        return hash;
    }

    @Override
    public PCR createClass() {
        return new PCR();
    }

    @Override
    public boolean myEquals(PCR data) {
        return ID == data.ID;
    }
}

package uia.utils;

public class ShortUtils {

    public static byte[] toBytes(short data) {
        byte[] value = new byte[2];
        value[0] = (byte) (data >> 8);
        value[1] = (byte) data;
        return value;
    }
}

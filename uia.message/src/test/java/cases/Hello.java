package cases;

public class Hello {
    private byte[] header;
    private int number;
    private byte tailer;

    public byte[] getHeader() {
        return this.header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public byte getTailer() {
        return this.tailer;
    }

    public void setTailer(byte tailer) {
        this.tailer = tailer;
    }
}

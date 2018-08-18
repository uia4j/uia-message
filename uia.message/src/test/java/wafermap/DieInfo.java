package wafermap;

public class DieInfo {

    private int marking;

    private int testResult;

    private byte[] reserved1;

    private byte[] reserved2;

    public DieInfo() {
        this.reserved1 = new byte[0];
        this.reserved2 = new byte[0];
    }

    public int getTestResult() {
        return this.testResult;
    }

    public void setTestResult(int testResult) {
        this.testResult = testResult;
    }

    public int getMarking() {
        return this.marking;
    }

    public void setMarking(int marking) {
        this.marking = marking;
    }

    public byte[] getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(byte[] reserved1) {
        this.reserved1 = reserved1;
    }

    public byte[] getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(byte[] reserved2) {
        this.reserved2 = reserved2;
    }
}

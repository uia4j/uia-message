package uia.message;

public class BlockInfo {

	private final Object value;

	private final byte[] data;

	private final int bitLength;

	public BlockInfo(Object value, byte[] data, int bitLength) {
		this.value = value;
		this.data = data;
		this.bitLength = bitLength;
	}

	public Object getValue() {
		return this.value;
	}

	public byte[] getData() {
		return this.data;
	}

	public int getBitLength() {
		return this.bitLength;
	}
}

package uia.message.codec;

import org.junit.Test;

import uia.utils.ByteUtils;

public class BooleanCodecTest {

	@Test
	public void testDecode() throws BlockCodecException {
		BooleanCodec codec = new BooleanCodec();
		codec.setTrueValue("1");
		System.out.println(codec.decode(new byte[] { (byte) 0xbf }, 1));
		codec.setTrueValue("0");
		System.out.println(codec.decode(new byte[] { (byte) 0x7f }, 2));
		codec.setTrueValue("1");
		System.out.println(codec.decode(new byte[] { (byte) 0x3f }, 3));
		codec.setTrueValue("0");
		System.out.println(codec.decode(new byte[] { (byte) 0x1f }, 4));
		codec.setTrueValue("1");
		System.out.println(codec.decode(new byte[] { (byte) 0x08 }, 5));
		codec.setTrueValue("0");
		System.out.println(codec.decode(new byte[] { (byte) 0x04 }, 6));
		codec.setTrueValue("1");
		System.out.println(codec.decode(new byte[] { (byte) 0x02 }, 7));
		codec.setTrueValue("0");
		System.out.println(codec.decode(new byte[] { (byte) 0x01 }, 8));
	}

	@Test
	public void testEncode() throws BlockCodecException {
		BooleanCodec codec = new BooleanCodec();
		System.out.println(ByteUtils.toBitString(codec.encode(true, 1)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 2)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 3)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 4)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 5)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 6)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 7)));
		System.out.println(ByteUtils.toBitString(codec.encode(true, 8)));
	}
}

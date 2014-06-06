package info.airsen.serialize.tool.custom;

import java.nio.ByteBuffer;

public class Serialization {

	//byte
	public static int getNeedBytes(byte b) {
		return 1;
	}

	public static byte encodeByte(byte b) {
		return Codec.encode(b);
	}

	public static byte decodeByte(byte[] value) {
		return Codec.decodeByte(value);
	}

	public static byte decodeByte(ByteBuffer buffer) {
		return Codec.decodeByte(buffer);
	}

	//short
	public static int getNeedBytes(short i) {
		return 2;
	}

	public static byte[] encodeShort(short i) {
		return Codec.encode(i);
	}

	public static int decodeShort(byte[] value) {
		return Codec.decodeShort(value);
	}

	public static int decodeShort(ByteBuffer buffer) {
		return Codec.decodeShort(buffer);
	}

	//char
	public static int getNeedBytes(char c) {
		//return 2;
		return Codec.getNeedBytes(c);
	}

	public static byte[] encodeChar(char c) {
		//return Codec.encode(c);
		return Codec.encodeVarChar(c);
	}

	public static int decodeChar(byte[] value) {
		//return Codec.decodeChar(value);
		return Codec.decodeVarChar(value);
	}

	public static int decodeChar(ByteBuffer buffer) {
		//return Codec.decodeChar(buffer);
		return Codec.decodeVarInt(buffer);
	}

	//int
	public static int getNeedBytes(int val) {
		return Codec.getNeedBytes(val);
	}

	public static byte[] encodeInt(int i) {
		return Codec.encodeVarInt(i);
	}

	public static int decodeInt(byte[] value) {
		return Codec.decodeVarInt(value);
	}

	public static int decodeInt(ByteBuffer buffer) {
		return Codec.decodeVarInt(buffer);
	}

	//long
	public static int getNeedBytes(long l) {
		return Codec.getNeedBytes(l);
	}

	public static byte[] encodeLong(long l) {
		return Codec.encodeVarLong(l);
	}

	public static long decodeLong(byte[] value) {
		return Codec.decodeVarLong(value);
	}

	public static long decodeLong(ByteBuffer buffer) {
		return Codec.decodeVarLong(buffer);
	}

	//float
	public static int getNeedBytes(float f) {
		return 4;
	}

	public static byte[] encodeFloat(float f) {
		return Codec.encode(Float.floatToRawIntBits(f));
	}

	public static float decodeFloat(byte[] bytes) {
		return Codec.decodeFloat(bytes);
	}

	public static float decodeFloat(ByteBuffer buffer) {
		return Codec.decodeFloat(buffer);
	}

	//double
	public static int getNeedBytes(double d) {
		return 8;
	}

	public static byte[] encodeDouble(double d) {
		return Codec.encode(Double.doubleToRawLongBits(d));
	}

	public static double decodeDouble(byte[] bytes) {
		return Codec.decodeDouble(bytes);
	}

	public static double decodeDouble(ByteBuffer buffer) {
		return Codec.decodeDouble(buffer);
	}


	// boolean
	public static int getNeedBytes(boolean b) {
		return 1;
	}

	public static byte encodeBoolean(boolean b) {
		return Codec.encode(b);
	}

	public static boolean decodeBoolean(byte[] value) {
		return Codec.decodeBoolean(value);
	}

	public static boolean decodeBoolean(ByteBuffer buffer) {
		return Codec.decodeBoolean(buffer);
	}

	//String
	public static int getNeedBytes(String str) {
		return Codec.getNeedBytes(str, Codec.DEFAULT_CHARSET);
	}

	public static byte[] encodeString(String b) {
		return Codec.encodeString(b, Codec.DEFAULT_CHARSET);
	}

	public static String decodeString(byte[] value) {
		return Codec.decodeString(value, Codec.DEFAULT_CHARSET);
	}

	public static String decodeString(ByteBuffer buffer) {
		return Codec.decodeString(buffer, Codec.DEFAULT_CHARSET);
	}

	public static void main(String args[]) {
		char x = 'F';
		System.out.println(Short.MAX_VALUE);
		System.out.println(1 << 7);
		System.out.println(Codec.getNeedBytes(128));
		System.out.println(Codec.getNeedBytes(x));

		System.out.println(Double.doubleToRawLongBits(12d));
	}
}
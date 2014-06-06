package info.airsen.serialize.tool.custom;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Codec {
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String NO_CHARSET = "ISO-8859-1";

	private static final long OB_MAX_V1B = (1l << 7) - 1;
	private static final long OB_MAX_V2B = (1l << 14) - 1;
	private static final long OB_MAX_V3B = (1l << 21) - 1;
	private static final long OB_MAX_V4B = (1l << 28) - 1;
	private static final long OB_MAX_V5B = (1l << 35) - 1;
	private static final long OB_MAX_V6B = (1l << 42) - 1;
	private static final long OB_MAX_V7B = (1l << 49) - 1;
	private static final long OB_MAX_V8B = (1l << 56) - 1;
	private static final long OB_MAX_V9B = (1l << 63) - 1;

	private static final long[] OB_MAX = {OB_MAX_V1B, OB_MAX_V2B, OB_MAX_V3B,
			OB_MAX_V4B, OB_MAX_V5B, OB_MAX_V6B, OB_MAX_V7B, OB_MAX_V8B,
			OB_MAX_V9B};

	public static int getNeedBytes(int i) {
		if (i < 0)
			return 5;
		int needBytes = 0;
		for (long max : OB_MAX) {
			needBytes++;
			if (i <= max)
				break;
		}
		return needBytes;
	}

	public static int getNeedBytes(long l) {
		if (l < 0)
			return 10;
		int needBytes = 0;
		for (long max : OB_MAX) {
			needBytes++;
			if (l <= max)
				break;
		}
		return needBytes;
	}

	public static int getNeedBytes(short s) {
		if (s < 0)
			return 3;
		int needBytes = 0;
		for (long max : OB_MAX) {
			needBytes++;
			if (s <= max)
				break;
		}
		return needBytes;
	}

	public static int getNeedBytes(char c) {
		short s = (short) c;
		return getNeedBytes(s);
	}


	public static byte[] encodeVarInt(int i) {
		byte[] ret = new byte[getNeedBytes(i)];
		int index = 0;
		while (i < 0 || i > OB_MAX_V1B) {
			ret[index++] = (byte) (i | 0x80);
			i >>>= 7;
		}
		ret[index] = (byte) (i & 0x7f);
		return ret;
	}

	public static int decodeVarInt(byte[] value) {
		int ret = 0;
		int shift = 0;
		for (byte b : value) {
			ret |= (b & 0x7f) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}

	public static int decodeVarInt(ByteBuffer buffer) {
		int ret = 0;
		int shift = 0;
		while (true) {
			byte b = buffer.get();
			ret |= (b & 0x7f) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}

	public static byte[] encodeVarShort(short s) {
		byte[] ret = new byte[getNeedBytes(s)];
		int index = 0;
		while (s < 0 || s > OB_MAX_V1B) {
			ret[index++] = (byte) (s | 0x80);
			s >>>= 7;
		}
		ret[index] = (byte) (s & 0x7f);
		return ret;
	}

	public static short decodeVarShort(byte[] value) {
		short ret = 0;
		int shift = 0;
		for (byte b : value) {
			ret |= (b & 0x7f) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}

	public static short decodeVarShort(ByteBuffer buffer) {
		short ret = 0;
		int shift = 0;
		while (true) {
			byte b = buffer.get();
			ret |= (b & 0x7f) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}


	public static byte[] encodeVarChar(char c) {
		short s = (short) c;
		return encodeVarShort(s);
	}

	public static char decodeVarChar(byte[] value) {
		short s = decodeVarShort(value);
		return (char) s;
	}

	public static char decodeVarChar(ByteBuffer buffer) {
		short s = decodeVarShort(buffer);
		return (char) s;
	}


	public static byte[] encodeVarLong(long l) {
		byte[] ret = new byte[getNeedBytes(l)];
		int index = 0;
		while (l < 0 || l > OB_MAX_V1B) {
			ret[index++] = (byte) (l | 0x80);
			l >>>= 7;
		}
		ret[index] = (byte) (l & 0x7f);
		return ret;
	}

	public static long decodeVarLong(byte[] value) {
		long ret = 0;
		int shift = 0;
		for (byte b : value) {
			ret |= (b & 0x7fl) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}

	public static long decodeVarLong(ByteBuffer buffer) {
		long ret = 0;
		int shift = 0;
		while (true) {
			byte b = buffer.get();
			ret |= (b & 0x7fl) << shift;
			shift += 7;
			if ((b & 0x80) == 0) {
				break;
			}
		}
		return ret;
	}

	public static int getNeedBytes(String str, String charset) {
		if (str == null)
			str = "";
		int strLen = 0;
		try {
			strLen = str.getBytes(charset).length;
			return getNeedBytes(strLen) + strLen;
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}
	}

	/*public static int getNeedBytes(String str,String charset) {
		if (str == null)
			str = "";
		int strLen = 0;
		try {
			strLen = Compress.jzlib(str.getBytes(charset)).length;
			return getNeedBytes(strLen) + strLen;
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException(e1);
		}
	}*/

	public static byte[] encodeString(String str, String charset) {
		if (str == null)
			str = "";
		try {
			//byte[] data = Compress.jzlib(str.getBytes(charset));
			byte[] data = str.getBytes(charset);
			int dataLen = data.length;
			int strLen = getNeedBytes(dataLen);
			byte[] ret = new byte[strLen + dataLen];
			int index = 0;
			for (byte b : encodeVarInt(dataLen)) {
				ret[index++] = b;
			}
			for (byte b : data) {
				ret[index++] = b;
			}
			return ret;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	public static String decodeString(byte[] value, String charset) {
		ByteBuffer buffer = ByteBuffer.wrap(value);
		return decodeString(buffer, charset);
	}

	public static String decodeString(ByteBuffer buffer, String charset) {
		int dataLen = decodeVarInt(buffer);
		byte[] content = new byte[dataLen];
		buffer.get(content);

		//TODO compress String use zlib
		try {
			//return new String(Compress.unjzlib(content), charset);
			return new String(content, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}


	public static byte encode(byte b) {
		return b;
	}

	public static byte[] encode(short s) {
		byte[] ret = new byte[2];
		ret[1] = (byte) s;
		ret[0] = (byte) (s >>> 8);
		return ret;
	}

	public static byte[] encode(int i) {
		byte[] ret = new byte[4];
		ret[3] = (byte) i;
		ret[2] = (byte) (i >>> 8);
		ret[1] = (byte) (i >>> 16);
		ret[0] = (byte) (i >>> 24);
		return ret;
	}

	public static byte[] encode(long l) {
		byte[] ret = new byte[8];
		ret[7] = (byte) l;
		ret[6] = (byte) (l >>> 8);
		ret[5] = (byte) (l >>> 16);
		ret[4] = (byte) (l >>> 24);
		ret[3] = (byte) (l >>> 32);
		ret[2] = (byte) (l >>> 40);
		ret[1] = (byte) (l >>> 48);
		ret[0] = (byte) (l >>> 56);
		return ret;
	}

	public static byte encode(boolean b) {
		return b ? encode((byte) 1) : encode((byte) 0);
	}

	public static byte[] encode(char c) {
		return encode((short) c);
	}

	//// decode

	public static boolean decodeBoolean(byte[] value) {
		return decodeByte(value) == 0 ? false : true;
	}

	public static boolean decodeBoolean(ByteBuffer buffer) {
		byte[] bytes = new byte[1];
		buffer.get(bytes);
		return decodeBoolean(bytes);
	}

	public static byte decodeByte(byte[] value) {
//		assertTrue(value.length == 1);
		return value[0];
	}

	public static byte decodeByte(ByteBuffer buffer) {
		byte[] bytes = new byte[1];
		buffer.get(bytes);
		return decodeByte(bytes);
	}


	public static short decodeShort(byte[] value) {
//		assertTrue(value.length == 2);
		return (short) ((value[0] << 8) | (value[1] & 0xff));
	}

	public static short decodeShort(ByteBuffer buffer) {
		byte[] bytes = new byte[2];
		buffer.get(bytes);
		return decodeShort(bytes);
	}

	public static char decodeChar(byte[] value) {
//		assertTrue(value.length == 2);
		return (char) decodeShort(value);
	}

	public static char decodeChar(ByteBuffer buffer) {
//		assertTrue(value.length == 2);
		byte[] bytes = new byte[2];
		buffer.get(bytes);
		return decodeChar(bytes);
	}

	public static int decodeInt(byte[] value) {
		//assertTrue(value.length == 4);
		assert value.length == 4;
		return (value[0] << 24) | ((value[1] & 0xff) << 16)
				| ((value[2] & 0xff) << 8) | (value[3] & 0xff);
	}

	public static long decodeLong(byte[] value) {
//	        assertTrue(value.length == 8);
		return ((value[0] & 0xffl) << 56) | ((value[1] & 0xffl) << 48)
				| ((value[2] & 0xffl) << 40) | ((value[3] & 0xffl) << 32)
				| ((value[4] & 0xffl) << 24) | ((value[5] & 0xffl) << 16)
				| ((value[6] & 0xffl) << 8) | ((value[7] & 0xffl) << 0);
	}

	public static float decodeFloat(byte[] bytes) {
		return Float.intBitsToFloat(decodeInt(bytes));
	}

	public static float decodeFloat(ByteBuffer buffer) {
		byte[] bytes = new byte[4];
		buffer.get(bytes);
		return decodeFloat(bytes);
	}

	public static double decodeDouble(byte[] bytes) {
		return Double.longBitsToDouble(decodeLong(bytes));
	}

	public static double decodeDouble(ByteBuffer buffer) {
		byte[] bytes = new byte[8];
		buffer.get(bytes);
		return decodeDouble(bytes);
	}

	private static int getStrLen(String str, String charset)
			throws UnsupportedEncodingException {
		return str.getBytes(charset).length;
	}
}
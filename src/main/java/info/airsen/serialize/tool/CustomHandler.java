package info.airsen.serialize.tool;

import info.airsen.serialize.Data;
import info.airsen.serialize.tool.custom.Serialization;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>自定义处理器</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-5 下午3:18
 */
public class CustomHandler {

	public static void deflate(String sourceFileName, List<Data> list) throws IOException {

		String fileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.')) + ".cst";
		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);

		for (Data data : list) {
			out.write(pure(data));
			out.flush();
		}
		out.close();
		System.out.println("custom\t\t压缩文件大小:" + new File(fileName).length());
	}

	private static byte[] pure(Data data) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();

		bo.write(data.getBool1() == null ? "\001".getBytes() : encode(data.getBool1()));
		bo.write(data.getBool2() == null ? "\001".getBytes() : encode(data.getBool2()));
		bo.write(data.getFloat1() == null ? "\001".getBytes() : encode(data.getFloat1()));
		bo.write(data.getFloat2() == null ? "\001".getBytes() : encode(data.getFloat2()));
		bo.write(data.getInt1() == null ? "\001".getBytes() : encode(data.getInt1()));
		bo.write(data.getInt2() == null ? "\001".getBytes() : encode(data.getInt2()));
		bo.write(data.getString1() == null ? "\001".getBytes() : encode(data.getString1()));
		bo.write(data.getString2() == null ? "\001".getBytes() : encode(data.getString2()));
		return bo.toByteArray();
	}

	private static byte[] encode(Object obj) {
		byte[] result = new byte[0];
		if (obj instanceof String) {
			result = Serialization.encodeString((String) obj);
		} else if (obj instanceof Integer) {
			result = Serialization.encodeInt((Integer) obj);
		} else if (obj instanceof Float) {
			result = Serialization.encodeFloat((Float) obj);
		}
		return result;
	}

}

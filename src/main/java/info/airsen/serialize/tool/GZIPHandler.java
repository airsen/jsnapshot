package info.airsen.serialize.tool;

import info.airsen.serialize.Data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * <p>gzip 处理器</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-3 下午5:48
 */
public class GZIPHandler {

	public static void deflate(String sourceFileName, List<Data> list) throws IOException {
		String fileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.')) + ".gzip";
		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);

		for (Data data : list) {
			String pureData = pure(data);
			out.write(gZip(pureData.getBytes()));
			out.flush();
		}
		out.close();
		System.out.println("gzip\t\t压缩文件大小:" + new File(fileName).length());
	}

	/**
	 * 压缩GZip
	 *
	 * @param data
	 * @return
	 */
	public static byte[] gZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	private static String pure(Data data) {
		StringBuilder builder = new StringBuilder();

		if (data.getBool1() != null)
			builder.append(data.getBool1());
		builder.append('\001');
		if (data.getBool2() != null)
			builder.append(data.getBool2());
		builder.append('\001');
		if (data.getFloat1() != null)
			builder.append(data.getFloat1());
		builder.append('\001');
		if (data.getFloat2() != null)
			builder.append(data.getFloat2());
		builder.append('\001');
		if (data.getInt1() != null)
			builder.append(data.getInt1());
		builder.append('\001');
		if (data.getInt2() != null)
			builder.append(data.getInt2());
		builder.append('\001');
		if (data.getString1() != null)
			builder.append(data.getString1());
		builder.append('\001');
		if (data.getString2() != null)
			builder.append(data.getString2());
		builder.append('\001');
		builder.append('\001');

		return builder.toString();
	}

}

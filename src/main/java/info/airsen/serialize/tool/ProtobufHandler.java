package info.airsen.serialize.tool;

import info.airsen.serialize.Data;
import info.airsen.serialize.tool.ProtobufOperator.data.Builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>pb 处理器</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-3 下午4:21
 */
public class ProtobufHandler {

	public static void deflate(String sourceFileName, List<Data> list) throws IOException {
		String fileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.')) + ".pb";
		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);

		for (Data data : list) {
			Builder builder = ProtobufOperator.data.newBuilder();
			if (data.getBool1() != null)
				builder.setBool1(data.getBool1());
			if (data.getBool2() != null)
				builder.setBool2(data.getBool2());
			if (data.getInt1() != null)
				builder.setInt1(data.getInt1());
			if (data.getInt2() != null)
				builder.setInt2(data.getInt2());
			if (data.getFloat1() != null)
				builder.setFloat1(data.getFloat1());
			if (data.getFloat2() != null)
				builder.setFloat2(data.getFloat2());
			if (data.getString1() != null)
				builder.setString1(data.getString1());
			if (data.getString2() != null)
				builder.setString2(data.getString2());
			ProtobufOperator.data dataBuilder = builder.build();
			dataBuilder.writeTo(out);
		}
		out.flush();
		out.close();
		System.out.println("protobuf\t压缩文件大小:" + new File(fileName).length());

	}

}

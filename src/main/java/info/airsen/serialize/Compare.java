package info.airsen.serialize;

import com.google.gson.reflect.TypeToken;
import info.airsen.serialize.tool.CustomHandler;
import info.airsen.serialize.tool.GZIPHandler;
import info.airsen.serialize.tool.GsonSerializer;
import info.airsen.serialize.tool.ProtobufHandler;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>比较压缩</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-3 下午3:51
 */
public class Compare {

	public static void main(String[] args) throws IOException {

		String tmpPath = "/tmp/compress";
		System.out.print("压缩的数据默认从 " + tmpPath + " 中读取，其他路径请输入:");
		BufferedReader ctlReader = new BufferedReader(new InputStreamReader(System.in));
		String input;

		DataType type = null;
		Integer count;

		while ((input = ctlReader.readLine()) != null) {
			if (!input.equals(""))
				tmpPath = input;
			if (!new File(tmpPath).exists()) {
				System.out.println("没有" + tmpPath + "这个路径…");
				System.exit(1);
			}

			// 类型
			System.out.print("输入要压缩的类型(0.all; 1.int; 2.float; 3.string):");
			input = ctlReader.readLine();
			if ("1".equals(input)) { // int
				type = DataType.INT;
			} else if ("2".equals(input)) {
				type = DataType.FLOAT;
			} else if ("3".equals(input)) {
				type = DataType.STRING;
			} else if ("0".equals(input)) {
				type = DataType.ALL;
			} else {
				System.exit(1);
			}

			// 记录数
			System.out.print("输入要压缩的记录数量: ");
			input = ctlReader.readLine();
			count = Integer.valueOf(input);

			// 读取数据
			String jsonFileName = tmpPath + "/" + count + "." + type + ".json";
			File jsonFile = new File(jsonFileName);
			BufferedReader jsonFileReader = new BufferedReader(new FileReader(jsonFile));
			Type listType = new TypeToken<ArrayList<Data>>() {
			}.getType();
			List<Data> dataList = GsonSerializer.fromJson(jsonFileReader, listType);
			System.out.println("从" + jsonFile.getPath() + " 中读取到共" + dataList.size() + "条记录");

			// 压缩数据
			System.out.println("json\t\t原始文件大小:" + jsonFile.length());
			ProtobufHandler.deflate(jsonFileName, dataList);
			GZIPHandler.deflate(jsonFileName, dataList);
			CustomHandler.deflate(jsonFileName, dataList);

			System.out.print(jsonFile.getPath() + " 压缩成功，继续请按回车:\n");
		}
	}

}

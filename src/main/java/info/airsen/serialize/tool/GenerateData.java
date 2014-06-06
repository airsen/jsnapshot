package info.airsen.serialize.tool;

import info.airsen.serialize.Data;
import info.airsen.serialize.DataType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>产生数据</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-3 下午2:21
 */
public class GenerateData {

	public static void main(String[] args) throws IOException {

		String tmpPath = "/tmp/compress";
		System.out.print("压缩的数据默认从 " + tmpPath + " 中读取，其他路径请输入:");
		BufferedReader ctlReader = new BufferedReader(new InputStreamReader(System.in));
		String input;
		Random random = new Random();

		DataType type = null;
		Integer count;
		while ((input = ctlReader.readLine()) != null) {
			if (!input.equals(""))
				tmpPath = input;
			else
				new File(tmpPath).mkdirs();
			if (!new File(tmpPath).exists())
				System.exit(1);

			// 类型
			System.out.print("输入要测试的类型(0.all; 1.int; 2.float; 3.string):");
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
			System.out.print("输入要测试的记录数量: ");
			input = ctlReader.readLine();
			count = Integer.valueOf(input);

			// 产生随机数据
			List<Data> dataList = new ArrayList<Data>();
			for (int i = 0; i < count; i++) {
				Data data = new Data();
				switch (type) {
					case INT:
						data.setInt1(random.nextInt());
						data.setInt2(random.nextInt());
						break;
					case FLOAT:
						data.setFloat1(random.nextFloat());
						data.setFloat2(random.nextFloat());
						break;
					case STRING:
						data.setString1(getRandomeString(7500));
						data.setString2(getRandomeString(2500));
						break;
					case ALL:
					default:
						data.setBool1(random.nextBoolean());
						data.setBool2(random.nextBoolean());
						data.setInt1(random.nextInt());
						data.setInt2(random.nextInt());
						data.setFloat1(random.nextFloat());
						data.setFloat2(random.nextFloat());
						data.setString1(getRandomeString(7500));
						data.setString2(getRandomeString(2500));
						break;
				}
				dataList.add(data);
			}

			// 写入文件
			String json = GsonSerializer.toJson(dataList);
			File jsonFile = new File(tmpPath + "/" + count + "." + type + ".json");
			FileOutputStream out = new FileOutputStream(jsonFile);
			out.write(json.getBytes());
			out.flush();
			out.close();
			System.out.print(jsonFile.getPath() + "生成成功，继续请按回车:\n");
		}
	}


	private static String getRandomeString(Integer size) {
		String tpl = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		while (builder.length() < size) {
			builder.append(tpl.charAt(random.nextInt(tpl.length())));
		}
		return builder.toString();
	}

}

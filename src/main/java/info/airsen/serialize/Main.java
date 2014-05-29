package info.airsen.serialize;

import com.google.gson.reflect.TypeToken;
import info.airsen.serialize.UserOperation.user.Builder;
import info.airsen.serialize.json.GsonSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>序列化比较</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-5-28 下午10:01
 */
public class Main {

	public static void main(String[] args) throws IOException {
		int recordCount = 1000000;
		int type = Prepare.INTEGER;

		String jsonName = "src/main/java/info/airsen/serialize/json/list_" + type + "_" + recordCount + ".json";
		String cpdName = "src/main/java/info/airsen/serialize/protobuf/user_" + recordCount + ".cpd";

		// 读取到内存中
		File jsonFile = new File(jsonName);

		BufferedReader br = new BufferedReader(new FileReader(jsonFile));

		Type listType = new TypeToken<ArrayList<User>>() {
		}.getType();

		List<User> userList = GsonSerializer.fromJson(br, listType);
		System.out.println("总共记录数\t\t:" + userList.size());

		// protobuf
		File protobufFile = new File(cpdName);
		FileOutputStream out = new FileOutputStream(protobufFile);
		for (User user : userList) {
			Builder builder = UserOperation.user.newBuilder();

			builder.setDesc(user.getDesc())
					.setNickname(user.getNickname())
					.setId(user.getId())
					.setName(user.getName())
					.setHomepage(user.getHomepage())
					.setMale(user.getMale())
					.setPrice(user.getPrice());

			UserOperation.user userBuilder = builder.build();
			userBuilder.writeTo(out);
		}
		out.flush();
		out.close();
		System.out.println("protobuf文件大小\t:" + new File(cpdName).length());
		System.out.println("json文件大小\t\t:" + new File(jsonName).length());

		System.out.println("大小比值\t\t\t:" + (float) new File(cpdName).length() / new File(jsonName).length());
	}

}

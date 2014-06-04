package info.airsen.serialize.tool;

import info.airsen.serialize.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-6-3 下午5:48
 */
public class GZIPHandler {

	public static void deflate(String sourceFileName, List<Data> list) throws FileNotFoundException {
		String fileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.')) + ".gzip";
		File file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);


	}

	private static String pure(Data data) {
		StringBuilder builder = new StringBuilder();

		builder.append('\001')
//				.append(user.getId())
//				.append('\002')
//				.append(user.getName()).append('\002')
//				.append(user.getDesc()).append('\002')
//				.append(user.getHomepage()).append('\002')
//				.append(user.getMale()).append('\002')
//				.append(user.getNickname()).append('\002')
//				.append(user.getPrice())
				.append('\001');
		return builder.toString();
	}

}

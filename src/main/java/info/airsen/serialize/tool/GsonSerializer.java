package info.airsen.serialize.tool;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>Gson序列化工具</p>
 *
 * @author 主公 (jason61719@gmail.com)
 * @since 14-3-31 上午10:47
 */
public class GsonSerializer {

	public final static Gson GSON;
	private final static SimpleDateFormat simpleDateFormat;

	static {
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		GSON = new GsonBuilder()
				.enableComplexMapKeySerialization()
				.setExclusionStrategies(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						return f.getName().equals("PARAVALUE_REGEX");
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				}).disableHtmlEscaping().registerTypeAdapter(Date.class, new TypeAdapter<Date>() {

					@Override
					public void write(JsonWriter writer, Date value) throws IOException {
						if (value == null) {
							writer.nullValue();
							return;
						}
						String date = simpleDateFormat.format(value);
						writer.value(date);
					}

					@Override
					public Date read(JsonReader reader) throws IOException {
						if (reader.peek() == JsonToken.NULL) {
							reader.nextNull();
							return null;
						}
						Date date = null;
						try {
							date = simpleDateFormat.parse(reader.nextString());
						} catch (ParseException e) {
						}
						return date;
					}
				}).create();
	}

	public static String toJson(Object t) {
		return GSON.toJson(t);
	}

	public static Map<String, String> toJson(String str) {
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> result;
		try {
			result = GSON.fromJson(str, type);
		} catch (JsonSyntaxException e) {
			return null;
		}
		return result;
	}

	public static <T> T fromJson(String json, Class<T> type) {
		return GSON.fromJson(json, type);
	}

	public static <T> T fromJson(String json, Type type) {
		return GSON.fromJson(json, type);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(Reader reader, Type type) {
		return (T) GSON.fromJson(reader, type);
	}

}

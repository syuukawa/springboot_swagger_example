package guru.springframework.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Customize JSON serialization using Jackson.
 *
 * @author liaoxuefeng
 */
public class JsonUtil {

	/**
	 * Holds ObjectMapper.
	 */
	public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

	private static ObjectMapper createObjectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// disabled features:
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// add java8 time support:
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	public static byte[] writeJsonAsBytes(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String writeJson(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String writeJsonWithPrettyPrint(Object obj) {
		try {
			return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(String str, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(str, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(Reader reader, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(reader, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(Reader reader, TypeReference<T> ref) {
		try {
			return OBJECT_MAPPER.readValue(reader, ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(InputStream input, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(input, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(InputStream input, TypeReference<T> ref) {
		try {
			return OBJECT_MAPPER.readValue(input, ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(String str, TypeReference<T> ref) {
		try {
			return OBJECT_MAPPER.readValue(str, ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(byte[] src, TypeReference<T> ref) {
		try {
			return OBJECT_MAPPER.readValue(src, ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(byte[] src, Class<T> clazz) {
		try {
			return OBJECT_MAPPER.readValue(src, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> readJsonAsMap(String str) {
		try {
			return OBJECT_MAPPER.readValue(str, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

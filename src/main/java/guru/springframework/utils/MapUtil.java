package guru.springframework.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Map util.
 *
 * @author liaoxuefeng
 */
public class MapUtil {

	public final static Map<String, Boolean> RESULT_TRUE = Collections.singletonMap("result", Boolean.TRUE);

	public final static Map<String, Boolean> RESULT_FALSE = Collections.singletonMap("result", Boolean.FALSE);

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		map.put(k6, v6);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7,
			V v7) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		map.put(k6, v6);
		map.put(k7, v7);
		return map;
	}

	/**
	 * Create a HashMap by key-value.
	 */
	public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7,
			V v7, K k8, V v8) {
		Map<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		map.put(k6, v6);
		map.put(k7, v7);
		map.put(k8, v8);
		return map;
	}
}

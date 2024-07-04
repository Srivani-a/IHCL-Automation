package com.ihclweb.automation.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class Registry {

	private static Map<String, Object> vault = new HashMap<>();
	private Registry() {}
	
	
	public static void setAttribute(String key, Object val){
		if(Objects.nonNull(key)){
			vault.put(key, val);
		}
	}
	
	public static void setAttributes(Map<String, Object> attributes){
		vault.putAll(attributes);
	}
	
	public static Object getAttribute(String key) {
		if(Objects.nonNull(key)) {
			ensureKeyIsPresent(key);
			return vault.get(key);
		} else {
			return key;
		}
	}
	
	public static <T> T getAttribute(String key, Class<T> clazz){
		return Objects.nonNull(getAttribute(key)) ? clazz.cast(getAttribute(key)) : null;
		}
	
	public static Object removeAttribute(String key) {
		return (Objects.nonNull(key) && vault.containsKey(key))? vault.remove(key) : key;
	}
	
	public static List<Object> removeAttributes(String... keys) {
		List<Object> removedValues = new ArrayList<>();
		for(String key : keys) {
			if(vault.containsKey(key)) {
				removedValues.add(vault.remove(key));
			}
		}
		return removedValues;
	}

	public static void removeAll() {
		vault.clear();
	}
	
	private static void ensureKeyIsPresent(String key) {
		if(!vault.containsKey(key)) {
			vault.put(key, null);
		}
	}
}

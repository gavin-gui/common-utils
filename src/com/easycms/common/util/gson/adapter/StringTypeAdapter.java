package com.easycms.common.util.gson.adapter;

import java.lang.reflect.Type;

import com.easycms.common.util.CommonUtility;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StringTypeAdapter implements JsonSerializer<String>, JsonDeserializer<String> {

	/**
	 * 反序列化
	 */
	@Override
	public String deserialize(JsonElement json, Type typeOfT,  
            JsonDeserializationContext context) throws JsonParseException {
		if(json == null) {
			return null;
		}
		
		String value = json.getAsString();
		if(!CommonUtility.isNonEmpty(value)) {
			return null;
		}
		return value;
	}

	/**
	 * 序列化
	 */
	@Override
	public JsonElement serialize(String src, Type typeOfSrc,  
            JsonSerializationContext context) {
		if(!CommonUtility.isNonEmpty(src)) {
			return null;
		}
		return new JsonPrimitive(src);
	}

}

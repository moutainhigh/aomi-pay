package com.aomi.pay.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;


public class FastJsonUtil {
	
	public static SerializerFeature[] getSerializerFeatures() {
		SerializerFeature[] serializerFeatures = new SerializerFeature[]{
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteDateUseDateFormat,//@JSONField (format="yyyy-MM-dd HH:mm:ss") 
//				SerializerFeature.PrettyFormat,
				SerializerFeature.WriteNonStringValueAsString,
		};
		return serializerFeatures;
	}
	
	public static ValueFilter getValueFilter() {
		ValueFilter filter = new ValueFilter() {
		    public Object process(Object obj, String s, Object v) {
		    	if(v == null) {
//		    		return "";
		    	}
		    	return v;
		    }
		};
		return filter;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T jsonToBean(String json,Class clazz) {
		return (T) JSON.parseObject(json, clazz);
	}
	
	public static String beanToJson(Object object) {
		return JSON.toJSONString(object,getValueFilter(),getSerializerFeatures());
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> jsonToMap(String json){
		return JSON.parseObject(json, Map.class);
	}

}


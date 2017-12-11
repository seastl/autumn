package com.autumn.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class JsonUtils {
  public static final String getJSONString(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException("error while getting JSON String from object " + object, ex);
    }
  }
  
  
  public static final <T> T getObjectFromJSONString(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    T object = null;
    if(!StringUtils.isEmpty(json)) {
      try {
        object = mapper.readValue(json, clazz);
      } catch (IOException ex) {
        throw new RuntimeException("can't convert a json String " + json + " to an Object type " + clazz.getName(), ex);
      }
    }
    
    return object;
  }

  public static final <T> List<T> getObjectListFromJSONStrings(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    List<T> list = null;
    if(!StringUtils.isEmpty(json)) {
      JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
      try {
        list = mapper.readValue(json, type);
      } catch (IOException ex) {
        throw new RuntimeException("can't convert a json String " + json + " to an Object type " + clazz.getName(), ex);
      }
    }

    return list;
  }
}

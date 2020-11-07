package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.payload.LoginRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConverterJson {
    public static Object parseJSON(String json){
        Type type = new TypeToken<LoginRequest>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static String converterToJSON(Object object){
        return new Gson().toJson(object, Object.class);
    }

}

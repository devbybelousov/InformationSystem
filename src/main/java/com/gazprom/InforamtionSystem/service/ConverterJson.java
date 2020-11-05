package com.gazprom.InforamtionSystem.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConverterJson {
    public static Object parseJSON(String json){
        Type type = new TypeToken<Object>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static String converterToJSON(Object questionDTO){
        return new Gson().toJson(questionDTO, Object.class);
    }

}

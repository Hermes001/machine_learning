package com.socket.pad.paddemo.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GjsonUtils {

    public static String listToJson(ArrayList<Integer> inputArray)
    {
        Gson gson = new Gson();
        String inputString= gson.toJson(inputArray);
        return inputString;
    }

    public static ArrayList<Integer> jsonToList(String json)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> finalOutputString = gson.fromJson(json, type);
        return finalOutputString;
    }
}

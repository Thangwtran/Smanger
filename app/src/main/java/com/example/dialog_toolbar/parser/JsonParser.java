package com.example.dialog_toolbar.parser;

import android.content.Context;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public JsonParser(){

    }

    public String getJsonFromFile(Context context){
        StringWriter writer = new StringWriter();
        char[] buffer = new char[1024];
        try(InputStream inputStream = context.getResources().openRawResource(R.raw.student)){
            Reader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            int size;
            while((size = reader.read(buffer)) != -1){
                writer.write(buffer,0,size);
            }
        }catch (IOException ignored){
        }
        return writer.toString();
    }

    public List<Student> loadData(String jsonStr){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(jsonStr, new TypeReference<List<Student>>() {
            });
        }catch (JsonProcessingException e){
            return new ArrayList<>();
        }
    }
}

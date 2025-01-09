package com.biblioteca;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import org.json.JSONException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;


public class llibres {
    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.US);
            String filePath = "../JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONObject jsonObject = new JSONObject(content);


        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al procesar el JSON: " + e.getMessage());
        }
    }
}

package com.biblioteca;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;


public class llibres {
    public static void main(Scanner scanner) {
        try {
            String filePath = "../JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            System.out.println("Introdueix l'ID del llibre: ");
            int id = scanner.nextInt();

            System.out.println("Introdueix el titol del llibre: ");
            String titol = scanner.nextLine();

            System.out.println("Introduiex l'autor del llibre: ");
            String autor = scanner.nextLine();

            JSONObject nouLlibre = new JSONObject();
            nouLlibre.put("id", id);
            nouLlibre.put("titol", titol);
            nouLlibre.put("autor", autor);

            llibresArray.put(nouLlibre);
            
            Files.write(Paths.get(filePath), llibresArray.toString(4).getBytes());

            System.out.println("Llibre afegit correctament.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al procesar el JSON: " + e.getMessage());
        }
    }
}

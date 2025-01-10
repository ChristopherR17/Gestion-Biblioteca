package com.biblioteca;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class functions {
    //Funciones de estetica de menu
    public static void menu (){
        System.err.println("Gestió de bilioteca");
        System.out.println("1. Llibres");
        System.err.println("2. Usuaris");
        System.out.println("3. Préstecs");
        System.out.println("0. Sortir");
    }
    
    public static void menuBooks (Scanner scanner){
        System.out.println("Gestió de llibres");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar");
        System.out.println("0. Tornar el menú principal");

        String optionBooks = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionBooks.equals("afegir") || optionBooks.equals("1")){
                addBook(scanner);
                break;

            } else if (optionBooks.equals("modificar") || optionBooks.equals("2")){
                break;

            } else if (optionBooks.equals("eliminar") || optionBooks.equals("3")){
                break;

            } else if (optionBooks.equals("llistar") || optionBooks.equals("4")){
                break;

            } else if (optionBooks.equals("menu") || optionBooks.equals("0")){
                menu();
                break;
            }

        }
        
    }

    public static void menUsers(Scanner scanner){
        System.out.println("Gestió d'usuaris");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar"); 
        System.out.println("0. Tornar el menú principal");

        String optionUsers = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionUsers.equals("afegir") || optionUsers.equals("1")){
                afegirUsuaris(scanner);
                break;

            } else if (optionUsers.equals("modificar") || optionUsers.equals("2")){
                break;

            } else if (optionUsers.equals("eliminar") || optionUsers.equals("3")){
                break;

            } else if (optionUsers.equals("llistar") || optionUsers.equals("4")){
                break;

            } else if (optionUsers.equals("menu") || optionUsers.equals("0")){
                menu();
                break;
            }

        }

    }

    public static void menuPrestecs(Scanner scanner){
        System.out.println("Gestió dels préstecs");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar"); 
        System.out.println("0. Tornar el menú principal");

        String optionPrestecs = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionPrestecs.equals("afegir") || optionPrestecs.equals("1")){
                break;
            } else if (optionPrestecs.equals("modificar") || optionPrestecs.equals("2")){
                break;
            } else if (optionPrestecs.equals("eliminar") || optionPrestecs.equals("3")){
                break;
            } else if (optionPrestecs.equals("llistar") || optionPrestecs.equals("4")){
                break;
            } else if (optionPrestecs.equals("menu") || optionPrestecs.equals("0")){
                menu();
                break;
            }

        }
    }

    public static void menuListBooks (Scanner scanner){
        System.out.println("Llistar llibres");
        System.out.println("1. Tots");
        System.out.println("2. En préstec");
        System.out.println("3. Per autor");
        System.out.println("4. Cercar títol");
        System.out.println("Tornar al menú de llibres");

        String listBooksBy = scanner.nextLine().toLowerCase();
        while (true) {
            if (listBooksBy.equals("afegir") || listBooksBy.equals("1")){
                break;
            } else if (listBooksBy.equals("modificar") || listBooksBy.equals("2")){
                break;
            } else if (listBooksBy.equals("eliminar") || listBooksBy.equals("3")){
                break;
            } else if (listBooksBy.equals("llistar") || listBooksBy.equals("4")){
                break;
            } else if (listBooksBy.equals("menu") || listBooksBy.equals("0")){
                menu();
                break;
            }

        }
    }

    public static void addBook (Scanner scanner){
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            System.out.println("Introdueix l'ID del llibre: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            boolean idExists = false;
            for (int i = 0; i < llibresArray.length(); i++) {
                JSONObject llibre = llibresArray.getJSONObject(i);
                if (llibre.getInt("id") == id){
                    idExists = true;
                    break;
                }
            }

            if (idExists){
                System.out.println("Error: L'ID ja existeix. Introdueix una ID única.");
                return;
            }

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
    //USUARIOS  
    public static void comprobarTelefon(int num,JSONArray llista)  throws IllegalArgumentException{
        /**
         * Funcion que comprueba si el numero de telefono existe pq no pueden haber dos iguales.
         * @param num -> Es el numero de telefono del usuario
         * @param llista -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * lanza una excepcion para que no pete el code
         */


        for (int i = 0; i < llista.length();i++){
            JSONObject user = llista.getJSONObject(i);
            int telefon = user.getInt("telefon");
            if (num == telefon){
                throw new IllegalArgumentException("El numero de telefon ja existeix");}
        }
    }
    public static void comprobarLlongitud(int num)  throws IllegalArgumentException{
        /**
         * Funcion que comprueba si el numero de telefono tiene la longitud necesaria.
         * @param num -> Es el numero de telefono del usuario
         * lanza una excepcion para que no pete el code 
         */
        int len_telefon = String.valueOf(num).length();
        if (len_telefon != 9){
            throw new IllegalArgumentException("El numero de telefon no es correcte");
        }
    }
    public static void afegirUsuaris(Scanner scanner) {
        /**
         * Funcion que añade usuarios
         * @param scanner -> valores que pasa el usuario
         */

        
        String filePath = "JSON/usuaris.json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            System.out.println("Introdueix el nom: ");
            String nom = scanner.nextLine();

            System.out.println("Introdueix el cognom: ");
            String cognom = scanner.nextLine();

            System.out.println("Introdueix el numero de telefon: ");
            int telefon = scanner.nextInt();

            comprobarLlongitud(telefon); // LLamamos a las funciones anteriores para comprobar que sea correcto
            comprobarTelefon(telefon, jsonArray);

            int id = jsonArray.length() + 1;
            JSONObject usuari = new JSONObject(); // Creamos un objeto nuevo para poner los valores pasados
            usuari.put("id", id);
            usuari.put("nom", nom);
            usuari.put("cognom", cognom);
            usuari.put("telefon", telefon);

            jsonArray.put(usuari);
            PrintWriter out = new PrintWriter(filePath);
            out.write(jsonArray.toString(4)); // Escribimos el json(el 4 es puramente visual para verlo nosotros mejor rollo lo pone por lineas en vez de todo apelotonado)
            out.flush();
            out.close();  //con estos dos (flush y close) cerramos el json
            System.out.println("Usuari afegit");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al accedir al fitxer: " + e.getMessage());
        }
    }
}
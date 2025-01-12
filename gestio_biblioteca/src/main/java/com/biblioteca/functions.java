package com.biblioteca;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class functions {
    //Funciones de estetica de menu
    public static void menu (){
        System.out.println("==========================");
        System.out.println("Gestió de bilioteca");
        System.out.println("1. Llibres");
        System.err.println("2. Usuaris");
        System.out.println("3. Préstecs");
        System.out.println("0. Sortir");
    }
    
    public static void menuBooks (Scanner scanner){
        System.out.println("==========================");
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
                modifyBook(scanner);
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
        System.out.println("==========================");
        System.out.println("Gestió d'usuaris");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar"); 
        System.out.println("0. Tornar el menú principal");

        String optionUsers = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionUsers.equals("afegir") || optionUsers.equals("1")){
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
        System.out.println("==========================");
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
        System.out.println("==========================");
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

    /*
     * Estas son las funciones que sirven para añadir, modificar y eliminar libros.
     */

    public static void addBook (Scanner scanner){
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            int id;
            while (true) {
                System.out.println("===========================");
                System.out.println("Introdueix l'ID del llibre: ");
                String idInput = scanner.nextLine().trim();
                if (idInput.isEmpty()) {
                    System.out.println("Error: L'ID no pot estar buit. Introdueix un valor vàlid.");
                    continue; 
                }
                try {
                    id = Integer.parseInt(idInput); 
                    break; 
                } catch (NumberFormatException e) {
                    System.out.println("Error: L'ID ha de ser un número. Introdueix un valor vàlid.");
                }
            }

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

            String titol;
            while(true){
                System.out.println("Introdueix el titol del llibre: ");
                titol = scanner.nextLine();
                
                if (titol.isEmpty()){
                    System.out.println("Error: El títol no pot estar buit. Introduiex un valor vàlid.");
                } else {
                    break;
                }
            }

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
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al procesar el JSON: " + e.getMessage());
        }
    }

    public static void modifyBook(Scanner scanner){
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            System.out.println("Introdueix l'ID del llibre que vols modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            JSONObject llibreModificar = null;
            for (int i = 0; i < llibresArray.length(); i++) {
                JSONObject llibre = llibresArray.getJSONObject(i);
                if (llibre.getInt("id") == id){
                    llibreModificar = llibre;
                    break;
                }
            }

            if (llibreModificar == null){
                System.out.println("Error: No s'ha trobat cap llibre amb aquesta ID.");
                return;
            }

            System.out.println("Llibre trobat: ");
            System.out.println("ID: " + llibreModificar.getInt("id"));
            System.out.println("Títol: " + llibreModificar.getString("titol"));
            System.out.println("Autor: " + llibreModificar.getString("autor"));

            System.out.println("Introdueix el nou títol del llibre (deixa'l buit per mantenir el títol actual):");
            String nouTitol = scanner.nextLine().trim();
            if (!nouTitol.isEmpty()){
                llibreModificar.put("titol", nouTitol);
            }

            System.out.println("Introdueix el nou autor del llibre (deixa'l buit per mantenir l'autor actual):");
            String nouAutor = scanner.nextLine().trim();
            if (!nouAutor.isEmpty()){
                llibreModificar.put("autor", nouAutor);
            }

            Files.write(Paths.get(filePath), llibresArray.toString(4).getBytes());

        } catch (IOException e) {
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al processar el JSON: " + e.getMessage());
        }
    }
}

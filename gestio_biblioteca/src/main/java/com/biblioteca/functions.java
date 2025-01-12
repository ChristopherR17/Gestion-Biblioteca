package com.biblioteca;
import java.io.IOException;
import java.io.PrintWriter;
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
                deleteBook(scanner);
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
                afegirUsuaris(scanner);
                break;

            } else if (optionUsers.equals("modificar") || optionUsers.equals("2")){
                break;

            } else if (optionUsers.equals("eliminar") || optionUsers.equals("3")){
                eliminarUsuario(scanner);
                break;

            } else if (optionUsers.equals("llistar") || optionUsers.equals("4")){
                llistarUsuaris(scanner);
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

    //LIBROS
    public static void addBook (Scanner scanner){
        /**
         * Funcion que elimina libros
         * @param llibresArray -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * Lanza una excepcion para que el codigo no falle
         */
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
        /**
         * Funcion que elimina libros
         * @param llibresArray -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * Lanza una excepcion para que el codigo no falle
         */
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

    public static void deleteBook(Scanner scanner) {
        /**
         * Funcion que elimina libros
         * @param llibresArray -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * Lanza una excepcion para que el codigo no falle
         */
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
    
            JSONArray llibresArray = new JSONArray(content);
    
            System.out.println("Introdueix l'ID del llibre que vols eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
    
            boolean llibreEliminat = false;
            for (int i = 0; i < llibresArray.length(); i++) {
                JSONObject llibre = llibresArray.getJSONObject(i);
                if (llibre.getInt("id") == id) {
                    llibresArray.remove(i); 
                    llibreEliminat = true;
                    break;
                }
            }
    
            if (llibreEliminat) {
                Files.write(Paths.get(filePath), llibresArray.toString(4).getBytes());
                System.out.println("Llibre eliminat correctament.");
            } else {
                System.out.println("Error: No s'ha trobat cap llibre amb aquesta ID.");
            }
    
        } catch (IOException e) {
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al processar el JSON: " + e.getMessage());
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
    public static void eliminarUsuario(Scanner scanner){

        /*
         * Esta función es la que se usará para eliminar a los usuarios
         * @param scanner -> Recibe el valor del input del usuario
         * Lanza una exepción si el usuario no es conocido
         * Los usuarios se eliminan mediante el Id, una vez pulsada la Id, aparecerá el mensaje de... estas seguro que deseas eliminar al usuario ("xxxxxxxx") ? Si confirmamos que si se borrarà
         * Si la ID introducida no se conoce o no está creada lanzará un mensaje de error
         */
     
        String filePath = "JSON/usuaris.json";

        try {
        
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray listaUsuarios = new JSONArray(content);

        System.out.println("Introduzca la ID del usuario que desea eliminar: ");
        int deleteId = scanner.nextInt();
        scanner.nextLine();
        boolean usuarioEncontrado = false;

        for (int i = 0; i < listaUsuarios.length(); i++) {

            JSONObject usuario = listaUsuarios.getJSONObject(i);
            int id = usuario.getInt("id");

            if (id == deleteId ) {

                usuarioEncontrado = true;
                String deletedName = usuario.getString("nom");
                String deletedApellido = usuario.getString("cognom");
                System.out.println("Estas seguro que deseas eliminar al usuario: "+deletedName+" "+deletedApellido+" ? ['Yes' // 'No']");
                String confirmation = scanner.nextLine().toLowerCase();

                if (confirmation.equals("yes")){
                    listaUsuarios.remove(i);
                    System.out.println("Usuario eliminado");
                } else {
                    System.out.println("Usuario no eliminado");
                }
                break;
            }
        }

        if (!usuarioEncontrado){
            System.out.println("La ID solicitada no existe, por lo que NO se ha elimiando ningún usuario");
        }

        Files.write(Paths.get(filePath), listaUsuarios.toString(4).getBytes());//Aquí se guardan los cambios realizados

        } catch (Exception e){
            System.out.println("No se pudo eliminar al usuario: "+e.getMessage());
         }
    }

    public static void llistarUsuaris(Scanner scanner){
        /*
         * Función que lista los usuarios que ya estan en los archivos JSON
         * @param scanner -> Es el valor de selecciónd el usuario
         * @param maxNombre -> Se usa para calcular la alargada maxima de los nombres para cuadrar la columnas
         * @param maxApellido -> Lo mismo que maxNombre pero para el apellido
         */

         String filePath =  "JSON/usuaris.json"; 

         try{
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaUsuarios = new JSONArray(content);
            
            int maxNombre = 0;
            int maxApellido = 0;
            

            //Este "for" es para el encabezado
            for (int i = 0; i < listaUsuarios.length(); i++) {
                
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                String nombre = usuario.getString("nom");
                String apellido = usuario.getString("cognom");
                maxNombre = Math.max(maxNombre, nombre.length());//Esto compara cual de los 2 es mas largo
                maxApellido = Math.max(maxApellido, apellido.length());

            }

            System.out.println("Nom" + " ".repeat(maxNombre - 3) + " | Cognom"+" ".repeat(maxApellido - 6)+" | Telefon"); // El -3/-6...  es para el espacio que hay entre la barra y el apellido
            System.out.println("-".repeat(maxNombre) + "---" + "-".repeat(maxApellido) + "---" + "-".repeat(9));

            //Este "for" es para los nombres
            for (int i = 0; i < listaUsuarios.length(); i++) {
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                String nombre = usuario.getString("nom");
                String apellido = usuario.getString("cognom");
                int telefono = usuario.getInt("telefon");

                System.out.printf("%-" + maxNombre + "s | %-" + maxApellido + "s | %s%n", nombre, apellido, telefono);

            }
         
         } catch (Exception e){
            System.out.println("Error de compilación de usuarios...");
         }
         
         
    }
}

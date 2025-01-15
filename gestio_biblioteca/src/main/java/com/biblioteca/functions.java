package com.biblioteca;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class functions {
    //Funciones de estetica de menu
    public static void menu (){
        System.out.println("=====================================================================");
        System.out.println("Gestió de bilioteca");
        System.out.println("1. Llibres");
        System.err.println("2. Usuaris");
        System.out.println("3. Préstecs");
        System.out.println("0. Sortir");
    }
    
    public static void menuBooks (Scanner scanner){
        System.out.println("=====================================================================");
        System.out.println("Gestió de llibres");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar");
        System.out.println("0. Tornar el menú principal ['menu']");

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
                menuListBooks(scanner);
                break;

            } else if (optionBooks.equals("menu") || optionBooks.equals("0")){
                menu();
                break;
            }

        }
        
    }

    public static void menUsers(Scanner scanner){
        System.out.println("=====================================================================");
        System.out.println("Gestió d'usuaris");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar"); 
        System.out.println("0. Tornar el menú principal ['menu']");

        String optionUsers = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionUsers.equals("afegir") || optionUsers.equals("1")){
                afegirUsuaris(scanner);
                break;

            } else if (optionUsers.equals("modificar") || optionUsers.equals("2")){
                modificarUsuario(scanner);
                break;

            } else if (optionUsers.equals("eliminar") || optionUsers.equals("3")){
                eliminarUsuario(scanner);
                break;

            } else if (optionUsers.equals("llistar") || optionUsers.equals("4")){
                menUsersFilter(scanner);
                break;

            } else if (optionUsers.equals("menu") || optionUsers.equals("0")){
                menu();
                break;
            }

        }

    }

    public static void menUsersFilter (Scanner scanner){
        System.out.println("=====================================================================");
        System.out.println("Filtros de usuario");
        System.out.println("1. Todos");
        System.out.println("2. Prestamos Activos");
        System.out.println("3. Prestamos Finalizados"); 
        System.out.println("0. Tornar el menú usuaris ['menu']");

        String optionUsers = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionUsers.equals("todos") || optionUsers.equals("1")) {
                llistarUsuaris(scanner);
                break;
            } else if (optionUsers.equals("prestamos activos") || optionUsers.equals("2")){
                filterUserByPrestecsActius(scanner);
                break;
            } else if (optionUsers.equals("prestamos finalizados") || optionUsers.equals("3")){
                filterUsersByPrestecsFinalizados(scanner);
                break;
            } else if (optionUsers.equals("menu") || optionUsers.equals("0")){
                menUsers(scanner);
                break;
            }
        }
    }
    

    public static void menuPrestecs(Scanner scanner){
        System.out.println("=====================================================================");
        System.out.println("Gestió dels préstecs");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar"); 
        System.out.println("0. Tornar el menú principal ['menu']");

        String optionPrestecs = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionPrestecs.equals("afegir") || optionPrestecs.equals("1")){
                addPrestec(scanner);
                break;
            } else if (optionPrestecs.equals("modificar") || optionPrestecs.equals("2")){
                break;
            } else if (optionPrestecs.equals("eliminar") || optionPrestecs.equals("3")){
                deletePrestec(scanner);
                break;
            } else if (optionPrestecs.equals("llistar") || optionPrestecs.equals("4")){
                filterPrestecs(scanner);
                break;
            } else if (optionPrestecs.equals("menu") || optionPrestecs.equals("0")){
                menu();
                break;
            }

        }
    }

    public static void menuListBooks (Scanner scanner){
        System.out.println("=====================================================================");
        System.out.println("Llistar llibres");
        System.out.println("1. Tots");
        System.out.println("2. En préstec");
        System.out.println("3. Per autor");
        System.out.println("4. Cercar títol");
        System.out.println("0. Tornar al menú de llibres ['menu llibres']");

        String listBooksBy = scanner.nextLine().toLowerCase();
        while (true) {
            if (listBooksBy.equals("tots") || listBooksBy.equals("1")){
                filterBooks(scanner);
                break;
            } else if (listBooksBy.equals("en prestec") || listBooksBy.equals("2")){
                filterBooksPrestados(scanner);
                break;
            } else if (listBooksBy.equals("per autor") || listBooksBy.equals("3")){
                filterBooksByAutor(scanner);
                break;
            } else if (listBooksBy.equals("cercar titol") || listBooksBy.equals("4")){
                filterByWordsInTitle(scanner);
                break;
            } else if (listBooksBy.equals("menu llibres") || listBooksBy.equals("0")){
                menuBooks(scanner);
                break;
            }

        }
    }

    public static Integer automaticID(JSONArray array){
        /**
         * Funcion para añadir la ID automaticamente
         * @param array -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * @param ids -> es la lista en la que se almacena todas las ID del JSON
         * @param nextId -> es el contador que se compara con la lista y que se va sumando de 1 en 1 hasta que encuentra el numero que falte.
         * @return nextId -> esta variable devuelve la proxima ID más pequeña a utilizar en el JSON seleccionado.
         */
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject objeto = array.getJSONObject(i);
            ids.add(objeto.getInt("id"));
        }

        // Encontrar el menor ID disponible
        int nextId = 1;  
        while (ids.contains(nextId)) {
            nextId++;
        }

        return nextId;
    }

    //LIBROS
    public static void addBook (Scanner scanner){
        /**
         * Funcion que añade libros
         * @param llibresArray -> es el JsonArray con el que trabajamos,para obtener despues los valores que queramos.
         * Lanza una excepcion en el caso de que la lectura del JSON falle
         */
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            int id = automaticID(llibresArray);
            System.out.println("=====================================================================");
            System.out.println("L'ID d'aquest llibre és: " + id);

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

            //El autor puede ir vacio porque a veces este se desconoce
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
         * Lanza una excepcion en el caso de que la lectura del JSON falle
         */
        try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray llibresArray = new JSONArray(content);

            System.out.println("=====================================================================");
            System.out.println("Introdueix l'ID del llibre que vols modificar: ");
            int id = scanner.nextInt();
            //Sirve para limpiar el buffer
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
            
            System.out.println("=====================================================================");
            System.out.println("Introdueix l'ID del llibre que vols eliminar: ");
            int id = scanner.nextInt();
            //Sirve para limpiar el buffer
            scanner.nextLine();
    
            boolean llibreEliminat = false;
            for (int i = 0; i < llibresArray.length(); i++) {
                JSONObject llibre = llibresArray.getJSONObject(i);
                if (llibre.getInt("id") == id) {
                    String deletedTitol = llibre.getString("titol");
                    System.out.println("Estas segur de que vols eliminar el llibre: " +deletedTitol+ " (Yes/No)");
                    String confirmacion = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirmacion.equals("yes")){
                        llibresArray.remove(i); 
                        llibreEliminat = true;
                    }
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

    public static void filterBooks (Scanner scanner){
        /*
         * Esta función realiza un filtrado básico de TODOS LOS LIBROS
         * @param scanner: el input del usuario en el menú principal
         */

         try {
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaLibros = new JSONArray(content);

            int maxTitulo = 0;
            int maxAutor = 0;
            int maxId = 0;

            //Iniciamos 'for' para crear el encabezado --> se hace de la misma forma que para filtrar los usuarios
            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");
                int id = libro.getInt("id");
                maxTitulo = Math.max(maxTitulo, titol.length());
                maxAutor = Math.max(maxAutor, autor.length());
                String idText = String.valueOf(id); // Esto pasa el valor de id que es un int lo convierte en String para poder saber su largo
                maxId = Math.max(maxId, idText.length());
                
            }
            
            System.out.println("=====================================================================");
            System.out.println("Titol" + " ".repeat(maxTitulo - 4) + "| Autor" +" ".repeat(maxAutor - 4)+ "| Id Llibre");
            System.out.println("-".repeat(maxTitulo) + "---" + "-".repeat(maxAutor) + "---" + "-".repeat(9));

            //Iniciamos bucle 'for' para los listar los nombres de los titulos

            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");
                int id = libro.getInt("id");

                System.out.printf("%-" + maxTitulo + "s | %-" + maxAutor + "s | %" + maxId + "d\n", titol, autor, id);
            }

         } catch (Exception e) {
            System.out.println("Error al filtra libros: "+e.getMessage());
         }
    }

    public static void filterBooksPrestados (Scanner scanner){
        /*
         * Esta función muestra los libros los cuales estan prestados
         * Se mostrarà la titulo, autor, la id del libro y la id del prestamo
         */

         try {
            String filePath = "./JSON/llibres.json";
            String filePathPrestecs = "./JSON/prestecs.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String contentPrestecs = new String(Files.readAllBytes(Paths.get(filePathPrestecs)));
            JSONArray listaLibros = new JSONArray(content);
            JSONArray listaPrestecs = new JSONArray(contentPrestecs);

            boolean librosFinded = false; 
            int maxTitol = 0;
            int maxAutor = 0;
            int maxIdLibro = 0;
            int maxIdPrestamo = 0;

            //Encabezado
            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                int libroId = libro.getInt("id");
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");
                String idText = String.valueOf(libroId);

                maxTitol = Math.max(maxTitol, titol.length());
                maxAutor = Math.max(maxAutor, autor.length());
                maxIdLibro = Math.max(maxIdLibro, idText.length());
            }

            for (int j = 0; j < listaPrestecs.length(); j++) {

                JSONObject prestamo = listaPrestecs.getJSONObject(j);
                String idPrestecText = String.valueOf(prestamo.getInt("id"));
                maxIdPrestamo = Math.max(maxIdPrestamo, idPrestecText.length());
            }

            System.out.println("=====================================================================");
            System.out.println("TITOL" + " ".repeat(Math.max(0, maxTitol - 5)) + " | " + "AUTOR" + " ".repeat(Math.max(0, maxAutor - 5)) + " | " + "ID LLIBRE" + " ".repeat(Math.max(0, maxIdLibro - 8)) + " | " + "ID PRESTEC");
            System.out.println("-".repeat(maxTitol + maxAutor + maxIdLibro + maxIdPrestamo + 29));

            //Contenido de las columnas
            for (int i = 0; i < listaLibros.length(); i++) {
                JSONObject libro = listaLibros.getJSONObject(i);
                int libroId = libro.getInt("id");
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");
                String idText = String.valueOf(libroId);

                maxTitol = Math.max(maxTitol, titol.length());
                maxAutor = Math.max(maxAutor, autor.length());
                maxIdLibro = Math.max(maxIdLibro, idText.length());

                for (int j = 0; j < listaPrestecs.length(); j++){

                    JSONObject prestamo = listaPrestecs.getJSONObject(j);
                    int idPrestec = prestamo.getInt("id");
                    int prestamoIdLibro = prestamo.getInt("idLlibre");

                    if (libroId == prestamoIdLibro) {
                        librosFinded = true;
                        System.out.println(titol + " ".repeat(maxTitol - titol.length()) + " | " + autor + " ".repeat(maxAutor - autor.length()) + " | " + libroId + " ".repeat(8) + " | " + idPrestec);
                    }
                }

            }

            if (!librosFinded) {
                System.out.println("No se han encontrado libros en valor de 'prestado'");
            }
         } catch (Exception e) {
            System.out.println("Erros al filtrar libros por prestamos: "+ e.getMessage());
         }
    }

    public static void filterBooksByAutor (Scanner scanner){
        /*
         * Esta función es la que permite el filtrado de todos los libros por autor y despues listarlos
         * Se mostrará En formato
         * Autor: "xxxxxx"
         * ==============
         * Titulo 1
         * Titulo 2
         * ....
         */

        try {

            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaLibros = new JSONArray(content);

            boolean librosFinded = false;
    
            System.out.println("Seleccione una Autor para filtrar sus libros: [DEBE INTRODUCIR EL AUTOR EN FORMATO 'Camel']");
            String filterAutor = scanner.nextLine();

            //Encabezado
            System.out.println("=====================================================================");
            System.out.println("Autor/a: " + filterAutor);
            System.out.println("-".repeat(filterAutor.length()+8));

            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                String autor = libro.getString("autor");

                if (autor.equals(filterAutor)) {
                    System.out.println(libro.getString("titol")+" || ID del llibre: " + libro.getInt("id"));
                    librosFinded = true;
                }
            }

            if(!librosFinded){
                System.out.println("No se han encontrado ningún libro del Autor: "+filterAutor);
            }

        } catch (Exception e) {
            System.out.println("Error al filtrar libros por autor: "+e.getMessage());
        }
    }

    public static void filterByWordsInTitle (Scanner scanner){
        /*
         * Si lo entendido bien se tiene que poder filtrar libros por palabras en el título.
         * Haremos que el titulo que recojemos lo pasamos a minúscula así como la palabra que recojemos
         * Se mostrará en formato similar a 'filterBooksByAutor'
         */

         try {
            
            String filePath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaLibros = new JSONArray(content);

            boolean librosFinded = false;

            System.out.println("Seleccione una palabra para filtrar: ");
            String filterWord = scanner.nextLine().toLowerCase();

            //Encabezado
            System.out.println("=".repeat(filterWord.length()+11)); //11 es el ancho total del String + filter word + las 2 "" al inicio y al final
            System.out.println("Palabra: " +"\""+filterWord+"\"");
            System.out.println("-".repeat(filterWord.length()+11));

            for (int i = 0; i < listaLibros.length(); i++) {
                JSONObject libro = listaLibros.getJSONObject(i);
                String titulo = libro.getString("titol").toLowerCase();

                if(titulo.contains(filterWord)){
                    System.out.println(titulo.toUpperCase() + "|| ID del llibre: "+libro.getInt("id"));
                    librosFinded = true;
                }
            }

            if (!librosFinded){
                System.out.println("No se han encontrado libros que contengan la palabra: " + filterWord);
            }
         } catch (Exception e) {
            System.out.println("Error al filtras por palabra en el título: " + e.getMessage());
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

            System.out.println("=====================================================================");
            System.out.println("Introdueix el nom: ");
            String nom = scanner.nextLine();

            System.out.println("Introdueix el cognom: ");
            String cognom = scanner.nextLine();

            System.out.println("Introdueix el numero de telefon: ");
            int telefon = scanner.nextInt();

            comprobarLlongitud(telefon); // LLamamos a las funciones anteriores para comprobar que sea correcto
            comprobarTelefon(telefon, jsonArray);

            int id = automaticID(jsonArray); // Utilizamos la funcion automaticID() para generar una ID
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
        boolean usuarioEncontrado = false;//Esta variabl es la que utilizamos para verificar en caso de error si el usuario no se ha encontrado

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

    public static void modificarUsuario(Scanner scanner){
        /*
         * Esta función permite modificar los parámetros de un usuario teniendo en cuenta varias cosas
         * --> 1. La ID del usuario no podrá ser modificada ya que es única para cada usuario, por lo que si se intenta modificar, saldrá un mensaje de ERROR
         * --> 2. El numero de telefono para cada usuario es único, por lo que si se intenta modificar el numero de telefono de un usuario y coincide con el de otro usuario se mostrará un mensaje de ERROR
         * @param scanner recibimos el input del usuario de la aplicación         
         */

         String  filePath = "JSON/usuaris.json";

         try{

            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaUsuarios = new JSONArray(content);

            System.out.println("Introduzca la ID del usuario que desea modificar: ");
            int changeId = scanner.nextInt();
            scanner.nextLine();
            boolean usuarioFinded = false;

            for (int i = 0; i < listaUsuarios.length(); i++) {
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                int id = usuario.getInt("id");
                

                if (id == changeId){

                    usuarioFinded = true;
                    String changeNombre = usuario.getString("nom");
                    String changeApellido = usuario.getString("cognom");
                    System.out.println("Estas seguro que deseas modificar al usuario: "+changeNombre+" "+changeApellido+" ? ['Yes' // 'No']");
                    String confirmationModified = scanner.nextLine().toLowerCase();

                    if (confirmationModified.equals("yes")){
                        System.out.println("Que quieres modificar ? 1. Nombre // 2. Apellido // 3. Telefono");
                        int aModificar = scanner.nextInt();
                        scanner.nextLine();

                        if (aModificar == 1){
                            System.out.println("Escribe un nuevo nombre: ");
                            String newName = scanner.nextLine();
                            usuario.put("nom", newName);

                        } else if (aModificar == 2){
                            System.out.println("Escribe un nuevo apellido: ");
                            String newApellido = scanner.nextLine();
                            usuario.put("cognom", newApellido);

                        } else if (aModificar == 3){
                            System.out.println("Escribe un nuevo numero de telefono: ");
                            int newNumber = scanner.nextInt();
                            scanner.nextLine();

                            boolean telefonoDuplicado = false;
                            for (int j = 0; j < listaUsuarios.length(); j++){
                                if (j != i && listaUsuarios.getJSONObject(j).getInt("telefon") == newNumber){
                                    telefonoDuplicado = true;
                                    break;
                                }
                            }

                            if (telefonoDuplicado){
                                System.out.println("El numero de telefono ya está asociado a otra persona");
                            } else {
                                usuario.put("telefon", newNumber);
                            }

                        } else {
                            System.out.println("No se ha realizado ninguna modificación");
                        }
                        break;
                    }

                }
            }

            if (!usuarioFinded){
                System.out.println("La ID solicitada no existe, por lo que NO se ha encontrado a  ningún usuario válido");
            }
    
            Files.write(Paths.get(filePath), listaUsuarios.toString(4).getBytes());

         } catch (Exception e){
            System.out.println("No se pudo modificar el usuario: "+e.getMessage());
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
            int maxId = 0;
            
            //Este "for" es para el encabezado
            for (int i = 0; i < listaUsuarios.length(); i++) {
                
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                String nombre = usuario.getString("nom");
                String apellido = usuario.getString("cognom");
                maxNombre = Math.max(maxNombre, nombre.length());//Esto compara cual de los 2 es mas largo
                maxApellido = Math.max(maxApellido, apellido.length());
                int id = usuario.getInt("id");
                String idText = String.valueOf(id);
                maxId = Math.max(maxId, idText.length());

            }

            System.out.println("=====================================================================");
            System.out.println("Nom" + " ".repeat(maxNombre - 3) + " | Cognom"+" ".repeat(maxApellido - 5)+" | Telefon"  + "  | Id "); // El -3/-6...  es para el espacio que hay entre la barra y el apellido
            System.out.println("-".repeat(maxNombre) + "---" + "-".repeat(maxApellido) + "---" + "-".repeat(9) + "---"+"-".repeat(maxId + 2));

            //Este "for" es para los nombres
            for (int i = 0; i < listaUsuarios.length(); i++) {
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                String nombre = usuario.getString("nom");
                String apellido = usuario.getString("cognom");
                int telefono = usuario.getInt("telefon");
                int id = usuario.getInt("id");

                System.out.printf("%-" + maxNombre + "s | %-" + maxApellido + "s | %-9d | %" + maxId + "d\n", nombre, apellido, telefono, id);

            }
         
         } catch (Exception e){
            System.out.println("Error de compilación de usuarios..."+ e.getMessage());
         }
    }

    public static void filterUserByPrestecsActius(Scanner scanner) {

        /*
         * Esta función muestra todos los usuarios con préstamos activos.
         * Mostraremos el ID del usuario, el ID del préstamo, el nombre del usuario (claves "nom" y "cognom")
         * y el teléfono del usuario.
         * Se mostraran aquellos usuarios con dataDevolucio = Null; 
         */
    
        try {

            String filePath = "./JSON/usuaris.json";
            String filePathPrestecs = "./JSON/prestecs.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String contentPrestecs = new String(Files.readAllBytes(Paths.get(filePathPrestecs)));
            JSONArray listaUsuaris = new JSONArray(content);
            JSONArray listaPrestecs = new JSONArray(contentPrestecs);
    
            boolean usuariosFinded = false;
            int maxNom = 0;
            int maxCognom = 0;
            int maxTelefon = 0;
    
            //ENCABEZADO
            for (int i = 0; i < listaUsuaris.length(); i++) {
                JSONObject usuari = listaUsuaris.getJSONObject(i);
                maxNom = Math.max(maxNom, usuari.getString("nom").length());
                maxCognom = Math.max(maxCognom, usuari.getString("cognom").length());
                maxTelefon = Math.max(maxTelefon, String.valueOf(usuari.getInt("telefon")).length());
            }
    

            System.out.println("=====================================================================");
            System.out.println("ID USUARI" + " | " + "ID PRESTEC" + " | " + "NOM" + " ".repeat(maxNom - 3) + " | " + "COGNOM" + " ".repeat(maxCognom - 6) + " | " + "TELEFON");
            System.out.println("-".repeat(maxNom + maxCognom + maxTelefon + 41));
    
            //Contenido de las columnas
            for (int i = 0; i < listaPrestecs.length(); i++) {
                JSONObject prestamo = listaPrestecs.getJSONObject(i);
                if (prestamo.isNull("dataDevolucio")) { 
                    int idUsuariPrestamo = prestamo.getInt("idUsuari");
                    int idPrestec = prestamo.getInt("id");
            
                    for (int j = 0; j < listaUsuaris.length(); j++) {
                        JSONObject usuari = listaUsuaris.getJSONObject(j);
                        if (usuari.getInt("id") == idUsuariPrestamo) {

                            usuariosFinded = true;
                            int idUsuari = usuari.getInt("id");
                            int telefon = usuari.getInt("telefon");
                            String nom = usuari.getString("nom");
                            String cognom = usuari.getString("cognom");

                            System.out.println(idUsuari + " ".repeat(9 - String.valueOf(idUsuari).length()) + " | " + idPrestec + " ".repeat(10 - String.valueOf(idPrestec).length()) + " | " + nom + " ".repeat(maxNom - nom.length()) + " | " + cognom + " ".repeat(maxCognom - cognom.length()) + " | " + telefon);
                        }
                    }
                }
            }
            
            if (!usuariosFinded) {
                System.out.println("No se han encontrado usuarios con préstamos activos.");
            }
    
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public static void filterUsersByPrestecsFinalizados(Scanner scanner) {
        /*
         * Esta función muestra todos los usuarios con préstamos finalizados.
         * Mostramos el ID del usuario, el ID del préstamo, el nombre del usuario (claves "nom" y "cognom"), el teléfono, y las fechas de inicio y finalización.
         */
    
        try {
            String filePath = "./JSON/usuaris.json";
            String filePathPrestecs = "./JSON/prestecs.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String contentPrestecs = new String(Files.readAllBytes(Paths.get(filePathPrestecs)));
            JSONArray listaUsuaris = new JSONArray(content);
            JSONArray listaPrestecs = new JSONArray(contentPrestecs);
    
            boolean usuariosFinded = false;
            int maxNom = 0;
            int maxCognom = 0;
            int maxTelefon = 0;
    
            //Encabezado
            for (int i = 0; i < listaUsuaris.length(); i++) {
                JSONObject usuari = listaUsuaris.getJSONObject(i);
                maxNom = Math.max(maxNom, usuari.getString("nom").length());
                maxCognom = Math.max(maxCognom, usuari.getString("cognom").length());
                maxTelefon = Math.max(maxTelefon, String.valueOf(usuari.getInt("telefon")).length());
            }
    
            
            System.out.println("=====================================================================");
            //Para el encabezado la unica forma que me dejaba era asi, es el ancho de la columna fijo - la longitud del nombre, apellido, fecha...
            System.out.println("ID USUARI" + " ".repeat(9 - "ID USUARI".length()) + " | " + "ID PRESTEC" + " ".repeat(10 - "ID PRESTEC".length()) + " | " + "NOM" + " ".repeat(maxNom - "NOM".length()) + " | " + "COGNOM" + " ".repeat(maxCognom - "COGNOM".length()) + " | " +
                                "TELEFON" + " ".repeat(9 - "TELEFON".length()) + " | " + "DATA INICI" + " ".repeat(10 - "DATA INICI".length()) + " | " + "DATA FINAL");
            System.out.println("-".repeat(maxNom + maxCognom + maxTelefon + 57));
    
            //COlumnas
            for (int i = 0; i < listaPrestecs.length(); i++) {
                JSONObject prestamo = listaPrestecs.getJSONObject(i);

                if (!prestamo.isNull("dataDevolucio")) { 

                    int idUsuariPrestamo = prestamo.getInt("idUsuari");
                    int idPrestec = prestamo.getInt("id");
                    String dataInici = prestamo.getString("dataPrestec");
                    String dataFinal = prestamo.getString("dataDevolucio");
    
                    for (int j = 0; j < listaUsuaris.length(); j++) {
                        JSONObject usuari = listaUsuaris.getJSONObject(j);

                        if (usuari.getInt("id") == idUsuariPrestamo) {
                            usuariosFinded = true;
    
                            int idUsuari = usuari.getInt("id");
                            int telefon = usuari.getInt("telefon");
                            String nom = usuari.getString("nom");
                            String cognom = usuari.getString("cognom");
    
                            System.out.println(idUsuari + " ".repeat(9 - String.valueOf(idUsuari).length()) + " | " + idPrestec + " ".repeat(10 - String.valueOf(idPrestec).length()) + " | " + nom + " ".repeat(maxNom - nom.length()) + " | " +
                                cognom + " ".repeat(maxCognom - cognom.length()) + " | " + telefon + " ".repeat(maxTelefon - String.valueOf(telefon).length()) + " | " + dataInici + " | " + dataFinal
                            );
                        }
                    }
                }
            }
    
            if (!usuariosFinded) {
                System.out.println("No se han encontrado usuarios con préstamos finalizados.");
            }
    
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
    
    public static boolean verificarId(String filePath, int id) throws IOException {
        //Sirve para verificar si la id se encuentra en el archivo json.
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray jsonArray = new JSONArray(content);
    
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getInt("id") == id) {
                return true; 
            }
        }
        return false; 
    }
     
    private static int contarPrestecsUsuario(JSONArray prestecsArray, int idUsuari) {
        // Cuenta cuántos libros tiene prestados un usuario
        int count = 0;
        for (int i = 0; i < prestecsArray.length(); i++) {
            JSONObject prestec = prestecsArray.getJSONObject(i);
            if (prestec.getInt("idUsuari") == idUsuari) {
                count++;
            }
        }
        return count;
    }

    private static boolean estaLlibrePrestado(JSONArray prestecsArray, int idLlibre) {
        // Verifica si un libro ya está prestado
        for (int i = 0; i < prestecsArray.length(); i++) {
            JSONObject prestec = prestecsArray.getJSONObject(i);
            if (prestec.getInt("idLlibre") == idLlibre) {
                return true;
            }
        }
        return false;
    }

    //PRESTECS
    public static void addPrestec(Scanner scanner){
        /*
        * Función que permite añadir un nuevo préstamo de un libro a un usuario.
        *
        * 1.Verifica si la ID del usuario existe en el archivo JSON de usuarios con la funcion verificarId().
        * 2.Comprueba cuántos libros tiene prestados el usuario. Si ya tiene 4, no se permite el nuevo préstamo. Lo hace con la funcion contarPrestecsUsuario().
        * 3.Verifica si la ID del libro existe en el archivo JSON de libros con la funcion verificarId().
        * 4.Comprueba si el libro ya está prestado. Un libro no puede ser prestado más de una vez. Lo hace con la funcion estaLlibrePrestado().
        */

        try {
            String prestecsPath = "./JSON/prestecs.json";
            String usuarisPath = "./JSON/usuaris.json";
            String llibresPath = "./JSON/llibres.json";
            String content = new String(Files.readAllBytes(Paths.get(prestecsPath)));

            JSONArray prestecsArray = new JSONArray(content);

            System.out.println("=====================================================================");
            System.out.println("Introdueix la teva ID(la del usuari): ");
            int idUsuari = scanner.nextInt();

            if (!verificarId(usuarisPath, idUsuari)) {
                System.out.println("Error: La ID del usuari no existeix.");
                return;
            }

            int contUsuari = contarPrestecsUsuario(prestecsArray, idUsuari);
            if (contUsuari >= 4){
                System.out.println("Error: Ja tens 4 llibres en préstec. No pots sobrepasar el límit.");
                return;
            }

            System.out.println("Introdueix l'ID del llibre que vols: ");
            int idLlibre = scanner.nextInt();
            //Sirve para limpiar el buffer
            scanner.nextLine();

            if (!verificarId(llibresPath, idLlibre)) {
                System.out.println("Error: La ID del llibre no existeix.");
                return;
            }

            if (estaLlibrePrestado(prestecsArray, idLlibre)) {
                System.out.println("Error: Aquest llibre ja es troba en préstec.");
                return;
            }

            System.out.println("Introdueix la data del préstec (format: yyyy-MM-dd): ");
            String dataPrestecStr = scanner.nextLine();

            //Esta parte sirve para convertir el String(dataPrestecStr) en un objeto
            LocalDate dataPrestec;
            try {
                dataPrestec = LocalDate.parse(dataPrestecStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Error: La data introduïda no té el format correcte.");
                return;
            }
            LocalDate dataDevolucio = dataPrestec.plusDays(7); 

            int id = automaticID(prestecsArray);

            JSONObject nouPrestec = new JSONObject();
            nouPrestec.put("id", id);
            nouPrestec.put("idLlibre", idLlibre);
            nouPrestec.put("idUsuari", idUsuari);
            nouPrestec.put("dataPrestec", dataPrestec);
            nouPrestec.put("dataDevolucio", dataDevolucio.toString());

            prestecsArray.put(nouPrestec);

            Files.write(Paths.get(prestecsPath), prestecsArray.toString(4).getBytes());

            System.out.println("Préstec afegit correctament. Tens 7 dies per retornar el llibre.");

        } catch (IOException e) {
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al processar el JSON: " + e.getMessage());
        }
    }

    public static void deletePrestec(Scanner scanner) {
        /*
        * Función que elimina un préstamo existente del archivo JSON.
        */
        try {
            String prestecsPath = "./JSON/prestecs.json";
            String content = new String(Files.readAllBytes(Paths.get(prestecsPath)));

            JSONArray prestecsArray = new JSONArray(content);

            System.out.println("=====================================================================");
            System.out.println("Introdueix l'ID del préstec que vols eliminar: ");
            int idPrestec = scanner.nextInt();

            boolean prestecEncontrado = false;
            for (int i = 0; i < prestecsArray.length(); i++) {
                JSONObject prestec = prestecsArray.getJSONObject(i);
                if (prestec.getInt("id") == idPrestec) {
                    prestecsArray.remove(i);
                    prestecEncontrado = true;
                    break;
                }
            }

            if (prestecEncontrado) {
                Files.write(Paths.get(prestecsPath), prestecsArray.toString(4).getBytes());
                System.out.println("Préstec eliminat correctament.");
            } else {
                System.out.println("Error: No s'ha trobat cap préstec amb aquesta ID.");
            }

        } catch (IOException e) {
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al processar el JSON: " + e.getMessage());
        }
    }


    public static void filterPrestecs (Scanner scanner){
        /*
         * Función que se utiliza para filtrar todos los prestámos
         * Información que se verá en el filtrado
         * 1 --> Id del usuario al que se le realiza el prestamos
         * 2 --> Id del libro prestado 
         * 3 --> Fecha en la que se realiza el prestámo
         * @param scanner: el input del usuario en el menú
         */

        String filePath = "JSON/prestecs.json";

        try {
            
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray listaPrestecs = new JSONArray(content);

            int maxId = 2;
            int maxDataPrestec = 10;
            int maxDataDevolucio = 10;

            //Este for es para el formato del encabezado
            for (int i = 0; i < listaPrestecs.length(); i++) {
                
                JSONObject prestec = listaPrestecs.getJSONObject(i);
                String dataPrestec = prestec.getString("dataPrestec");
                String dataDevolucio = "null";

                if (!prestec.isNull("dataDevolucio")){
                    dataDevolucio = prestec.getString("dataDevolucio");
                }

                maxDataPrestec = Math.max(maxDataPrestec, dataPrestec.length());
                maxDataDevolucio = Math.max(maxDataDevolucio, dataDevolucio.length());
            }

            System.out.println("=====================================================================");
            System.out.println("Id" + " ".repeat(maxId - 2) + "| Data Prestec" + " ".repeat(maxDataPrestec - 10) + "| Data Devolucio"); //Encabezado
            System.out.println("-".repeat(maxId) + "---" +"-".repeat(maxDataPrestec) + "---" +"-".repeat(maxDataDevolucio)); //Separadores

            //Este for es para el contenido del filtrado
            for (int i = 0; i < listaPrestecs.length(); i++) {

                JSONObject prestec = listaPrestecs.getJSONObject(i);
                int id = prestec.getInt("id");
                String dataPrestec = prestec.getString("dataPrestec");
                String dataDevolucio = "null";

                if (!prestec.isNull("dataDevolucio")) {
                    dataDevolucio = prestec.getString("dataDevolucio");
                }
    
                System.out.printf("%-" + maxId + "d | %s | %-" + maxDataDevolucio + "s\n", id, dataPrestec, dataDevolucio);
            }

        } catch (Exception e) {
            System.out.println("Error al filtrar los prestamos: "+ e.getMessage());
        }
    }
}

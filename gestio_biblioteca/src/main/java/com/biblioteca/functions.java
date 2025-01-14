package com.biblioteca;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("0. Tornar el menú principal");

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
                llistarUsuaris(scanner);
                break;

            } else if (optionUsers.equals("menu") || optionUsers.equals("0")){
                menu();
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
        System.out.println("0. Tornar el menú principal");

        String optionPrestecs = scanner.nextLine().toLowerCase();
        while (true) {
            if (optionPrestecs.equals("afegir") || optionPrestecs.equals("1")){
                addPrestec(scanner);
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
        System.out.println("=====================================================================");
        System.out.println("Llistar llibres");
        System.out.println("1. Tots");
        System.out.println("2. En préstec");
        System.out.println("3. Per autor");
        System.out.println("4. Cercar títol");
        System.out.println("0. Tornar al menú de llibres");

        String listBooksBy = scanner.nextLine().toLowerCase();
        while (true) {
            if (listBooksBy.equals("tots") || listBooksBy.equals("1")){
                filterBooks(scanner);
                break;
            } else if (listBooksBy.equals("en prestec") || listBooksBy.equals("2")){
                break;
            } else if (listBooksBy.equals("per autor") || listBooksBy.equals("3")){
                break;
            } else if (listBooksBy.equals("cercar titol") || listBooksBy.equals("4")){
                break;
            } else if (listBooksBy.equals("tornar al menu de llibres") || listBooksBy.equals("0")){
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
         * @param nextId -> es el contador que se compara con la lista y que va sumando de 1 en 1 hasta que encuentra el numero que falte.
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

            //Iniciamos 'for' para crear el encabezado --> se hace de la misma forma que para filtrar los usuarios
            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");
                maxTitulo = Math.max(maxTitulo, titol.length());
                maxAutor = Math.max(maxAutor, autor.length());
                
            }
            
            System.out.println("=====================================================================");
            System.out.println("Titol" + " ".repeat(maxTitulo - 4) + "| Autor");
            System.out.println("-".repeat(maxTitulo) + "---" + "-".repeat(maxAutor) + "---" + "-".repeat(9));

            //Iniciamos bucle 'for' para los listar los nombres de los titulos

            for (int i = 0; i < listaLibros.length(); i++) {

                JSONObject libro = listaLibros.getJSONObject(i);
                String titol = libro.getString("titol");
                String autor = libro.getString("autor");

                System.out.printf("%-" + maxTitulo + "s | %-" + maxAutor+"s\n", titol, autor);
            }

         } catch (Exception e) {
            System.out.println("Error al filtra libros: "+e.getMessage());
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

            int id = automaticID(jsonArray);
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
            

            //Este "for" es para el encabezado
            for (int i = 0; i < listaUsuarios.length(); i++) {
                
                JSONObject usuario = listaUsuarios.getJSONObject(i);
                String nombre = usuario.getString("nom");
                String apellido = usuario.getString("cognom");
                maxNombre = Math.max(maxNombre, nombre.length());//Esto compara cual de los 2 es mas largo
                maxApellido = Math.max(maxApellido, apellido.length());

            }

            System.out.println("=====================================================================");
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
            System.out.println("Error de compilación de usuarios..."+ e.getMessage());
         }
    }

    //PRESTECS
    public static void addPrestec(Scanner scanner){
        /*
         * Hay que hacer ajustes a esta funcion.
         * 
         */
        try {
            String filePath = "./JSON/prestecs.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray prestecsArray = new JSONArray(content);

            System.out.println("=====================================================================");
            int id = automaticID(prestecsArray);
            System.out.println("L'ID d'aquest préstec és: " + id);
            
            System.out.println("Introdueix l'ID del llibre que vols: ");
            int idLlibre = scanner.nextInt();

            System.out.println("Introdueix la teva ID(la del usuari): ");
            int idUsuari = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Introdueix la data del préstec (format: yyyy-MM-dd): ");
            String dataPrestecStr = scanner.nextLine();

            LocalDate dataPrestec = LocalDate.parse(dataPrestecStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate dataDevolucio = dataPrestec.plusDays(7); 

            JSONObject nouPrestec = new JSONObject();
            nouPrestec.put("id", id);
            nouPrestec.put("idLlibre", idLlibre);
            nouPrestec.put("idUsuari", idUsuari);
            nouPrestec.put("dataPrestec", dataPrestec);
            nouPrestec.put("dataDevolucio", dataDevolucio.toString());

            prestecsArray.put(nouPrestec);

            Files.write(Paths.get(filePath), prestecsArray.toString(4).getBytes());

            System.out.println("Préstec afegit correctament.");

        } catch (IOException e) {
            System.out.println("Error al llegir/escriure l'arxiu: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al processar el JSON: " + e.getMessage());
        }
    }
}

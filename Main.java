//Cuando se pone para volver atrás, es simplemente llamar a la función "menu()"
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //HACER EL BUCLE PRINCIPAL EN UNA FUNCION ? 
        while (true) {
            functions.menu();
            System.out.println("Escull una opció");
            String option = scanner.nextLine().toLowerCase();

            if (option.equals("llibres") || option.equals("1")){
                //En menubooks llamar funciones de modificar... así para todos los métodos
                functions.menuBooks(scanner);
            } else if (option.equals("usuaris") || option.equals("2")){
                functions.menUsers(scanner);
            } else if (option.equals("préstecs") || option.equals("3")){
                functions.menuPrestecs(scanner);
            } else if (option.equals("sortir") || option.equals("0")){
                System.out.println("Sortint del programa...");
                break;
            }
        }

        scanner.close();
    }
}

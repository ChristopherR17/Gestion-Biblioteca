
import java.util.Scanner;

public class Main {

    public static void menu (){
        System.err.println("Gestió de bilioteca");
        System.out.println("1. Llibres");
        System.err.println("2. Usuaris");
        System.out.println("3. Préstecs");
        System.out.println("0. Sortir");
    }
    
    public static void menuBooks (){
        System.out.println("Gestió de llibres");
        System.out.println("1. Afegir");
        System.out.println("2. Modificar");
        System.out.println("3. Eliminar");
        System.out.println("4. Llistar");
        System.out.println("0. Tornar el menú principal");
    }

    public static void menuListBooks (){
        System.out.println("Llistar llibres");
        System.out.println("1. Tots");
        System.out.println("2. En préstec");
        System.out.println("3. Per autor");
        System.out.println("4. Cercar títol");
        System.out.println("Tornar al menú de llibres");
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            //Hacer el menu en una función
            menu();
            System.out.println("Escull una opció");
            String option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "llibres":
                    int optionBooks = scanner.nextInt();
                    break;
                default:
                    break;
            }

        }

        scanner.close();
    }
}

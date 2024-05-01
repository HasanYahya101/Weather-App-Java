import java.util.Scanner;

import config.API_Key;
import ui_layer.terminal.terminal;

public class App {
    public static void main(String[] args) throws Exception {
        String ui_type;
        String db_type;
        // get values from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter UI type (terminal, gui): ");
        ui_type = scanner.nextLine();
        while (!ui_type.equals("terminal") && !ui_type.equals("gui")) {
            System.out.println("Invalid UI type. Enter UI type (terminal, gui): ");
            ui_type = scanner.nextLine();
        }
        System.out.println("Enter DB type (txt, sql): ");
        db_type = scanner.nextLine();
        while (!db_type.equals("txt") && !db_type.equals("sql")) {
            System.out.println("Invalid DB type. Enter DB type (txt, sql): ");
            db_type = scanner.nextLine();
        }

        if (ui_type.equals("terminal")) {
            terminal terminal = new terminal();
            terminal.run(db_type);
        } else {
            System.out.println("GUI not implemented yet.");
        }

    }
}

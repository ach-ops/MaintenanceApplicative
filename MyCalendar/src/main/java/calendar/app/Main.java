package calendar.app;
import calendar.action.acces.Connexion;
import calendar.action.menu.Menu;
import calendar.objet.Utilisateur;

import java.util.Scanner;

public class Main {

    public static final Scanner scanner = new Scanner(System.in);
    public static final CalendarManager calendar = new CalendarManager();

    public static void main(String[] args) {
        // Logo
        System.out.println("  _____         _                   _                __  __");
        System.out.println(" / ____|       | |                 | |              |  \\/  |");
        System.out.println("| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __");
        System.out.println("| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\");
        System.out.println("| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | |");
        System.out.println(" \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_|");

        while (true) {
            Utilisateur utilisateur = null;

            while ((utilisateur = Connexion.run(scanner)) == null);


            while (Menu.run(scanner, calendar, utilisateur));
        }
    }
}

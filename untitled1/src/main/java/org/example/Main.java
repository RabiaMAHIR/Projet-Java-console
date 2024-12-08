package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLOutput;
import java.util.Scanner;
import static org.example.QRCodeApp.*;
import java.awt.Desktop;
import com.github.lalyos.jfiglet.FigletFont;

public class Main {
    public static void main(String[] args) {

        createDatabase();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        try {

            String text = "--CODE QR--";
            String asciiArt = FigletFont.convertOneLine(text);


            System.out.println(asciiArt);
        } catch (Exception e) {
            System.err.println("ERREUR ASCII Art: " + e.getMessage());
        }
        while (running) {

            System.out.println("\033[0;32m------------MENU------------\033[0m");
            System.out.println("\033[0;35m[1]\033[0m Générer un code QR");
            System.out.println("\033[0;35m[2]\033[0m Supprimer le code QR");
            System.out.println("\033[0;35m[3]\033[0m Afficher tous les codes QR");
            System.out.println("\033[0;35m[4]\033[0m Sortie");
            System.out.println("\033[0;32m----------------------------\033[0m");


            System.out.print("Entrez votre choix: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    System.out.print("Entrez le texte du code QR:");
                    String textToGenerate = scanner.nextLine();
                    if (!textToGenerate.isEmpty()) {
                        try {
                            System.out.println("Génération de code QR pour: " + textToGenerate);


                            byte[] qrImage = generateQRCode(textToGenerate, 200, 200);


                            saveQRCodeToDatabase(textToGenerate, qrImage);


                            String fileName = "QRCode_" + textToGenerate.replace(" ", "_") + ".png";
                            File directory = new File(System.getProperty("user.dir") + File.separator + "qr_codes");


                            if (!directory.exists()) {
                                directory.mkdir();
                            }

                            File file = new File(directory + File.separator + fileName);

                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                fos.write(qrImage);
                            }
                            System.out.println("\033[0;34m Code enregistré à: \033[0m \033[0;33m" + file.getAbsolutePath()+"\033[0m");
                            System.out.println("\033[0;34m Code QR généré avec succès!\033[0m");

                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(file);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Veuillez saisir un texte valide !");
                    }
                    break;

                case 2:
                    System.out.print("Entrez le texte du QR Code à supprimer : ");
                    String textToDelete = scanner.nextLine();
                    if (!textToDelete.isEmpty()) {
                        try {
                            deleteQRCodeFromDatabase(textToDelete);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Veuillez saisir un texte valide pour supprimer!");
                    }
                    break;

                case 3:
                    getAllQRCodes();
                    break;

                case 4:
                    running = false;
                    System.out.println("Sortie...");
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez sélectionner une option valide.");
            }
        }

        scanner.close();
    }
}

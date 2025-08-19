package com.UI;

import com.controllers.URLController;

import java.util.Scanner;

public class URL_UI {
    public static void main(String[] args) {
        URLController controller = new URLController();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to URL-Shortner" +
                            "\nPlease Enter Your Choice" +
                    "\n 1 Shorten Your Url" +
                    "\n 2 Find Long Url" +
                    "\n 3 Deactivate ShortUrl" +
                    "\n 4 For EXIT URL-Shortner");

            int option  = sc.nextInt();

            switch (option) {

                case 1:
                    controller.createShortURL(sc);
                    break;
                case 2:
                    controller.findLongURl(sc);
                    break;
                case 3:
                    controller.deactivateShortURL(sc);
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }
}

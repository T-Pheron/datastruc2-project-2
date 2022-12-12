package hashing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Project2 {

    private static Hashing<PCR> propertyHashing = new Hashing<>("file.bin", 13, 100);
    static Scanner keybordProject = new java.util.Scanner(System.in);

    Project2() {
    }

    private void addPCR() {
        String name = null;
        String surname = null;
        int born[] = new int[3];
        int date[] = new int[3];
        int hour[] = new int[2];
        int ID;
        Boolean result;

        System.out.println("Enter the name of patient (max 10 char):");
        do {
            if (name != null)
                System.out.println("Error : Enter the name of patient (max 10 char):");
            name = keybordProject.next();
        } while (name.length() > 10);

        System.out.println("Enter the surname of patient (max 10 char):");
        do {
            if (surname != null)
                System.out.println("Error : Enter the surname of patient (max 10 char):");
            surname = keybordProject.next();
        } while (name.length() > 10);

        System.out.println("Date of born :");
        System.out.println("Day :");
        born[0] = 0;

        do {
            try {
                if (born[0] != 0)
                    System.out.println("Error - try again\n Day :");
                born[0] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                born[0] = -1;
                keybordProject.nextLine();
            }
        } while (born[0] < 1 || born[0] > 31);

        System.out.println("Month :");
        born[1] = 0;

        do {
            try {
                if (born[1] != 0)
                    System.out.println("Error - try again\n Month :");
                born[1] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                born[1] = -1;
                keybordProject.nextLine();
            }
        } while (born[1] < 1 || born[1] > 12);

        System.out.println("Year :");
        born[2] = 0;

        do {
            try {
                if (born[2] != 0)
                    System.out.println("Error - try again\n Year :");
                born[2] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                born[2] = -1;
                keybordProject.nextLine();
            }
        } while (born[2] < 1900);

        System.out.println("Date of test :");
        System.out.println("Day :");
        date[0] = 0;

        do {
            try {
                if (date[0] != 0)
                    System.out.println("Error - try again\n Day :");
                date[0] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                date[0] = -1;
                keybordProject.nextLine();
            }
        } while (date[0] < 1 || date[0] > 31);

        System.out.println("Month :");
        date[1] = 0;

        do {
            try {
                if (date[1] != 0)
                    System.out.println("Error - try again\n Month :");
                date[1] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                date[1] = -1;
                keybordProject.nextLine();
            }
        } while (date[1] < 1 || date[1] > 12);

        System.out.println("Year :");
        date[2] = 0;

        do {
            try {
                if (date[2] != 0)
                    System.out.println("Error - try again\n Year :");
                date[2] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                date[2] = -1;
                keybordProject.nextLine();
            }
        } while (date[2] < 1900);

        System.out.println("Hour of test :");
        System.out.println("Hour :");
        hour[0] = 0;

        do {
            try {
                if (hour[0] != 0)
                    System.out.println("Error - try again\n Hour :");
                hour[0] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                hour[0] = -1;
                keybordProject.nextLine();
            }
        } while (hour[0] < 0 || hour[0] > 23);

        System.out.println("Minute :");
        hour[1] = 0;

        do {
            try {
                if (hour[1] != 0)
                    System.out.println("Error - try again\n Minute :");
                hour[1] = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                hour[1] = -1;
                keybordProject.nextLine();
            }
        } while (hour[1] < 0 || hour[1] > 59);

        String resultTest;
        System.out.println("Result of the test : ");
        System.out.println("Positif : Yes or No ?");
        keybordProject.nextLine();
        resultTest = keybordProject.next();
        while (!resultTest.equals("Yes") && !resultTest.equals("yes") && !resultTest.equals("No")
                && !resultTest.equals("no")) {
            System.out.println("Error - try again\n Positif : Yes or No ?");
            resultTest = keybordProject.next();
        }
        if (resultTest.equals("Yes") || resultTest.equals("yes"))
            result = true;
        else
            result = false;

        do {
            ID = ThreadLocalRandom.current().nextInt(100, 99999);
        } while (propertyHashing.Find(ID) != null);

        PCR pcrInsert = new PCR(ID, name, surname, convert(born[0]) + "/" + convert(born[1]) + "/" + convert(born[2]),
                convert(date[0]) + "/" + convert(date[1]) + "/" + convert(date[2]),
                convert(hour[0]) + ":" + convert(hour[1]), result);
        if (propertyHashing.Insert(pcrInsert) == true)
            System.out.println("\n\n\nThe test has been registered under the identifier : " + ID + "\n\n\n");
        else
            System.out.println("\n\n\nError : Error while recording the test please try again\n\n\n");

    }

    void searchPCR() {
        int reference = -1;
        boolean fist = true;
        PCR findPCR = new PCR();
        System.out.println("Please enter the PCR test reference :");

        do {
            try {
                if (fist == false)
                    System.out.println(
                            "The test reference was not found in the database, please re-order or enter 0 to quit :");
                reference = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                reference = -1;
                keybordProject.nextLine();
            }
            if (reference != 0) {
                findPCR = propertyHashing.Find(reference);
                if (findPCR == null) {
                    reference = -1;
                }
            }
            fist = false;
        } while (reference == -1 && findPCR == null);

        if (reference != 0) {
            viewPCR(findPCR);
        }
    }

    String convert(int nombre) {
        if (nombre < 10) {
            return "0" + nombre;
        } else
            return "" + nombre;
    }

    void viewPCR(PCR paPCR) {
        System.out.println("\n\n\n\n" + "Reference of the test : " + paPCR.getID());
        System.out.println("Date  : " + paPCR.getDate());
        System.out.println("Time : " + paPCR.getHour());
        System.out.println("Result : " + paPCR.getResult());

        System.out.println("\nPatient");
        System.out.println("Name  : " + paPCR.getName());
        System.out.println("Surname  : " + paPCR.getSurname());
        System.out.println("Date of Born  : " + paPCR.getBorn() + "\n\n");
    }

    void deletePCR() {
        int reference = -1;
        System.out.println("Please enter the PCR test reference for delete:");

        do {
            try {
                if (reference != -1)
                    System.out.println(
                            "The test reference was not found in the database, please re-order or enter 0 to quit :");
                reference = keybordProject.nextInt();
            } catch (java.util.InputMismatchException e) {
                reference = -1;
                keybordProject.nextLine();
            }
        } while (reference == -1 && propertyHashing.Find(reference) == null);

        if (reference != 0) {
            if (propertyHashing.Delete(reference) == true)
                System.out.println("\n\nThe test " + reference + " was well delete\n\n");
            else
                System.out.println("Error when suppressing the test, please try again");

        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException,
            IllegalAccessException, InterruptedException {
        boolean statuWhile = true;
        Project2 project2 = new Project2();

        do {

            System.out.println("Welcome to the Centrum of Health PCR Test Registration System");
            System.out.println("Please enter the number of what you want to do :");

            System.out.println("1 - Insert a PCR test result");
            System.out.println("2 - Find the test result");
            System.out.println("3 - Delete a PCR test");
            System.out.println("4 - Close app");
            int number = keybordProject.nextInt();
            while (number < 1 || number > 7) {
                System.out.println("Error - The number entered is not an option  ");
                System.out.println("Please enter the number of what you want to do :");

                System.out.println("1 - Insert a PCR test result");
                System.out.println("2 - Find the test result");
                System.out.println("5 - Delete a PCR test");
                System.out.println("4 - Close app");
            }

            switch (number) {
                case 1:
                    project2.addPCR();
                    break;
                case 2:
                    project2.searchPCR();
                    break;
                case 3:
                    project2.deletePCR();
                    break;
                case 4:
                    statuWhile = false;
                    break;
                default:
                    break;
            }
        } while (statuWhile == true);

    }

}
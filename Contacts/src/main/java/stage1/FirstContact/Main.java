package stage1.FirstContact;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NewContact newContact = new NewContact();
    }
}

class NewContact {
    String firstName;
    String surName;
    String number;

    public NewContact() {
        Scanner scanner = new Scanner(System.in);
        this.firstName = getFirstName(scanner);
        this.surName = getSurName(scanner);
        this.number = getNumber(scanner);
        System.out.println("A record created!");
        System.out.println("A Phone Book with a single record created!");
    }

    public String getFirstName(Scanner scanner) {
        System.out.println("Enter the name of the person:");
        return scanner.nextLine();
    }

    public String getSurName(Scanner scanner) {
        System.out.println("Enter the surname of the person:");
        return scanner.nextLine();
    }

    public String getNumber(Scanner scanner ) {
        System.out.println("Enter the number:");
        return scanner.nextLine();
    }
}

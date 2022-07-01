package stage2.CreateMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactsMenu contactsMenu = new ContactsMenu();
        contactsMenu.contactsMenu();
    }
}

class ContactsMenu {
    PhoneBook phoneBook = new PhoneBook();

    public void contactsMenu() {
        Scanner scanner = new Scanner(System.in);
        List<Contact> contactList = new ArrayList<>();
        String actionMenu = "Enter action (add, remove, edit, count, list, exit): ";

        while (true) {
            System.out.print(actionMenu);
            String action = scanner.nextLine();

            switch (action) {
                case "exit":
                    return;
                case "add":
                    phoneBook.addContact(contactList);
                    break;
                case "remove":
                    phoneBook.removeContact(contactList);
                    break;
                case "edit":
                    phoneBook.editContact(contactList);
                    break;
                case "count":
                    phoneBook.countContacts(contactList);
                    break;
                case "list":
                    phoneBook.showContacts(contactList);
                    break;
                default:
                    break;
            }
        }
    }
}

class Contact {
    String name;
    String surname;
    String number;

    public Contact(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }
}

class PhoneBook {
    public Contact getNewContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter the number: ");
        String number = getNumber();
        return new Contact(name, surname, number);
    }

    public void addContact(List<Contact> contactList) {
        Contact contact = getNewContact();
        contactList.add(contact);
        System.out.println("The record added!");
    }

    public void removeContact(List<Contact> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No records to remove!");
        }
        else {
            showContacts(contactList);
            int remove = new Scanner(System.in).nextInt();
            contactList.remove(remove -1);
            System.out.println("The record removed!");
        }
    }

    public void editContact(List<Contact> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No records to edit!");
        }
        else {
            Scanner scanner = new Scanner(System.in);
            showContacts(contactList);
            System.out.print("Select a record: ");
            int index = scanner.nextInt();
            Contact contact = contactList.get(index -1);
            updateContact(contact);
            System.out.println("The record updated!");
        }
    }

    public void countContacts(List<Contact> contactList) {
        System.out.printf("The Phone Book has %d records.\n", contactList.size());
    }

    public void showContacts(List<Contact> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No records to list");
        }
        else {
            int count = 1;
            for (Contact contact : contactList) {
                System.out.printf("%d. %s %s, %s\n", count, contact.name, contact.surname, contact.number);
                count++;
            }
        }
    }

    public void updateContact(Contact contact) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a field (name, surname, number): ");
        String field = scanner.nextLine();
        switch (field) {
            case "name":
                System.out.print("Enter name: ");
                contact.name = scanner.nextLine();
                break;
            case "surname":
                System.out.print("Enter surname: ");
                contact.surname = scanner.nextLine();
                break;
            case "number":
                System.out.print("Enter number: ");
                contact.number = getNumber();
                break;
            default:
                break;
        }
    }
    public String getNumber() {
        String firstGroup = "[+]?[\\p{Alnum}]+";
        String firstGroupBrackets = "[+]?[(][\\p{Alnum}]+[)]";
        String secondGroup = "[\\p{Alnum}]{2,}";
        String secondGroupBrackets = "[(][\\p{Alnum}]{2,}[)]";

        String number = new Scanner(System.in).nextLine();
        String[] numberArray = number.replaceAll("-", " ").split(" ");

        if (number.isBlank() || !checkNumber(number)) {
            return wrongNumber();
        }

        for (int i = 0; i < numberArray.length; i++) {
            String next = numberArray[i];

            if (i == 0) {
                if (next.contains("(") || next.contains(")")) {
                    if (!next.matches(firstGroupBrackets)) {
                        return wrongNumber();
                    }
                }
                else if (!next.matches(firstGroup)) {
                    return wrongNumber();
                }
            }
            else {
                if (next.contains("(") || next.contains(")")) {
                    if (!next.matches(secondGroupBrackets)) {
                        return wrongNumber();
                    }
                }
                else if (!next.matches(secondGroup)) {
                    return wrongNumber();
                }
            }
        }
        return number;
    }

    public String wrongNumber() {
        System.out.println("wrong number format!");
        return "[no number]";
    }

    public Boolean checkNumber(String number) {
        boolean check = true;
        int leftBracket = 0;
        int rightBracket = 0;
        int operators = 0;

        String punct = "\\p{Punct}&&[^()-]";

        String[] array = number.split("");

        for (String next : array) {

            if (next.equals("(")) {
                leftBracket++;
            }
            else if (next.equals(")")) {
                rightBracket++;
            }
            else if (next.matches(punct)) {
                operators++;
            }
        }

        if (leftBracket != rightBracket || leftBracket > 1 || rightBracket > 1) {
            check = false;
        }
        if (operators != 0) {
            check = false;
        }
        return check;
    }
}





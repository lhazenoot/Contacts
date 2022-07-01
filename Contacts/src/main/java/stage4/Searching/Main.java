package stage4.Searching;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ContactMenu contactMenu = new ContactMenu();
        contactMenu.menu();
    }
}

class ContactMenu extends PhoneBook {
    String actionMenu = "Enter action (add, list, search, count, exit): ";

    public void menu() {
        String action;
        List<Contact> contactList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(actionMenu);
            action = scanner.nextLine();

            switch (action) {
                case "exit":
                    return;
                case "add":
                    add(contactList);
                    break;
                case "list":
                    list(contactList);
                    break;
                case "search":
                    search(contactList);
                    break;
                case "count":
                    count(contactList);
                    break;
                default:
                    break;
            }
        }
    }
}

class PhoneBook {
    public void add(List<Contact> contactList) {
        Contact contact = new AddContact().addContact();
        contactList.add(contact);
        System.out.println("The record added!\n");
    }

    public void list(List<Contact> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No records to list\n");
            return;
        }
        listContacts(contactList);
        showContactDetails(contactList);
    }

    public void listContacts(List<Contact> contactList) {
        int count = 1;
        for (Contact contact : contactList) {
            if (contact.isPerson) {
                System.out.printf("%d. %s %s\n", count, contact.name, contact.surname);
            }
            else {
                System.out.printf("%d. %s\n", count, contact.name);
            }
            count++;
        }
        System.out.println();
    }

    public void showContactDetails(List<Contact> contactList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter action ([number], back): ");
        String action = scanner.nextLine();

        if (action.matches("\\d")) {
            int num = Integer.parseInt(action);
            Contact contact = contactList.get(num -1);
            contact.printContact();
            recordContact(contactList, contact);
        }
    }

    public void recordContact(List<Contact> contactList, Contact contact) {
        while (true) {
            System.out.print("Enter action (edit, delete, menu): ");
            String action = new Scanner(System.in).nextLine();

            switch (action) {
                case "menu":
                    return;
                case "edit":
                    editContact(contact);
                    break;
                case "delete":
                    contactList.remove(contact);
                    System.out.println("Deleted\n");
                    break;
                default:
                    break;
            }
        }
    }

    public void editContact(Contact contact) {
        if (contact.isPerson) {
            System.out.print("Select a field (name, surname, birth, gender, number): ");
        }
        else {
            System.out.print("Select a field (name, address, number): ");
        }

        String action = new Scanner(System.in).nextLine();

        switch (action) {
            case "name":
                if (contact.isPerson) {
                    contact.name = contact.getName("Enter the name: ");
                }
                else {
                    contact.name = contact.getName("Enter the organization name: ");
                }
                break;
            case "surname":
                contact.surname = contact.getSurname();
                break;
            case "birth":
                contact.birth = contact.getBirth();
                break;
            case "gender":
                contact.gender = contact.getGender();
                break;
            case "address":
                contact.address = contact.getAddress();
                break;
            case "number":
                contact.number = contact.getNumber();
                break;
            default:
                break;
        }
        contact.timeUpdated = contact.getTime();
        System.out.println("Saved");
        contact.printContact();
    }

    public void search(List<Contact> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No records to search\n");
            return;
        }

        while (true) {
            List<Contact> searchList = searchContacts(contactList);
            System.out.printf("Found %d result:\n", searchList.size());
            listContacts(searchList);

            System.out.print("Enter action ([number], back, again): ");
            String action = new Scanner(System.in).nextLine();

            if (action.equals("back")) {
                return;
            }
            else if (action.equals("again")) {
                search(contactList);
            }
            else if (action.matches("\\d+")) {
                int num = Integer.parseInt(action);
                Contact contact = searchList.get(num - 1);
                num = contactList.indexOf(contact);
                contact = contactList.get(num);
                contact.printContact();

                recordContact(contactList, contact);
                return;
            }
        }
    }

    public List<Contact> searchContacts(List<Contact> contactList) {
        System.out.print("Enter search query: ");
        String search = new Scanner(System.in).nextLine().toLowerCase();
        List<Contact> searchList = new ArrayList<>();

        for (Contact contact : contactList) {
            if (contact.isPerson) {
                if (
                        contact.name.toLowerCase().contains(search) ||
                                contact.surname.toLowerCase().contains(search) ||
                                contact.birth.toLowerCase().contains(search) ||
                                contact.gender.toLowerCase().contains(search) ||
                                contact.number.toLowerCase().contains(search)
                ) searchList.add(contact);
            }
            else {
                if (
                        contact.name.toLowerCase().contains(search) ||
                                contact.address.toLowerCase().contains(search) ||
                                contact.number.toLowerCase().contains(search)
                ) {
                    searchList.add(contact);
                }
            }
        }
        return searchList;
    }

    public void count(List<Contact> contactList) {
        System.out.printf("The Phone Book has %d records.\n\n", contactList.size());
    }
}

class Contact implements ContactDetails {
    Boolean isPerson;
    String name;
    String surname;
    String birth;
    String gender;
    String address;
    String number;
    String timeCreated;
    String timeUpdated;

    public Contact(Boolean isPerson, String name, String surname, String birth, String gender, String address, String number, String timeCreated, String timeUpdated) {
        this.isPerson = isPerson;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
        this.number = number;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public void printContact() {
        if (isPerson) {
            System.out.printf("Name: %s\n", name);
            System.out.printf("Surname: %s\n", surname);
            System.out.printf("Birth date: %s\n", birth);
            System.out.printf("Gender: %s\n", gender);
        }
        else {
            System.out.printf("Organization name: %s\n", name);
            System.out.printf("Address: %s\n", address);
        }
        System.out.printf("Number: %s\n", number);
        System.out.printf("Time created: %s\n", timeCreated);
        System.out.printf("Time last edit: %s\n\n", timeUpdated);
    }
}

class AddContact implements ContactDetails {
    Boolean isPerson;
    String name;
    String surname;
    String birth;
    String gender;
    String address;
    String number;
    String timeCreated;
    String timeUpdated;

    public Contact addContact() {
        System.out.print("Enter person or organization: ");
        String input = new Scanner(System.in).nextLine();
        boolean type = input.equals("person");

        if (type) {
            this.isPerson = true;
            this.name = getName("Enter the name: ");
            this.surname = getSurname();
            this.birth = getBirth();
            this.gender = getGender();
        }
        else {
            this.isPerson = false;
            this.name = getName("Enter the organization name: ");
            this.address = getAddress();
        }
        this.number = getNumber();
        this.timeCreated = getTime();
        this.timeUpdated = getTime();

        return new Contact(isPerson, name, surname, birth, gender, address, number, timeCreated, timeUpdated);
    }
}

interface ContactDetails {

    default String setNoData(String string) {
        System.out.println(string);
        return "[no data]";
    }

    default String getName(String string) {
        System.out.print(string);
        return new Scanner(System.in).nextLine();
    }

    default String getSurname() {
        System.out.print("Enter the surname: ");
        return new Scanner(System.in).nextLine();
    }

    default String getBirth() {
        System.out.print("Enter the birth date: ");
        String birth = new Scanner(System.in).nextLine();
        return birth.isBlank() ? setNoData("Bad birth date!") : birth;
    }

    default String getGender() {
        System.out.print("Enter the gender: ");
        String gender = new Scanner(System.in).nextLine();
        return gender.isBlank() ? setNoData("Bad gender!") : gender;
    }

    default String getAddress() {
        System.out.print("Enter the address: ");
        return new Scanner(System.in).nextLine();
    }

    default String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }

    default String getNumber() {
        String firstGroup = "[+]?[\\p{Alnum}]+";
        String firstGroupBrackets = "[+]?[(][\\p{Alnum}]+[)]";
        String secondGroup = "[\\p{Alnum}]{2,}";
        String secondGroupBrackets = "[(][\\p{Alnum}]{2,}[)]";

        System.out.print("Enter the number: ");
        String number = new Scanner(System.in).nextLine();

        String[] numberArray = number.replaceAll("-", " ").split(" ");

        if (number.isBlank() || !checkNumber(number)) {
            return setNoData("Wrong number format!");
        }

        for (int i = 0; i < numberArray.length; i++) {
            String next = numberArray[i];

            if (i == 0) {
                if (next.contains("(") || next.contains(")")) {
                    if (!next.matches(firstGroupBrackets)) {
                        return setNoData("Wrong number format!");
                    }
                }
                else if (!next.matches(firstGroup)) {
                    return setNoData("Wrong number format!");
                }
            }
            else {
                if (next.contains("(") || next.contains(")")) {
                    if (!next.matches(secondGroupBrackets)) {
                        return setNoData("Wrong number format!");
                    }
                }
                else if (!next.matches(secondGroup)) {
                    return setNoData("Wrong number format!");
                }
            }
        }
        return number;
    }

    default Boolean checkNumber(String number) {
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

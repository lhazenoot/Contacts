package stage3.UpgradeYourContacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.menu();
    }
}

class Menu {
    PhoneBook phoneBook = new PhoneBook();
    List<Contact> contactList = new ArrayList<>();

    public void menu() {
        String actionMenu = "Enter action (add, remove, edit, count, info, exit): ";

        while (true) {
            System.out.print(actionMenu);
            String action = new Scanner(System.in).nextLine();

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
                    phoneBook.getCount(contactList);
                    break;
                case "info":
                    phoneBook.getInfo(contactList);
                    break;
                default:
                    break;
            }
        }
    }
}

class PhoneBook extends ContactDetails {
    Person person = new Person();
    Organization organization = new Organization();

    public Boolean showContacts(List<Contact> contactList, String string) {
        if (contactList.isEmpty()) {
            System.out.println(string + "\n");
            return false;
        }
        else {
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
            return true;
        }
    }

    public Contact getRecord(List<Contact> contactList) {
        System.out.print("Select a record: ");
        int num = new Scanner(System.in).nextInt();
        return contactList.get(num - 1);
    }

    public void addContact(List<Contact> contactList) {
        System.out.print("Enter the type (person, organization): ");
        String type = new Scanner(System.in).nextLine();
        Contact contact;

        switch (type) {
            case "person":
                contact = person.getPerson();
                contactList.add(contact);
                break;
            case "organization":
                contact = organization.getOrganization();
                contactList.add(contact);
                break;
            default:
                break;
        }
        System.out.println("The record added!\n");
    }

    public void removeContact(List<Contact> contactList) {
        Boolean info = showContacts(contactList, "No records to remove");

        if (!info) {
            return;
        }

        Contact remove = getRecord(contactList);
        contactList.remove(remove);
        System.out.println("The record removed!\n");
    }

    public void editContact(List<Contact> contactList) {
        Scanner scanner = new Scanner(System.in);
        Boolean info = showContacts(contactList, "No records to edit");

        if (!info) {
            return;
        }

        Contact edit = getRecord(contactList);

        String field;

        if (edit.isPerson) {
            System.out.print("Select a field (name, surname, birth, gender, number): ");
        }
        else {
            System.out.print("Select a field (name, address, number): ");
        }
        field = scanner.nextLine();

        switch (field) {
            case "name":
                if (edit.isPerson) {
                    System.out.print("Enter the name: ");
                }
                else {
                    System.out.print("Enter the organization name: ");
                }
                edit.name = scanner.nextLine();
                break;
            case "surname":
                System.out.print("Enter the surname: ");
                edit.surname = scanner.nextLine();
                break;
            case "birth":
                edit.birth = person.getBirth();
                break;
            case "gender":
                edit.gender = person.getGender();
                break;
            case "address":
                edit.address = organization.getAddress();
                break;
            case "number":
                edit.number = getPhoneNumber();
                break;
            default:
                break;
        }
        edit.timeEdit = setTimeLastEdit();
        System.out.println("The record updated!\n");
    }

    public void getCount(List<Contact> contactList) {
        System.out.printf("The Phone Book has %d records.\n\n", contactList.size());
    }

    public void getInfo(List<Contact> contactList) {
        Boolean info = showContacts(contactList, "No records to list");

        if (!info) {
            return;
        }

        Contact record = getRecord(contactList);

        if (record.isPerson) {
            System.out.printf("Name: %s\n", record.name);
            System.out.printf("Surname: %s\n", record.surname);
            System.out.printf("Birth date: %s\n", record.birth);
            System.out.printf("Gender: %s\n", record.gender);
            System.out.printf("Number: %s\n", record.number);
            System.out.printf("Time created: %s\n", record.timeCreated);
            System.out.printf("Time last edit: %s\n", record.timeEdit);
        }
        else {
            System.out.printf("Organization name: %s\n", record.name);
            System.out.printf("Address: %s\n", record.address);
            System.out.printf("Number: %s\n", record.number);
            System.out.printf("Time created: %s\n", record.timeCreated);
            System.out.printf("Time last edit: %s\n", record.timeEdit);
        }
        System.out.println();
    }
}

class Contact {
    Boolean isPerson;
    String name;
    String surname;
    String birth;
    String gender;
    String address;
    String number;
    String timeCreated;
    String timeEdit;

    public Contact(Boolean isPerson, String name, String surname, String birth, String gender, String number, String timeCreated, String timeEdit) {
        this.isPerson = isPerson;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
        this.number = number;
        this.timeCreated = timeCreated;
        this.timeEdit = timeEdit;
    }

    public Contact(Boolean isPerson, String name, String address, String number, String timeCreated, String timeEdit) {
        this.isPerson = isPerson;
        this.name = name;
        this.address = address;
        this.number = number;
        this.timeCreated = timeCreated;
        this.timeEdit = timeEdit;
    }
}

class Person extends ContactDetails {
    Boolean isPerson;
    String name;
    String surname;
    String birth;
    String gender;
    String number;
    String timeCreated;
    String timeEdit;

    public Contact getPerson() {
        this.isPerson = true;
        this.name = getName();
        this.surname = getSurname();
        this.birth =getBirth();
        this.gender = getGender();
        this.number = getPhoneNumber();
        this.timeCreated = setTimeCreated();
        this.timeEdit = setTimeLastEdit();
        return new Contact(isPerson, name, surname, birth, gender, number, timeCreated, timeEdit);
    }

    public String getName() {
        System.out.print("Enter the name: ");
        return new Scanner(System.in).nextLine();
    }

    public String getSurname() {
        System.out.print("Enter the surname: ");
        return new Scanner(System.in).nextLine();
    }

    public String getBirth() {
        System.out.print("Enter the birth date: ");
        String birthdate = new Scanner(System.in).nextLine();
        return birthdate.isBlank() ? setNoData("Bad birth date!") : birthdate;
    }

    public String getGender() {
        System.out.print("Enter the gender (M, F): ");
        String gender = new Scanner(System.in).nextLine();
        return gender.isBlank() ? setNoData("Bad gender!") : gender;
    }
}

class Organization extends ContactDetails {
    Boolean isPerson;
    String name;
    String address;
    String number;
    String timeCreated;
    String timeEdit;

    public Contact getOrganization() {
        this.isPerson = false;
        this.name = getName();
        this.address = getAddress();
        this.number = getPhoneNumber();
        this.timeCreated = setTimeCreated();
        this.timeEdit = setTimeLastEdit();
        return new Contact(isPerson, name, address, number, timeCreated, timeEdit);
    }

    public String getName() {
        System.out.print("Enter the organization name: ");
        return new Scanner(System.in).nextLine();
    }

    public String getAddress() {
        System.out.print("Enter the address: ");
        return new Scanner(System.in).nextLine();
    }
}

class ContactDetails {

    public String setNoData(String string) {
        System.out.println(string);
        return "[no data]";
    }

    public String setTimeCreated() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }

    public String setTimeLastEdit() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }

    public String getPhoneNumber() {
        String firstGroup = "[+]?[\\p{Alnum}]+";
        String firstGroupBrackets = "[+]?[(][\\p{Alnum}]+[)]";
        String secondGroup = "[\\p{Alnum}]{2,}";
        String secondGroupBrackets = "[(][\\p{Alnum}]{2,}[)]";

        System.out.print("Enter the number: ");
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



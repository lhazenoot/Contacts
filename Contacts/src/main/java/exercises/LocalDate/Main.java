package exercises.LocalDate;

import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDate dateStart = LocalDate.parse(scanner.nextLine());
        System.out.println(dateStart.plusWeeks(2));
    }
}

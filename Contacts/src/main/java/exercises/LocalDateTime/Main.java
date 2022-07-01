package exercises.LocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine());
        LocalDate localDate = dateTime.plusHours(11).toLocalDate();
        System.out.println(localDate);
    }
}

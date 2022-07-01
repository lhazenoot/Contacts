package exercises.LocalTime;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalTime time1 = LocalTime.parse(scanner.next());
        LocalTime time2 = LocalTime.parse(scanner.next());

        Calculate calculate = new Calculate();
        calculate.calculateSeconds(time1, time2);
    }
}

class Calculate {
    public void calculateSeconds(LocalTime time1, LocalTime time2) {
        int seconds = Math.abs(time1.toSecondOfDay() - time2.toSecondOfDay());
        System.out.println(seconds);
    }
}

package basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainSream {
   private int[] array = new int[]{6,4,2,5,1,2,6,3,6};

    private int minValue (int[] values){
       return Arrays.stream(values).
               distinct().
               sorted().
               reduce(0, (a, b) -> 10 * a + b);

    }

    private List<Integer> oddOrEven(List<Integer> integers){
        Integer number = (integers.stream().reduce(0,Integer::sum)) % 2;
        return integers.stream().filter(x -> x % 2 == number).collect(Collectors.toList());
    }
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Collections.addAll(integers, 1, 2, 3, 1);

        MainSream mainSream = new MainSream();

        System.out.println("мин число из: " + mainSream.minValue(mainSream.array));
        System.out.println(mainSream.oddOrEven(integers));
    }
}

package basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class MainSream {
   private int[] array = new int[]{6,4,2,5,1,2,6,3,6};

    private int minValue (int[] values){
       int result = Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (a, b) -> 10 * a + b);
        return result;
    }

    private List<Integer> oddOrEven(List<Integer> integers){
        List<Integer> list = new ArrayList<>();
        int sum = integers.stream().reduce(0,Integer::sum);
        if (sum % 2 == 1){
             list =  integers.stream().filter(o -> o % 2 !=0).collect(Collectors.toList());
            integers.removeAll(list);
            return integers;
        }
        else {
            list =  integers.stream().filter(o -> o % 2 ==0).collect(Collectors.toList());
            integers.removeAll(list);
        }
        return integers;
    }
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Collections.addAll(integers, 1, 2, 3, 1);

        MainSream mainSream = new MainSream();

        System.out.println("мин число из: " + mainSream.minValue(mainSream.array));
        System.out.println( mainSream.oddOrEven(integers));
    }
}

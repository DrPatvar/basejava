package basejava.webapp;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainSream {
   private int[] array = new int[]{6,4,2,5,1,2,6,3,6};

    private void minValue (int[] values){
     // Arrays.stream(values).collect(Collectors.toCollection(LinkedHashSet::new))
    }
    public static void main(String[] args) {
        MainSream mainSream = new MainSream();
        mainSream.minValue(mainSream.array);
    }
}

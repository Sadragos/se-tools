package eu.fehlemann;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUtils {
    public static <T> ArrayList<T> ArrayToList(T[] input) {
        if(input != null) return new ArrayList<>(Arrays.asList(input));
        else return new ArrayList<>();
    }
}

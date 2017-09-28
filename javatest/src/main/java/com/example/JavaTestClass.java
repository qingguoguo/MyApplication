package com.example;

import java.util.ArrayList;
import java.util.List;

public class JavaTestClass {

    public static void main(String[] args) {
//        Integer[] intArry = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        binarySearch(intArry, 3);

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");

        for (String temp : list) {
            if ("1".equals(temp))
                list.remove(temp);
        }
    }

    public static int binarySearch(Integer[] srcArray, int des) {
        int low = 0;
        int high = srcArray.length - 1;

        while ((low <= high) && (low <= srcArray.length - 1)
                && (high <= srcArray.length - 1)) {
            int middle = (high + low) >> 1;
            if (des == srcArray[middle]) {
                return middle;
            } else if (des < srcArray[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }
}

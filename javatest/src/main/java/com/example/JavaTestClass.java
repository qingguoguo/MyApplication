package com.example;

public class JavaTestClass {

    public static void main(String[] args) {
//        Integer[] intArry = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        binarySearch(intArry, 3);

//        List<String> list = new ArrayList<String>();
//        list.add("1");
//        list.add("2");
//
//        for (String temp : list) {
//            if ("1".equals(temp))
//                list.remove(temp);
//        }

        String stra = "ABC";
        String straAA = "ABC";
        String strb = new String("ABC");
        System.out.println(stra.hashCode());
        System.out.println(straAA.hashCode());
        System.out.println(stra == strb);
        System.out.println(stra == strb);//1,false
        System.out.println(stra.equals(strb));//2,true

        String str3 = new String("ijk");
        String str4 = str3.substring(0);
        System.out.println(str3 == str4);//5,true
        System.out.println((new String("ijk") == str4)); //6,false
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

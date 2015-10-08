package solutions.alterego.android.unisannio.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    //method for extracting Data from String
    public static String extractingData(String s) {

        String date = null;
        Pattern p = Pattern.compile("(\\d{2}).+?(\\d{2}.+?(\\d{4}))");
        Matcher m = p.matcher(s);

        while (m.find()){
            date = m.group();
        }

        return date;
    }


    //method for extracting all the numbers from string
    public static ArrayList<Integer> extractingNumbers(String s) {

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);

        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }

        if(numbers.size() <= 0) {
            numbers.add(-1);
        }

        return numbers;

    }


}

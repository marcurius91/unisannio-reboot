package solutions.alterego.android.unisannio.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;
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

    public static String convertMonth(String date){

        String date_temp = date.replace("Pubblicato:","");
        date_temp = date_temp.replace(" ","/");

        StringTokenizer st = new StringTokenizer(date_temp,"/");
        String day = st.nextToken();
        String month = st.nextToken();
        String year = st.nextToken();

        String final_date = null;

        switch (month){
            case "Gennaio":
                month = "01";
                break;
            case "Febbraio":
                month = "02";
                break;
            case "Marzo":
                month = "03";
                break;
            case "Aprile":
                month = "04";
                break;
            case "Maggio":
                month = "05";
                break;
            case "Giugno":
                month = "06";
                break;
            case "Luglio":
                month = "07";
                break;
            case "Agosto":
                month = "08";
                break;
            case "Settembre":
                month = "09";
                break;
            case "Ottobre":
                month = "10";
                break;
            case "Novembre":
                month = "11";
                break;
            case "Dicembre":
                month = "12";
                break;
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }

        final_date = day.concat("/").concat(month).concat("/").concat(year);

        return final_date;
    }


}

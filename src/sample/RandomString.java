package sample;

import java.util.Random;

/**
 * Created by ZloiY on 10.05.2016.
 */
public class RandomString {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder str;
    Random rnd;

    public String getRandString(Integer size){
        str = new StringBuilder();
        rnd = new Random();
        while (str.length() < (int)(rnd.nextFloat()*size)){
            int index = (int)(rnd.nextFloat()*chars.length());
            str.append(chars.charAt(index));
        }
        String resultStr = str.toString();
        return resultStr;
    }
}

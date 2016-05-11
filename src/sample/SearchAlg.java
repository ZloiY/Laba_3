package sample;

import java.util.HashMap;

/**
 * Created by ZloiY on 10.05.2016.
 */
public class SearchAlg {

    public int getFirstEntry(String source, String template){
        int sourceLen = source.length();
        int tmpLen = template.length();
        if(tmpLen > sourceLen){
            return -1;
        }
        HashMap<Character, Integer> offsetTable = new HashMap<>();
        for (int index = 0; index <= 255; index++) {
            offsetTable.put((char) index, tmpLen);
        }
        for (int symbol =0; symbol < tmpLen-1; symbol++){
            offsetTable.put(template.charAt(symbol), tmpLen -symbol -1);
        }
        int lastIndex = tmpLen-1;
        int firstIndex = lastIndex;
        int anotherIndex = lastIndex;
        while(firstIndex >= 0 && lastIndex <= sourceLen -1){
            firstIndex = tmpLen - 1;
            anotherIndex = lastIndex;
            while(firstIndex >= 0 && source.charAt(anotherIndex) == template.charAt(firstIndex)){
                firstIndex--;
                anotherIndex--;
            }
            lastIndex += offsetTable.get(source.charAt(lastIndex));
        }
        if (anotherIndex >= sourceLen - tmpLen){
            return -1;
        }else{
            return anotherIndex+1;
        }
    }

}

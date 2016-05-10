package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ZloiY on 10.05.2016.
 */
public class FileMake {
    FileMake(Integer numberOfSymbols, Integer numberOfRows){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("graphic.txt", true))){
            RandomString rndStr = new RandomString();
            for (int curRow = 0; curRow < numberOfRows; curRow++){
                bw.write(rndStr.getRandString(numberOfSymbols));
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

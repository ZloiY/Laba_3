package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Created by ZloiY on 10.05.2016.
 */
public class FileMake {
    File file = new File("template.txt");
    List<String> firstList = new ArrayList<>();

    public List<String> getFirstList() {
        return firstList;
    }

    FileMake(Integer numberOfSymbols, Integer numberOfRows){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("graphic.txt", true))){
            RandomString rndStr = new RandomString();
            for (int curRow = 0; curRow < numberOfRows; curRow++){
                firstList.add(rndStr.getRandString(numberOfSymbols));
                bw.write(firstList.get(curRow));
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    FileMake(){}

    public List<String> readFile(String fileName) {
        List<String> allStrings = new ArrayList<>();
        File file = new File(fileName);
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            allStrings.add(line);
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                allStrings.add(br.readLine());
            }
            fr.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return allStrings;
    }
}

import java.util.*;
import java.io.*;



public class Word {
    Set<String> wordBank;
    public Word() {
        wordBank = new HashSet<>();
        createWordBank();
    }

    public void createWordBank(){
        try (BufferedReader reader = new BufferedReader(new FileReader("WordBank.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordBank.add(line.toLowerCase().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  boolean isWord(String word){
        return wordBank.contains(word);
    }

}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //System.out.println(String.valueOf("GAATTCTCTCTTGTTGTAGTCTCTTGACAAAATGCAATGGTCAGGTAGCG".length()));
        loadByNumber(2);
        loadByNumber(6);
        loadByNumber(9);
    }
    private static BufferedReader bufferedReader;
    private static void loadByNumber(int i) {
        char[] current = new char[i];
        List<String> resultsToDb = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("Genome_1-1.txt"));
            int s = 0;
            for(int j = 0;j < i - 1; j++){
                s = bufferedReader.read();
                current[j] = (char) s;
            }
            while(s != -1){
                s = bufferedReader.read();
                if(s == -1){
                    break;
                }
                if((char) s < 'A' || (char)  s > 'Z'){
                    continue;
                }
                current[i-1] = (char) s;
                resultsToDb.add(new String(current));
                for(int j = 0; j < i - 1;j ++){
                    current[j] = current[j+1];
                }
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        DataServiceLoader loader = DataServiceLoader.create("postgres","QAZedctgb123","jdbc:postgresql://localhost:5432/plagiarism",i+1);
        for(String string: resultsToDb){
            loader.save(string);
        }
        loadAnotherTable(i);
    }

    private static void loadAnotherTable(int i) {
        char[] current = new char[i];
        List<String> resultsToDb = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("Genome_2-1.txt"));
            int s = 0;
            for(int j = 0;j < i - 1; j++){
                s = bufferedReader.read();
                current[j] = (char) s;
            }
            while(s != -1){
                s = bufferedReader.read();
                if(s == -1){
                    break;
                }
                if((char) s < 'A' || (char)  s > 'Z'){
                    continue;
                }
                current[i-1] = (char) s;
                resultsToDb.add(new String(current));
                for(int j = 0; j < i - 1;j ++){
                    current[j] = current[j+1];
                }
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        DataServiceLoader loader = DataServiceLoader.create("postgres","QAZedctgb123","jdbc:postgresql://localhost:5432/plagiarism",i+2);
        for(String string: resultsToDb){
            loader.save(string);
        }
    }
}

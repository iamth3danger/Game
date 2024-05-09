
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextConverter {
    public static String[][] convertTextFileToArray(String filePath) {
        String[][] array = new String[20][125];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                for (int i = 0; i < words.length; i++) {
                    array[row][i] = words[i];
                }
                row++;
                if (row >= 20) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}
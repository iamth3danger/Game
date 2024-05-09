package ImageHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextConverter {
    
    
    
    public static String[][] convertTextFileToArray6(String filePath) {
        int rows = 0;
        int columns = 0;

        // Count number of rows and columns
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows++;
                if(rows==1){
                    columns=line.replace(",", "").length();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fill in the array
        String[][] array = new String[rows][columns];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int row = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                for (int i = 0; i < words.length; i++) {
                    array[row][i] = words[i].replace(",", "");
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }
    
    public static String[][] convertTextFileToArray(String filePath) {
        int rows = 0;
        int columns = 0;

        // Count number of rows and columns
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows++;
                if(rows==1){
                	columns=line.split(",").length - 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fill in the array
        String[][] array = new String[rows][columns];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int row = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                for (int i = 0; i < columns; i++) {
                    array[row][i] = words[i].replace(",", "");
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }
    
    public static void countCols(String filePath) {
        int rows = 0;
        int totalColumns = 0;

        // Count number of rows and columns
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows++;
                int columnsInLine = line.split(",").length;
                totalColumns += columnsInLine;
                System.out.println("Number of columns in line " + rows + ": " + columnsInLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate and print the average number of columns
        double averageColumns = (double) totalColumns / rows;
        System.out.println("Average number of columns: " + averageColumns);
    }
    
    
    
    
    public static void main(String[] args) {
    	TextConverter.countCols("level.txt");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
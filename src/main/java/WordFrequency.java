import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class WordFrequency {
    public static void main(String[] args) {
    	String filePath = "usconstitution.txt";
        int[] tableSizes = {1361, 2003, 3001};
        for (int size : tableSizes) {
            HashedDictionary<String, Integer> dictionary = new HashedDictionary<>(size);
            Scanner scanner;
			try {
				scanner = new Scanner(new File(filePath));
			} catch (FileNotFoundException e) {
				System.err.println("File not found: " + filePath);
                return;
			}
            while (scanner.hasNext()) {
                String word = scanner.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!word.isEmpty()) {
                    Integer count = dictionary.getValue(word);
                    dictionary.add(word, (count == null) ? 1 : count + 1);
                }
            }
            scanner.close();
            
            Iterator<String> keyIterator = dictionary.getKeyIterator();
            while (keyIterator.hasNext()) {
                String word = keyIterator.next();
                System.out.println(word + ": " + dictionary.getValue(word));
            }

            System.out.println("Table Length: " + size);
            System.out.println("Collisions: " + dictionary.getCollisionCount());
            System.out.println("Unique Words: " + dictionary.getKeyIterator().hasNext());
        }
    }
}

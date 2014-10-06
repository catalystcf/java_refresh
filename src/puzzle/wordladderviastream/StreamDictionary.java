package puzzle.wordladderviastream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Instance of a dictionary. 
 * @author Alex Ar
 *
 */
public class StreamDictionary {
	
	String [] words = { "TRUCK", "PLANE", "BONUS", "BIGUS", "FICUS", "SEARS", "SMILE", "SMEAR", "CHAIR" }; 
    Set<String>  filteredWords;

    int word_size;
    
    /**
     * initialize dictionary with a word size we are looking fore
     * @param word_size
     */
    public StreamDictionary( int word_size)
    {
    	this.word_size = word_size;
    }
    
	public void init() throws IOException
	{
		
		String homePath = System.getenv("HOMEPATH");
		String fileName =  homePath + File.separator + "words.txt";
		System.out.println("Reading " + fileName);
			
		List<String> allWords = Files.readAllLines(Paths.get(fileName));
		
		System.out.println("Read " + fileName + " with " + allWords.size() + " words");
		
		Stream<String> filteredWordsStream = allWords.stream().filter(x->this.word_size == x.length());
		// TODO: lowercase them
		this.filteredWords = filteredWordsStream.collect(Collectors.toSet());
		
		System.out.println("Filtered to " + this.filteredWords.size() + " words");
	}

	boolean contains(String madeUpWord) {
		
		return this.filteredWords.contains(madeUpWord);

	}
};
package puzzle.wordladderviastream;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class StreamDictionaryTest {

	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
	      System.out.println("Starting test: " + description.getMethodName());
	   }
	};
	
	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);
	
	/** check basic use and initialization
	 * (integration test)
	 * @throws Exception */
	@Test
	public void testStreamDictionary() throws Exception {
		StreamDictionary sd = new StreamDictionary(5);
		sd.init();

		
		String firstWord = sd.filteredWords.toArray(new String[0])[0]; 
		
		System.out.println("First Word  = " + firstWord);
		System.out.println("Number of words = " + sd.filteredWords.size());
		
		// very weak test.. but .. 
		assertTrue( sd.filteredWords.size() > 1000);
		
	}

	

}

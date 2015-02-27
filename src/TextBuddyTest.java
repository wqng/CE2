import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TextBuddyTest {
	
	ArrayList<String> searchFoundContents;
	String fileName = "mytextfile.txt";

	@Before
	public void setUp() throws Exception {
		TextBuddy.loadFileData(new String[] {"mytextfile.txt"});
		
		TextBuddy.processInputData("add nus", fileName);
		TextBuddy.processInputData("add ntu", fileName);
		TextBuddy.processInputData("add smu", fileName);
		TextBuddy.processInputData("add sutd", fileName);
		TextBuddy.processInputData("add sit", fileName);
		TextBuddy.processInputData("add sim", fileName);
		TextBuddy.processInputData("add national university of singapore", fileName);
		TextBuddy.processInputData("add nanyang technological university", fileName);
		TextBuddy.processInputData("add singapore management university", fileName);
	}

	@After
	public void tearDown() throws Exception {
		TextBuddy.clearData(fileName);
	}
	
	@Test
	public void testSearchEmpty() {
		searchFoundContents = TextBuddy.searchData("", fileName);
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSearchNoSuchItem() {
		searchFoundContents = TextBuddy.searchData("ibm", fileName);
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSearchValid() {
		searchFoundContents = TextBuddy.searchData("t", fileName);
		assertTrue(searchFoundContents.contains("ntu"));
		assertTrue(searchFoundContents.contains("sit"));
		assertTrue(searchFoundContents.contains("sutd"));
		assertFalse(searchFoundContents.contains("nus"));
	}
	
	@Test
	public void testSearchMultipleKeyWords() {
		searchFoundContents = TextBuddy.searchData("university singapore", fileName);
		assertFalse(searchFoundContents.contains("national university of singapore"));
		assertFalse(searchFoundContents.contains("nanyang technological university"));
		assertFalse(searchFoundContents.contains("singapore management university"));
		
		searchFoundContents = TextBuddy.searchData("national uni", fileName);
		assertTrue(searchFoundContents.contains("national university of singapore"));
	}
	
	@Test
	public void testSortEmpty() {
		TextBuddy.clearData(fileName);
		
		searchFoundContents = TextBuddy.sortData(fileName);
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSortValid() {
		String expected = "[nanyang technological university, national university of singapore, ntu, nus, sim, singapore management university, sit, smu, sutd]";
		searchFoundContents = TextBuddy.sortData(fileName);
		assertEquals(expected, searchFoundContents.toString());
	}
}

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TextBuddyTest {
	
	ArrayList<String> searchFoundContents;

	@Before
	public void setUp() throws Exception {
		TextBuddy.loadFileData(new String[] {"mytextfile.txt"});
		
		TextBuddy.processInputData("add nus");
		TextBuddy.processInputData("add ntu");
		TextBuddy.processInputData("add smu");
		TextBuddy.processInputData("add sutd");
		TextBuddy.processInputData("add sit");
		TextBuddy.processInputData("add sim");
		TextBuddy.processInputData("add national university of singapore");
		TextBuddy.processInputData("add nanyang technological university");
		TextBuddy.processInputData("add singapore management university");
	}

	@After
	public void tearDown() throws Exception {
		//TextBuddy.clearData();
	}
	
	@Test
	public void testSearchEmpty() {
		searchFoundContents = TextBuddy.searchData("");
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSearchNoSuchItem() {
		searchFoundContents = TextBuddy.searchData("ibm");
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSearchValid() {
		searchFoundContents = TextBuddy.searchData("t");
		assertTrue(searchFoundContents.contains("ntu"));
		assertTrue(searchFoundContents.contains("sit"));
		assertTrue(searchFoundContents.contains("sutd"));
		assertFalse(searchFoundContents.contains("nus"));
	}
	
	@Test
	public void testSearchMultipleKeyWords() {
		searchFoundContents = TextBuddy.searchData("university singapore");
		assertFalse(searchFoundContents.contains("national university of singapore"));
		assertFalse(searchFoundContents.contains("nanyang technological university"));
		assertFalse(searchFoundContents.contains("singapore management university"));
		
		searchFoundContents = TextBuddy.searchData("national uni");
		assertTrue(searchFoundContents.contains("national university of singapore"));
	}
	
	@Test
	public void testSortEmpty() {
		TextBuddy.clearData();
		
		searchFoundContents = TextBuddy.sortData();
		assertTrue(searchFoundContents.isEmpty());
	}
	
	@Test
	public void testSortValid() {
		String expected = "[nanyang technological university, national university of singapore, ntu, nus, sim, singapore management university, sit, smu, sutd]";
		searchFoundContents = TextBuddy.sortData();
		assertEquals(expected, searchFoundContents.toString());
	}
}

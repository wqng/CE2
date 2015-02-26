import static org.junit.Assert.*;

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
	}

	@After
	public void tearDown() throws Exception {
		//TextBuddy.clearData();
	}
	
	@Test
	public void test() {
		
	}
}

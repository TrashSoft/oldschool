package trash.oldschool.clojure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClojureRunnerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public static void main(String[] args) throws Exception {
		ClojureRunnerTest tester = new ClojureRunnerTest();
		tester.test();
	}

	@Test
	public void test() throws Exception {
		ClojureRunner.loadAndStartClojureProgram("res/test.clj");
	}

}

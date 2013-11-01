package test.chetan.unittest;

import org.testng.annotations.*;

/**
 * This is an integration test.
 * the maven-surefire-plugin will look for this, as it will pattern match Test.java
 * @author chetanjhaveri
 *
 */
public class SampleTest {

	@Test
	public void helloWorldTest() {
		System.out.println("happy run for unit test!");
	}
}

package test.chetan.integrationtest;
import org.testng.annotations.*;

/**
 * This is an integration test.
 * the maven-failsafe-plugin will look for this, as it will pattern match IT.java
 * @author chetanjhaveri
 *
 */
public class SampleTestIT {

	@Test
	public void helloWorldTest() {
		System.out.println("happy run for integration test!");
	}
}

package test.chetan.unittest;

import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This is a unit test.
 * the maven-surefire-plugin will look for this, as it will pattern match Test.java
 * @author chetanjhaveri
 *
 */
public class SampleTest {
	
	private ArrayList<String> isSame = new ArrayList<String>();
	
	@BeforeTest
	public void bringUp() {
		System.out.println("This ran in thread:" + Thread.currentThread().getId());
		System.out.println("Size of list: " + isSame.size());
		for (String line: isSame) {
			System.out.println(line);
		}
	}
	
	@DataProvider(name="stocksToTest", parallel = false)
	private Object [] [] stocksToTest() {
		System.out.println("data provider created, thread name:" + Thread.currentThread().getId());
		return new Object [] [] {
				{"AAPL","Apple Corp"},
				{"IBM","IBM Corp"},
				{"NFLX","Netflix"},
				
		};
	} 

	@Test (invocationCount = 4, threadPoolSize=4, dataProvider = "stocksToTest")
	public void helloWorldTest(String ticker, String stockName) {
		System.out.println("Size of list: " + isSame.size() + " before");
		isSame.add("Thread id " + Thread.currentThread().getId() + " has added this, argument:" + stockName);
		System.out.println("Size of list: " + isSame.size() + " after");
		//System.out.println(Thread.currentThread().getName());
		//System.out.println("happy run for unit test!");
	}
	
	@AfterTest
	public void tearDown() {
		System.out.println("This ran in thread:" + Thread.currentThread().getId());
		System.out.println("Size of list: " + isSame.size());
		for (String line: isSame) {
			System.out.println(line);
		}
	}
}

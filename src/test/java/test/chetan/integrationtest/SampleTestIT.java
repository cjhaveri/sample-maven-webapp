package test.chetan.integrationtest;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import test.chetan.dto.StockDTO;

/**
 * This is an integration test. the maven-failsafe-plugin will look for this, as
 * it will pattern match IT.java
 * 
 * @author chetanjhaveri
 * 
 */
public class SampleTestIT extends SampleTestITSuper {

	private Logger logger = LoggerFactory.getLogger(SampleTestIT.class);



	@DataProvider(name = "stocksToTest")
	private Object[][] stocksToTest() {
		return new Object[][] {
				{ "AAPL", "Apple Corp", "I love apple let it pass", true },
				{
						"IBM",
						"IBM Corp",
						"They are now sucking. I am going to make this so long it will fail",
						false },
				{ "NFLX", "Netflix", "8 bucks is a great price", true },

		};
	}


	@Test(enabled = true, dataProvider = "stocksToTest")
	public void createStock(String ticker, String companyName, String comments,
			boolean expectedPass) {
		// create the stock
		WebClient client = createWebClient();
		client.path(stockServicePath + "/stock");
		StockDTO currentTestCase = new StockDTO();
		currentTestCase.setCompanyName(companyName);
		currentTestCase.setTickerSymbol(ticker);
		currentTestCase.setComments(comments);
		Response r = client.post(currentTestCase);
		if (expectedPass) {
			Assert.assertTrue(r.getStatus() == 200);
		}
		// now delete the stock

	}

	@Test(enabled = true, dependsOnMethods = "createStock", dataProvider = "stocksToTest")
	public void getStock(String ticker, String companyName, String comments,
			boolean expectedPass) {

		WebClient client = createWebClient();
		client.path(stockServicePath + "/stock/{ticker}", ticker);
		// client.accept("application/json");
		Response r = client.get();
		if (expectedPass) {

			StockDTO stockJustAdded = r.readEntity(StockDTO.class);

			Assert.assertEquals(stockJustAdded.getCompanyName(), companyName);
			Assert.assertEquals(stockJustAdded.getTickerSymbol(), ticker);
		} else {
			Assert.assertNotEquals(r.getStatus(), 200);
		}

	}

	@Test(enabled = true, dependsOnMethods = "getStock", dataProvider = "stocksToTest")
	public void deleteStock(String ticker, String companyName, String comments,
			boolean expectedPass) {

		WebClient deleteClient = createWebClient();
		deleteClient.path(stockServicePath + "/stock/{ticker}", ticker);
		//only delete stocks that were created in the first place
		if (expectedPass) {
			Response responseDelete = deleteClient.delete();
			Assert.assertTrue(responseDelete.getStatus() == 200);
		}

	}

}

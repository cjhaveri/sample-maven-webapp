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
public class SampleTestIT {

	private Logger logger = LoggerFactory.getLogger(SampleTestIT.class);

	private String stockServicePath = "sample-web/service/stockservice";
	
	@DataProvider(name="stocksToTest")
	private Object [] [] stocksToTest() {
		return new Object [] [] {
				{"AAPL","Apple Corp"},
				{"IBM","IBM Corp"},
				{"NFLX","Netflix"},
				
		};
	}
	
	private WebClient createWebClient() {
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		ArrayList<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
		providers.add(jsonProvider);
		WebClient client = WebClient.create("http://localhost:8080", providers);
		return client;
	}


	@Test(enabled = true, dataProvider="stocksToTest")
	public void createStock(String ticker, String companyName) {
		// create the stock
		WebClient client = createWebClient();
		client.path(stockServicePath + "/stock");
		StockDTO currentTestCase = new StockDTO();
		currentTestCase.setCompanyName(companyName);
		currentTestCase.setTickerSymbol(ticker);
		Response r = client.post(currentTestCase);
		Assert.assertTrue(r.getStatus() == 200);

		// now delete the stock
		
	}

	@Test(enabled = true, dependsOnMethods = "createStock", dataProvider="stocksToTest")
	public void getStock(String ticker, String companyName) {
		
		
		WebClient client = createWebClient();
		client.path(stockServicePath + "/stock/{ticker}", ticker);
		// client.accept("application/json");
		Response r = client.get();
		StockDTO stockJustAdded = r.readEntity(StockDTO.class);

		
		
		Assert.assertEquals(stockJustAdded.getCompanyName(),
				companyName);
		Assert.assertEquals(stockJustAdded.getTickerSymbol(),
				ticker);

	}
	
	@Test(enabled=true, dependsOnMethods="getStock", dataProvider="stocksToTest") 
	public void deleteStock(String ticker, String companyName) {
		
		WebClient deleteClient = createWebClient();
		deleteClient.path(stockServicePath + "/stock/{ticker}",
			ticker);
		Response responseDelete = deleteClient.delete();
		Assert.assertTrue(responseDelete.getStatus() == 200);

	}
	

}

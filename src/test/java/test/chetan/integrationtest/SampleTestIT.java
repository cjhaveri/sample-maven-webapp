package test.chetan.integrationtest;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.*;

import test.chetan.model.Stock;

/**
 * This is an integration test.
 * the maven-failsafe-plugin will look for this, as it will pattern match IT.java
 * @author chetanjhaveri
 *
 */
public class SampleTestIT {

	@Test
	public void helloWorldTest() {
		
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		ArrayList<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
		providers.add(jsonProvider);
		
		WebClient client = WebClient.create("http://localhost:8080",providers);
		client.path("sample-web/service/stockservice/stock/{id}","1");
		//client.accept("application/json");
		Response r = client.get();
		Stock stock = r.readEntity(Stock.class);
		Assert.assertNotNull(stock);
		
	}
}

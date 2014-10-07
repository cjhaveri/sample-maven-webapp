package test.chetan.endpoint;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.testng.annotations.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import test.chetan.dto.StockDTO;
import test.chetan.endpoint.StockServiceEndpoint;
import test.chetan.model.Stock;
import test.chetan.repository.CommentsRepository;
import test.chetan.repository.MiscRepository;
import test.chetan.repository.StockRepository;

/**
 * This is an integration test. the maven-surefire-plugin will look for this, as
 * it will pattern match Test.java
 * 
 * @author chetanjhaveri
 * 
 */
public class HelloWorldTest {

	@Test
	public void testStockWithEmptyFields() {

		StockDTO crappyStock = new StockDTO();
		StockServiceEndpoint endPoint = new StockServiceEndpoint();
		Response response = endPoint.addStockInformation(crappyStock);

		Assert.assertEquals(response.getStatus(), Response.noContent().build()
				.getStatus());

	}

	@Test
	public void testStockWithPopulatedFields() {

		StockDTO goodStock = new StockDTO();
		goodStock.setCompanyName("IBM Corporation");
		goodStock.setTickerSymbol("IBM");
		goodStock.setComments("Hey Hey");

		Stock mockStock = new Stock();
		mockStock.setCompanyName("IBM Corporation");
		mockStock.setTickerSymbol("IBM");

		StockRepository mockRepository = mock(StockRepository.class);

		CommentsRepository commentsRepository = mock(CommentsRepository.class);

		TransactionalMisc misc = mock(TransactionalMisc.class);

		// look into why this is not needed anymore!
		// when(mockRepository.save(any(Stock.class))).thenReturn(mockStock);

		StockServiceEndpoint endPoint = new StockServiceEndpoint();
		// set the mock repository in
		endPoint.stockRepository = mockRepository;
		endPoint.commentRepository = commentsRepository;
		endPoint.misc = misc;

		Response response = endPoint.addStockInformation(goodStock);

		verify(mockRepository, times(1)).save(any(Stock.class));
		Assert.assertEquals(response.getStatus(), Response.ok().build()
				.getStatus());

	}

	@Test
	public void testDelete() {

		StockRepository mockRepository = mock(StockRepository.class);

		StockServiceEndpoint endPoint = new StockServiceEndpoint();
		// set the mock repository in
		endPoint.stockRepository = mockRepository;

		Response response = endPoint.deleteAllStocks();

		verify(mockRepository, times(1)).deleteAllData();
		Assert.assertEquals(response.getStatus(), Response.ok().build()
				.getStatus());

	}
}

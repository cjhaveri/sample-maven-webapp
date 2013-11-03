package test.chetan.endpoint;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.chetan.dto.StockDTO;
import test.chetan.model.Stock;
import test.chetan.repository.StockRepository;


@Service("StockServiceEndpoint")
@Path("/stockservice/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StockServiceEndpoint {
	
	Logger logger = LoggerFactory.getLogger(StockServiceEndpoint.class);
	
	@Autowired
	StockRepository stockRepository;
	
	public StockServiceEndpoint(){
		logger.debug("endpoint class invoked");
	}

	
	@GET
	@Path("/stock/{ticker}")
	public StockDTO getStockInformationById(@PathParam("ticker") String ticker) {
		
		Stock oneStock = stockRepository.findByTickerSymbol(ticker);
		
		//convert it into dto
		StockDTO stockFound = new StockDTO();
		
		if (oneStock != null) {		
			stockFound.setCompanyName(oneStock.getCompanyName());
			stockFound.setTickerSymbol(oneStock.getTickerSymbol());
		}
		return stockFound;
		
	}
	
	@POST
	@Path("/stock")
	public Response addStockInformation(StockDTO stock) {
		
		Stock stockToSave = new Stock();
		
		if (stock.getCompanyName() == null || stock.getCompanyName().equals("")) {
			
			return Response.noContent().build();
		}
		
		if (stock.getTickerSymbol() == null || stock.getTickerSymbol().equals("")) {
			return Response.noContent().build();
		}
		stockToSave.setCompanyName(stock.getCompanyName());
		stockToSave.setTickerSymbol(stock.getTickerSymbol());
		
		
		Stock savedStock = stockRepository.save(stockToSave);
		//logger.debug("saved stock with id: {}", savedStock.getId());
		
		return Response.ok().build();
		
	}
	
	@DELETE
	@Path("/stock/{ticker}")
	public Response deleteStockByName(@PathParam("ticker")String ticker) {
		
		
		Stock stockFound = stockRepository.findByTickerSymbol(ticker);
		if (stockFound != null) {
			stockRepository.delete(stockFound);
		} else {
			return Response.notModified("Stock not found").build();
		}
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/stock")
	public Response deleteAllStocks() {
		
		stockRepository.deleteAllData();
		return Response.ok().build();
	}

	
}

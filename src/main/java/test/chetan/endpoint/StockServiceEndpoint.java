package test.chetan.endpoint;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.GET;
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

//@Service
@Service("StockServiceEndpoint")
@Path("/stockservice/")
//@Produces("application/json")
public class StockServiceEndpoint {
	
	Logger logger = LoggerFactory.getLogger(StockServiceEndpoint.class);
	
	@Autowired
	StockRepository stockRepository;
	
	public StockServiceEndpoint(){
		logger.debug("endpoint class invoked");
	}
	
	@GET
	public StockDTO getStockInformation() {
		
Stock oneStock = stockRepository.findOne(1);
		
		//convert it into dto
		StockDTO stockFound = new StockDTO();
		
		if (stockFound != null) {		
			stockFound.setId(oneStock.getId());
			stockFound.setCompanyName(oneStock.getCompanyName());
			stockFound.setTickerSymbol(stockFound.getTickerSymbol());
		}
		return stockFound;
	}
	
	@GET
	@Path("/stock/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public StockDTO getStockInformationById(@PathParam("id") int id) {
		
		Stock oneStock = stockRepository.findOne(id);
		
		//convert it into dto
		StockDTO stockFound = new StockDTO();
		
		if (oneStock != null) {		
			stockFound.setId(oneStock.getId());
			stockFound.setCompanyName(oneStock.getCompanyName());
			stockFound.setTickerSymbol(stockFound.getTickerSymbol());
		}
		return stockFound;
		
	}

	
}

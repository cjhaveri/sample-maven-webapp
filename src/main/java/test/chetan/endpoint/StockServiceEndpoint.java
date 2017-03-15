package test.chetan.endpoint;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import test.chetan.dto.StockDTO;
import test.chetan.logic.DownstreamFourSecondService;
import test.chetan.model.Comments;
import test.chetan.model.Stock;
import test.chetan.repository.CommentsRepository;
import test.chetan.repository.StockRepository;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("StockServiceEndpoint")
@Path("/stockservice/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Transactional
public class StockServiceEndpoint {

    Logger logger = LoggerFactory.getLogger(StockServiceEndpoint.class);

    private AtomicInteger counter = new AtomicInteger();

    @Autowired
    StockRepository stockRepository;

    @Autowired
    CommentsRepository commentRepository;

    @Autowired
    TransactionalMisc misc;

//    @Autowired
//    DownstreamFourSecondService dfs;


    ExecutorService executor1;
    ExecutorService executor2;

    public StockServiceEndpoint() {
        logger.debug("endpoint class invoked");
    }

    @PostConstruct
    public void initialize() {

        executor1 = Executors.newFixedThreadPool(20);
        executor2 = Executors.newFixedThreadPool(20);
    }


    @PreDestroy
    public void tearDown() {
        executor1.shutdown();
        executor2.shutdown();
    }

    @GET
    @Path("/stock/{ticker}")
    public Response getStockInformationById(@PathParam("ticker") String ticker) {

        Stock oneStock = stockRepository.findByTickerSymbol(ticker);

        //convert it into dto
        StockDTO stockFound = new StockDTO();

        if (oneStock != null) {
            stockFound.setCompanyName(oneStock.getCompanyName());
            stockFound.setTickerSymbol(oneStock.getTickerSymbol());
            stockFound.setComments(Integer.toString(oneStock.getComments().size()));
            java.util.Iterator<Comments> it = oneStock.getComments().iterator();
            while (it.hasNext()) {
                logger.info("comment: {}", it.next().getComments());
            }
            return Response.ok(stockFound).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }


    }

    @POST
    @Path("/stock")
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.NESTED)
    public Response addStockInformation(StockDTO stock) {

        Stock stockToSave = new Stock();

        if (stock.getCompanyName() == null || stock.getCompanyName().equals("")) {

            return Response.noContent().build();
        }

        if (stock.getTickerSymbol() == null
                || stock.getTickerSymbol().equals("")) {
            return Response.noContent().build();
        }
        stockToSave.setCompanyName(stock.getCompanyName());
        stockToSave.setTickerSymbol(stock.getTickerSymbol());

        Stock saved = stockRepository.save(stockToSave);

        misc.saveMisc("1234");

        Comments comment = new Comments();
        comment.setComments(stock.getComments());
        comment.setStockId(saved);

        // comment.setComments("this comment is definitely greater than 25 characters long for sure!!!!!!!");
        commentRepository.save(comment);

        // logger.debug("saved stock with id: {}", savedStock.getId());

        return Response.ok().build();

    }


    @PUT
    @Path("/stock")
    @Transactional(rollbackFor = {Exception.class})
    public Response updateStockInformation(StockDTO stock) {


        Stock savedStock = stockRepository.findByTickerSymbol(stock.getTickerSymbol());

        if (savedStock == null) {

            return Response.notModified("Stock not found").build();
        }


        savedStock.setCompanyName(stock.getCompanyName());

        stockRepository.save(savedStock);

        return Response.ok().build();

    }


    @DELETE
    @Path("/stock/{ticker}")
    public Response deleteStockByName(@PathParam("ticker") String ticker) {

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

    @POST
    @Path("/nio-test")
    public Response nioTest() {


        DownstreamFourSecondService dfs = new DownstreamFourSecondService();

//        if (counter.incrementAndGet() % 2 == 0) {
//
//            logger.info("got request even");
//            Future<String> submit = executor1.submit(dfs);
//
//            try {
//                String test = submit.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        } else {
//            logger.info("got request odd");
//            Future <String> submit = executor2.submit(dfs);
//            try {
//                String test = submit.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        CompletableFuture<String> one = CompletableFuture.supplyAsync(() -> {
            try {
                logger.info("starting first sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return counter.incrementAndGet();

        },executor2).thenApplyAsync((s1) -> {

            try {
                logger.info("starting second sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "done " + s1;

        },executor2);


        try {
            String finalAnswer = one.get();
            logger.info("final answer {}", finalAnswer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        logger.info("returning out");


        return Response.ok().build();

    }
}

package test.chetan.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chetanjhaveri on 3/8/17.
 */
//@Service
public class DownstreamFourSecondService implements Callable<String> {

    Logger logger = LoggerFactory.getLogger(DownstreamFourSecondService.class);

    /**
     * questions: does this service need to implement callable?
     */


    //TODO: Look at completable future

    //@Async("threadPoolTaskExecutor")
    public Future<String> callDownstreamService() {

        logger.info("before sleep");

        String test = "chetan";

        try {
            Thread.sleep(5000);
            logger.info("after sleep");
            return new AsyncResult<String>(test);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * call downstream service which will take 4 seconds to respond
         * use the async rest template from Spring to perform the operation
         *
         */
        logger.info("returning null");

        return null;
    }


    public String call() throws ExecutionException, InterruptedException {

        logger.info("before sleep");

        String test = "chetan";

        try {
            Thread.sleep(5000);
            logger.info("after sleep");
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * call downstream service which will take 4 seconds to respond
         * use the async rest template from Spring to perform the operation
         *
         */
        logger.info("returning chetan");

        return test;
    }

}

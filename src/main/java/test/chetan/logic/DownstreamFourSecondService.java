package test.chetan.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chetanjhaveri on 3/8/17.
 */
@Service
public class DownstreamFourSecondService{

    Logger logger = LoggerFactory.getLogger(DownstreamFourSecondService.class);



    public String callService(String payload, String id) {


        int i = 0;
        try {
            i = SecureRandom.getInstanceStrong().nextInt() % 5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (i < 0) {
            i = 0 - i;
        }

        try {
            logger.info("Thread id: {}, ID: {}, going to sleep for {} seconds,", Thread.currentThread().getId(), id, i);
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "successful response";

    }

}

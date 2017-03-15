package test.chetan.integrationtest;

import ch.qos.logback.classic.Level;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

/**
 * Created by chetanjhaveri on 3/7/17.
 */
public class MultiThreadedClientTestIT extends SampleTestITSuper {

    long avgTime = 0;

    Logger logger = LoggerFactory.getLogger(MultiThreadedClientTestIT.class);


    @BeforeTest
    public void initialize() {

        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.apache.cxf.phase.PhaseInterceptorChain")).setLevel(Level.INFO);


    }


    @Test(enabled = true, threadPoolSize = 20, invocationCount = 20)
    public void multiThreadedTest() throws Exception {
        try {

            logger.info(Thread.currentThread().getId() + " Starting sleep operation");
            long startTime = System.currentTimeMillis();

            WebClient client = createWebClient();
            client.path(stockServicePath + "/nio-test");

            Response r = client.post(null);

            Assert.assertEquals(r.getStatus(), 200);
            // now delete the stock

            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            logger.info(Thread.currentThread().getId() + " Ending sleep operation");
            //System.out.println("time taken: " + Thread.currentThread().getId() + ":" + (timeTaken));
            avgTime += timeTaken;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @AfterClass
//    public void tearDown() {
//        System.out.println("Total time: " + avgTime + " Avg time: " + avgTime / 20);
//    }
}

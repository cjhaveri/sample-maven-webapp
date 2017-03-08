package test.chetan.integrationtest;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by chetanjhaveri on 3/8/17.
 */
@Test
public class SampleTestITSuper {

    protected String stockServicePath = "sample-web/service/stockservice";

    protected static WebClient createWebClient() {
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        ArrayList<JacksonJaxbJsonProvider> providers = new ArrayList<JacksonJaxbJsonProvider>();
        providers.add(jsonProvider);
        WebClient client = WebClient.create("http://localhost:8080", providers);
        return client;
    }

}

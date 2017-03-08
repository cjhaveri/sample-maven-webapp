package test.chetan.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.backportconcurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Created by chetanjhaveri on 3/8/17.
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig {
//
//    @Bean(name="threadPoolTaskExecutor")
//    public Executor threadPoolTaskExecutor() {
//        return new ThreadPoolTaskExecutor();
//    }
}

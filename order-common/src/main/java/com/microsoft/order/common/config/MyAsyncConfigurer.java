package com.microsoft.order.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhousongbai
 *
 * 通过实现AsyncConfigurer自定义异常线程池，包含异常处理
 *
 * 处理流程
 * 当一个任务被提交到线程池时，首先查看线程池的核心线程是否都在执行任务，否就选择一条线程执行任务，是就执行第二步。
 * 查看核心线程池是否已满，不满就创建一条线程执行任务，否则执行第三步。
 * 查看任务队列是否已满，不满就将任务存储在任务队列中，否则执行第四步。
 * 查看线程池是否已满，不满就创建一条线程执行任务，否则就按照策略处理无法执行的任务。
 * 在ThreadPoolExecutor中表现为:
 *
 * 如果当前运行的线程数小于corePoolSize，那么就创建线程来执行任务（执行时需要获取全局锁）。
 * 如果运行的线程大于或等于corePoolSize，那么就把task加入BlockQueue。
 * 如果创建的线程数量大于BlockQueue的最大容量，那么创建新线程来执行该任务。
 * 如果创建线程导致当前运行的线程数超过maximumPoolSize，就根据饱和策略来拒绝该任务
 */
@Component
@EnableAsync
public class MyAsyncConfigurer implements AsyncConfigurer {
    private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(4);
        //最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(8);

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60 * 15);

        //缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(200);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(180);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("MyAsync-");

        /**
         * AbortPolicy，用于被拒绝任务的处理程序，它将抛出RejectedExecutionException。
         * CallerRunsPolicy，用于被拒绝任务的处理程序，它直接在execute方法的调用线程中运行被拒绝的任务。
         * DiscardOldestPolicy，用于被拒绝任务的处理程序，它放弃最旧的未处理请求，然后重试execute。
         * DiscardPolicy，用于被拒绝任务的处理程序，默认情况下它将丢弃被拒绝的任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     *
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.info("Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : obj) {
                log.info("Parameter value - " + param);
            }
        }
    }
}


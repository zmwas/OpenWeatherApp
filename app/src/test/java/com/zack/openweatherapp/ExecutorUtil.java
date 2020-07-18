package com.zack.openweatherapp;

import org.mockito.stubbing.Answer;

import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;


public class ExecutorUtil {
    public static void implementAsDirectExecutor(Executor executor) {
        doAnswer((Answer<Object>) invocation -> {
            invocation.getArguments();
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(executor).execute(any(Runnable.class));
    }

}

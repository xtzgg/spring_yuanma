package com.spring.mvc.controller;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DeferredResultQueue  {

    private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedDeque<DeferredResult<Object>>();

    public static void save(DeferredResult<Object> deferredResult){
            queue.add(deferredResult);
    }
    public static DeferredResult<Object> get(){
        return queue.poll();
    }
}

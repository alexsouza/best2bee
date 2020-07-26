package com.xyz.orderapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xyz.orderapp.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/emitter")
public class SseController {

    private CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(SseController.class);

    @GetMapping("/order")
    public SseEmitter handle(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");

        // SseEmitter emitter = new SseEmitter();
        SseEmitter emitter = new SseEmitter(180_000L);

        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;
    }

    @EventListener
    public void onOrderUpdate(Order order) {
        LOGGER.info(String.format("Receiveing object event ==> %s", order));
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(order.getStatus());

                // close connnection, browser automatically reconnects
                emitter.complete();

                // SseEventBuilder builder = SseEmitter.event().name("second").data("1");
                // SseEventBuilder builder =
                // SseEmitter.event().reconnectTime(10_000L).data(memoryInfo).id("1");
                // emitter.send(builder);
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(deadEmitters);
    }
}
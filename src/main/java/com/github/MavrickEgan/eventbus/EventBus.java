package com.github.MavrickEgan.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {
    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void post(Object event) {
        List<Consumer<?>> consumerList = listeners.get(event.getClass());
        if (consumerList != null) {
            for (Consumer<?> consumer : consumerList) {
                ((Consumer<Object>) consumer).accept(event);
            }
        }
    }
}
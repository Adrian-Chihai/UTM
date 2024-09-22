package org.example.broker;

import org.example.common.Payload;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PayloadStorage {
    private static final ConcurrentLinkedQueue<Payload> queuePayload = new ConcurrentLinkedQueue<Payload>();

    public static void add(Payload payload) {
        queuePayload.add(payload);
    }

    public static Payload getNext() {
        return queuePayload.poll();
    }

    public static boolean isEmpty() {
        return queuePayload.isEmpty();
    }
}

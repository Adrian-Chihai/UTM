package org.example.common;

public class ConnectionInfo<T> {
    public final int BUFF_LEN = 1024;
    public T socket;
    public String address;
    public String topic;
    public byte[] data;

    public ConnectionInfo(int len) {
        data = new byte[len];
    }

    public T getSocket() {
        return socket;
    }

    public void setSocket(T socket) {
        this.socket = socket;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

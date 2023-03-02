package com.web.api.dto;

import java.util.ArrayList;
import java.util.List;

// Class ini  untuk mengecek apakah data yang diinputkan sudah terisi dengan benar atau belum
public class ResponData<T> {
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private T payload;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}

package com.tecdesoftware.market.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {
    private int purchaseId;
    private String clientId;
    private LocalDateTime date;
    private String paymentMethod;
    private String comment;
    private String state;
    private List<PurchaseItem> items;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // CORRECCIÓN: Getter corregido - era getEstate() debería ser getState()
    public String getState() {
        return state;
    }

    // CORRECCIÓN: Setter corregido - era setEstate() debería ser setState()
    public void setState(String state) {
        this.state = state;
    }

    // CORRECCIÓN: Getter corregido - era getItem() debería ser getItems() para ser consistente
    public List<PurchaseItem> getItems() {
        return items;
    }

    // CORRECCIÓN: Setter corregido - era setItem() debería ser setItems() para ser consistente
    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
}
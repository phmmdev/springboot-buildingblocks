package com.stacksimplify.restservices.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue
    private Long orderid;
    private String orderdecription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Order() {
    }

    public Order(Long orderid, String orderdecription, User user) {
        this.orderid = orderid;
        this.orderdecription = orderdecription;
        this.user = user;
    }

    public Long getOrderid() {
        return orderid;
    }

    public String getOrderdecription() {
        return orderdecription;
    }

    public User getUser() {
        return user;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public void setOrderdecription(String orderdecription) {
        this.orderdecription = orderdecription;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

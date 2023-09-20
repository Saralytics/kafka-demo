package org.producer.serialization;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {

    private String name;
    private Integer id;
    private String address;
    private Date firstPurchaseDate;

    public Customer(String name, Integer id, String address, Date firstPurchaseDate) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.firstPurchaseDate = firstPurchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getFirstPurchaseDate() {
        return firstPurchaseDate;
    }

    public void setFirstPurchaseDate(Date firstPurchaseDate) {
        this.firstPurchaseDate = firstPurchaseDate;
    }
}

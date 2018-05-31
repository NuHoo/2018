package com.test.model;

public class Texto {
    private String one;
    private Double two;
    private String three;

    public Texto() {
        super();
    }

    public Texto(String one, Double two, String three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public Double getTwo() {
        return two;
    }

    public void setTwo(Double two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }
}

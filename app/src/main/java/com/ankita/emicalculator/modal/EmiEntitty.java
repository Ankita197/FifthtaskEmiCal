package com.ankita.emicalculator.modal;

public class EmiEntitty {
    private int instNo;
    private String date;
    private double loanAmount;
    private double emi;
    private double interest;
    private double principle;
    private boolean wantPrePayment;



    public int getInstNo() {
        return instNo;
    }

    public void setInstNo(int instNo) {
        this.instNo = instNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPrinciple() {
        return principle;
    }

    public void setPrinciple(double principle) {
        this.principle = principle;
    }

    public boolean isWantPrePayment() {
        return wantPrePayment;
    }

    public void setWantPrePayment(boolean wantPrePayment) {
        this.wantPrePayment = wantPrePayment;
    }
}

package com.epam.rd.autocode.assessment.basics.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Client extends User implements Serializable {
    protected BigDecimal balance;
    protected String driverLicense;


    public Client() {
    }


    public Client(long id, String email, String password, String name, BigDecimal balance, String driverLicense) {
        super(id, email, password, name);
        this.balance = balance;
        this.driverLicense = driverLicense;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }


    @Override
    public String toString() {
        return "Client{" +
                super.toString() +
                ", balance=" + balance +
                ", driverLicense='" + driverLicense + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Client client = (Client) obj;
        return Objects.equals(balance, client.balance) &&
                Objects.equals(driverLicense, client.driverLicense);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), balance, driverLicense);
    }
}

package com.epam.rd.autocode.assessment.basics.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Employee extends User implements Serializable {

    protected String phone;
    protected LocalDate dateOfBirth;


    public Employee() {
    }

    public Employee(long id, String email, String password, String name, String phone, LocalDate dateOfBirth) {
        super(id, email, password, name);
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        if (!super.equals(obj)) return false;
        return
                Objects.equals(phone, employee.phone) &&
                        Objects.equals(dateOfBirth, employee.dateOfBirth);


    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, dateOfBirth);
    }


}
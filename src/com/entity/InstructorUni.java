package com.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "instructor")
public class InstructorUni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetailUni instructorDetailUni;

    public int getId() {
        return id;
    }

    public InstructorDetailUni getInstructorDetailUni() {
        return instructorDetailUni;
    }

    public void setInstructorDetailUni(InstructorDetailUni instructorDetailUni) {
        this.instructorDetailUni = instructorDetailUni;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public InstructorUni(String lastName, String firstName, String email) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public InstructorUni() {

    }

    @Override
    public String toString() {
        return "InstructorUni{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", instructorDetailUni=" + instructorDetailUni +
                '}';
    }
}

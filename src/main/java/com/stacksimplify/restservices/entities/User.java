package com.stacksimplify.restservices.entities;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.RepresentationModel;

import javax.swing.text.View;
import javax.validation.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name ="user")
// @JsonIgnoreProperties({"firstname", "lastname"}) -- just a example to how to implement static filters
//@JsonFilter(value="userFilter")
public class User extends RepresentationModel {

    @Id
    @GeneratedValue
    @JsonView(Views.External.class)
    private Long userid;

    @NotEmpty(message="UserName is mandatory field. Please provide username.")
    @Column(name = "USER_NAME", length = 50, nullable = false, unique = true)
    @JsonView(Views.External.class)
    private String username;

    @Size(min=2, message="FirstName should have  at least 2 characters.")
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    @JsonView(Views.External.class)
    private  String firstname;

    @Column(name = "LAST_NAME", length = 50, nullable = false)
    @JsonView(Views.External.class)
    private  String lastname;

    @Column(name = "EMAIL_ADDRESS", length = 50, nullable = false)
    @JsonView(Views.External.class)
    private  String email;

    @Column(name = "ROLE", length = 50, nullable = false)
    @JsonView(Views.Internal.class)
    private  String role;

    @Column(name = "SSN", length = 50, nullable = false, unique = true)
    @JsonView(Views.Internal.class)
    private  String ssn;

    @OneToMany(mappedBy = "user")
    @JsonView(Views.External.class)
    private List<Order> orders;

    public User() {
    }

    public User(Long userid, String username, String firstname, String lastname, String email, String role, String ssn) {
        this.userid = userid;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.ssn = ssn;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getSsn() {
        return ssn;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "  userid=" + userid +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}

package com.fyc.crm.pojo;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_customer")
@XmlRootElement
public class Customer implements java.io.Serializable {

    private Integer id;
    private String username;
    private String password;
    private String type;
    private String sex;
    private String telephone;
    private String address;
    private String email;
    private Integer fixedAreaId;

    // Constructors

    /**
     * default constructor
     */
    public Customer() {
    }

    /**
     * full constructor
     */
    public Customer(String username, String password, String type, String sex,
                    String telephone, String address, String email, Integer fixedAreaId) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.sex = sex;
        this.telephone = telephone;
        this.address = address;
        this.email = email;
        this.fixedAreaId = fixedAreaId;
    }

    // Property accessors

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "TYPE")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "SEX")
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "FIXED_AREA_ID")
    public Integer getFixedAreaId() {
        return this.fixedAreaId;
    }

    public void setFixedAreaId(Integer fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }
}

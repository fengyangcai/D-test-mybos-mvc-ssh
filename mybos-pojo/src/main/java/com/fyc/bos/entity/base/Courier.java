package com.fyc.bos.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_courier", schema = "newbos", catalog = "")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fixedAreas"})
public class Courier {
    private Integer id;
    private String courierNum;
    private String name;
    private String telephone;
    private String pda;
    private String checkPwd;
    private String company;
    private Standard standard;
    private Set<FixedArea> fixedAreas =new HashSet<FixedArea>(0);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "courier_num")
    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "pda")
    public String getPda() {
        return pda;
    }

    public void setPda(String pda) {
        this.pda = pda;
    }

    @Basic
    @Column(name = "check_pwd")
    public String getCheckPwd() {
        return checkPwd;
    }

    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }

    @Basic
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(id, courier.id) &&
                Objects.equals(courierNum, courier.courierNum) &&
                Objects.equals(name, courier.name) &&
                Objects.equals(telephone, courier.telephone) &&
                Objects.equals(pda, courier.pda) &&
                Objects.equals(checkPwd, courier.checkPwd) &&
                Objects.equals(company, courier.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courierNum, name, telephone, pda, checkPwd, company);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", courierNum='" + courierNum + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", pda='" + pda + '\'' +
                ", checkPwd='" + checkPwd + '\'' +
                ", company='" + company + '\'' +
                ", standard=" + standard +
                ", fixedAreas=" + fixedAreas +
                '}';
    }

    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "standard_id", referencedColumnName = "id")
    @JoinColumn(name = "standard_id", nullable=false)
    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy = "courier")
    public Set<FixedArea> getFixedAreas() {
        return fixedAreas;
    }

    public void setFixedAreas(Set<FixedArea> fixedAreas) {
        this.fixedAreas = fixedAreas;
    }
}

package com.fyc.bos.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_fixed_area", schema = "newbos", catalog = "")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","subAreas"})
public class FixedArea {
    private Integer id;
    private String fixedAreaName;
    private String telephone;
    private Courier courier;
    private Set<SubArea> subAreas;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fixed_area_name")
    public String getFixedAreaName() {
        return fixedAreaName;
    }

    public void setFixedAreaName(String fixedAreaName) {
        this.fixedAreaName = fixedAreaName;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedArea fixedArea = (FixedArea) o;
        return Objects.equals(id, fixedArea.id) &&
                Objects.equals(fixedAreaName, fixedArea.fixedAreaName) &&
                Objects.equals(telephone, fixedArea.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fixedAreaName, telephone);
    }

    @ManyToOne
    @JoinColumn(name = "fixed_area_leader", referencedColumnName = "id")
    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy = "fixedarea")
    public Set<SubArea> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(Set<SubArea> subAreas) {
        this.subAreas = subAreas;
    }
}

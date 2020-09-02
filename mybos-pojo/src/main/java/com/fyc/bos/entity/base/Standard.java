package com.fyc.bos.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_standard", schema = "newbos", catalog = "")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","couriers"})
public class Standard {
    private Integer id;
    private String name;
    private String minWeight;
    private String maxWeight;
    private String minLength;
    private String maxLength;
    private Set<Courier> couriers;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "min_weight")
    public String getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(String minWeight) {
        this.minWeight = minWeight;
    }

    @Basic
    @Column(name = "max_weight")
    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Basic
    @Column(name = "min_length")
    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    @Basic
    @Column(name = "max_length")
    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Standard standard = (Standard) o;
        return Objects.equals(id, standard.id) &&
                Objects.equals(name, standard.name) &&
                Objects.equals(minWeight, standard.minWeight) &&
                Objects.equals(maxWeight, standard.maxWeight) &&
                Objects.equals(minLength, standard.minLength) &&
                Objects.equals(maxLength, standard.maxLength);
    }

    @Override
    public String toString() {
        return "Standard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minWeight='" + minWeight + '\'' +
                ", maxWeight='" + maxWeight + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", couriers=" + couriers +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minWeight, maxWeight, minLength, maxLength);
    }

    @OneToMany(cascade=CascadeType.ALL, fetch= FetchType.LAZY,mappedBy = "standard")
    public Set<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
    }
}

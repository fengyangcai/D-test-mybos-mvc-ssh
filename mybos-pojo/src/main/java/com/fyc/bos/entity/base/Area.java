package com.fyc.bos.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_area", schema = "newbos")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","subAreas"})
public class Area {
    private Integer id;
    private String areacode;
    private String province;
    private String city;
    private String distrcit;
    private String postcode;
    private String citycode;
    private String shortcode;
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
    @Column(name = "areacode")
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "distrcit")
    public String getDistrcit() {
        return distrcit;
    }

    public void setDistrcit(String distrcit) {
        this.distrcit = distrcit;
    }

    @Basic
    @Column(name = "postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "citycode")
    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    @Basic
    @Column(name = "shortcode")
    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(id, area.id) &&
                Objects.equals(areacode, area.areacode) &&
                Objects.equals(province, area.province) &&
                Objects.equals(city, area.city) &&
                Objects.equals(distrcit, area.distrcit) &&
                Objects.equals(postcode, area.postcode) &&
                Objects.equals(citycode, area.citycode) &&
                Objects.equals(shortcode, area.shortcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areacode, province, city, distrcit, postcode, citycode, shortcode);
    }

    @OneToMany(mappedBy = "area")
    public Set<SubArea> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(Set<SubArea> subAreas) {
        this.subAreas = subAreas;
    }
}

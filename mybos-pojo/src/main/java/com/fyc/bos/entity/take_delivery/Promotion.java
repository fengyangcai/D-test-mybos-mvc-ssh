package com.fyc.bos.entity.take_delivery;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_promotion", schema = "newbos", catalog = "")
public class Promotion {
    private Integer id;
    private String title;
    private String titleImg;
    private String activeScope;
    private Date startDate;
    private Date endDate;
    private String status;
    private String descrption;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "title_img")
    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    @Basic
    @Column(name = "active_scope")
    public String getActiveScope() {
        return activeScope;
    }

    public void setActiveScope(String activeScope) {
        this.activeScope = activeScope;
    }

    @Basic
    @Column(name = "start_date")
    public Date  getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date  endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "descrption")
    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(id, promotion.id) &&
                Objects.equals(title, promotion.title) &&
                Objects.equals(titleImg, promotion.titleImg) &&
                Objects.equals(activeScope, promotion.activeScope) &&
                Objects.equals(startDate, promotion.startDate) &&
                Objects.equals(endDate, promotion.endDate) &&
                Objects.equals(status, promotion.status) &&
                Objects.equals(descrption, promotion.descrption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, titleImg, activeScope, startDate, endDate, status, descrption);
    }
}

package com.fyc.bos.entity.take_delivery;

import com.fyc.bos.entity.base.Courier;

import javax.annotation.Generated;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_work_bill", schema = "newbos", catalog = "")
public class WorkBill {
    private Integer id;
    private String pickstate;
    private Date buildtime;
    private String remark;
    private Courier courier;
    private Order order;

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
    @Column(name = "pickstate")
    public String getPickstate() {
        return pickstate;
    }

    public void setPickstate(String pickstate) {
        this.pickstate = pickstate;
    }

    @Basic
    @Column(name = "buildtime")
    public Date getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkBill workBill = (WorkBill) o;
        return Objects.equals(id, workBill.id) &&
                Objects.equals(pickstate, workBill.pickstate) &&
                Objects.equals(buildtime, workBill.buildtime) &&
                Objects.equals(remark, workBill.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pickstate, buildtime, remark);
    }

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

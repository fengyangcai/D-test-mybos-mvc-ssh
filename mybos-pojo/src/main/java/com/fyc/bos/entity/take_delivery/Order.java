package com.fyc.bos.entity.take_delivery;

import com.alibaba.fastjson.annotation.JSONField;
import com.fyc.bos.entity.base.Area;
import com.fyc.bos.entity.base.Courier;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_order", schema = "newbos")
@XmlRootElement(name = "order")
public class Order implements Serializable {
    private Integer id;
    private String orderNum;
    private Integer customerId;
    private String sendName;
    private String sendMobile;
    private String sendCompany;
    private String sendAddress;
    private String recName;
    private String recMobile;
    private String recCompany;
    private String recAddress;
    private String sendProNum;
    private String goodType;
    private String payTypeNum;
    private String weight;
    private String remark;
    private String sendMobileMsg;
    private String orderType;
    private String status;
    private Date orderTime;
    private Courier courier;
    private Area recArea;
    private Area sendArea;
    @JSONField(serialize=false)
    private Set<WayBill> wayBills =new HashSet<WayBill>(0);
    @JSONField(serialize=false)
    private Set<WorkBill> workBills =new HashSet<WorkBill>(0);

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
    @Column(name = "order_num")
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "send_name")
    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    @Basic
    @Column(name = "send_mobile")
    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    @Basic
    @Column(name = "send_company")
    public String getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }

    @Basic
    @Column(name = "send_address")
    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    @Basic
    @Column(name = "rec_name")
    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    @Basic
    @Column(name = "rec_mobile")
    public String getRecMobile() {
        return recMobile;
    }

    public void setRecMobile(String recMobile) {
        this.recMobile = recMobile;
    }

    @Basic
    @Column(name = "rec_company")
    public String getRecCompany() {
        return recCompany;
    }

    public void setRecCompany(String recCompany) {
        this.recCompany = recCompany;
    }

    @Basic
    @Column(name = "rec_address")
    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    @Basic
    @Column(name = "send_pro_num")
    public String getSendProNum() {
        return sendProNum;
    }

    public void setSendProNum(String sendProNum) {
        this.sendProNum = sendProNum;
    }

    @Basic
    @Column(name = "good_type")
    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    @Basic
    @Column(name = "pay_type_num")
    public String getPayTypeNum() {
        return payTypeNum;
    }

    public void setPayTypeNum(String payTypeNum) {
        this.payTypeNum = payTypeNum;
    }

    @Basic
    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "send_mobile_msg")
    public String getSendMobileMsg() {
        return sendMobileMsg;
    }

    public void setSendMobileMsg(String sendMobileMsg) {
        this.sendMobileMsg = sendMobileMsg;
    }

    @Basic
    @Column(name = "order_type")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
    @Column(name = "order_time")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderNum, order.orderNum) &&
                Objects.equals(customerId, order.customerId) &&
                Objects.equals(sendName, order.sendName) &&
                Objects.equals(sendMobile, order.sendMobile) &&
                Objects.equals(sendCompany, order.sendCompany) &&
                Objects.equals(sendAddress, order.sendAddress) &&
                Objects.equals(recName, order.recName) &&
                Objects.equals(recMobile, order.recMobile) &&
                Objects.equals(recCompany, order.recCompany) &&
                Objects.equals(recAddress, order.recAddress) &&
                Objects.equals(sendProNum, order.sendProNum) &&
                Objects.equals(goodType, order.goodType) &&
                Objects.equals(payTypeNum, order.payTypeNum) &&
                Objects.equals(weight, order.weight) &&
                Objects.equals(remark, order.remark) &&
                Objects.equals(sendMobileMsg, order.sendMobileMsg) &&
                Objects.equals(orderType, order.orderType) &&
                Objects.equals(status, order.status) &&
                Objects.equals(orderTime, order.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNum, customerId, sendName, sendMobile, sendCompany, sendAddress, recName, recMobile, recCompany, recAddress, sendProNum, goodType, payTypeNum, weight, remark, sendMobileMsg, orderType, status, orderTime);
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
    @JoinColumn(name = "rec_area_id", referencedColumnName = "id")
    public Area getRecArea() {
        return recArea;
    }

    public void setRecArea(Area recArea) {
        this.recArea = recArea;
    }

    @ManyToOne
    @JoinColumn(name = "send_area_id", referencedColumnName = "id")
    public Area getSendArea() {
        return sendArea;
    }

    public void setSendArea(Area sendArea) {
        this.sendArea = sendArea;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy = "order")
    public Set<WayBill> getWayBills() {
        return wayBills;
    }

    public void setWayBills(Set<WayBill> wayBills) {
        this.wayBills = wayBills;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy = "order")
    public Set<WorkBill> getWorkBills() {
        return workBills;
    }

    public void setWorkBills(Set<WorkBill> workBills) {
        this.workBills = workBills;
    }
}

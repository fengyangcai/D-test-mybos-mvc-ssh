package com.fyc.bos.entity.take_delivery;

import com.fyc.bos.entity.base.Area;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_way_bill", schema = "newbos", catalog = "")
public class WayBill {
    private Integer id;
    private String wayBillNum;
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
    private String arriveCity;
    private String actlweit;
    private String feeitemnum;
    private String vol;
    private String floadreqr;
    private String signStatus;
    private Order order;
    private Area recArea;
    private Area sendArea;

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
    @Column(name = "way_bill_num")
    public String getWayBillNum() {
        return wayBillNum;
    }

    public void setWayBillNum(String wayBillNum) {
        this.wayBillNum = wayBillNum;
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
    @Column(name = "arrive_city")
    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    @Basic
    @Column(name = "actlweit")
    public String getActlweit() {
        return actlweit;
    }

    public void setActlweit(String actlweit) {
        this.actlweit = actlweit;
    }

    @Basic
    @Column(name = "feeitemnum")
    public String getFeeitemnum() {
        return feeitemnum;
    }

    public void setFeeitemnum(String feeitemnum) {
        this.feeitemnum = feeitemnum;
    }

    @Basic
    @Column(name = "vol")
    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    @Basic
    @Column(name = "floadreqr")
    public String getFloadreqr() {
        return floadreqr;
    }

    public void setFloadreqr(String floadreqr) {
        this.floadreqr = floadreqr;
    }

    @Basic
    @Column(name = "sign_status")
    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayBill wayBill = (WayBill) o;
        return Objects.equals(id, wayBill.id) &&
                Objects.equals(wayBillNum, wayBill.wayBillNum) &&
                Objects.equals(sendName, wayBill.sendName) &&
                Objects.equals(sendMobile, wayBill.sendMobile) &&
                Objects.equals(sendCompany, wayBill.sendCompany) &&
                Objects.equals(sendAddress, wayBill.sendAddress) &&
                Objects.equals(recName, wayBill.recName) &&
                Objects.equals(recMobile, wayBill.recMobile) &&
                Objects.equals(recCompany, wayBill.recCompany) &&
                Objects.equals(recAddress, wayBill.recAddress) &&
                Objects.equals(sendProNum, wayBill.sendProNum) &&
                Objects.equals(goodType, wayBill.goodType) &&
                Objects.equals(payTypeNum, wayBill.payTypeNum) &&
                Objects.equals(weight, wayBill.weight) &&
                Objects.equals(remark, wayBill.remark) &&
                Objects.equals(arriveCity, wayBill.arriveCity) &&
                Objects.equals(actlweit, wayBill.actlweit) &&
                Objects.equals(feeitemnum, wayBill.feeitemnum) &&
                Objects.equals(vol, wayBill.vol) &&
                Objects.equals(floadreqr, wayBill.floadreqr) &&
                Objects.equals(signStatus, wayBill.signStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wayBillNum, sendName, sendMobile, sendCompany, sendAddress, recName, recMobile, recCompany, recAddress, sendProNum, goodType, payTypeNum, weight, remark, arriveCity, actlweit, feeitemnum, vol, floadreqr, signStatus);
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
}

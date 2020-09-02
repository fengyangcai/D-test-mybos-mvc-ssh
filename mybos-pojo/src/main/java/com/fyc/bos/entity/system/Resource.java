package com.fyc.bos.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_resource", schema = "newbos")
public class Resource implements Serializable {
    private Integer id;
    private String name; //资源名称（可能菜单名称，可能是按钮）
    private String grantKey;//授权码，给shiro使用的
    private String pageUrl;// 菜单连接，该属性只要菜单才有
    private Integer seq;//排序字段
    private String resourceType;//资源类型，0：菜单  1：按钮......
    private String icon;//菜单图标，该属性只要菜单才有

    //@JSONField(name="_parentId") //转换的时候修改属性名称
    @JsonProperty(value = "_parentId")
    private Integer pid;// 父菜单ID，该属性只要菜单才有用

    private Integer open;//是否展开，该属性只要菜单才有
    private Set<Role> roles = new HashSet<Role>(0);

    public Resource() {}
    public Resource( String name, String grantKey, String pageUrl, Integer seq, String resourceType, String icon, Integer pid, Integer open) {
        this.name = name;
        this.grantKey = grantKey;
        this.pageUrl = pageUrl;
        this.seq = seq;
        this.resourceType = resourceType;
        this.icon = icon;
        this.pid = pid;
        this.open = open;

    }
    //添加checked临时属性
    private Boolean checked=false;
    @Transient
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

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
    @Column(name = "grant_key")
    public String getGrantKey() {
        return grantKey;
    }

    public void setGrantKey(String grantKey) {
        this.grantKey = grantKey;
    }

    @Basic
    @Column(name = "page")
    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @Basic
    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "resource_type")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "open")
    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



 //这里不重写toString方法，重写会导致@JSONField(name="_parentId")获取的时候还是pid;


}

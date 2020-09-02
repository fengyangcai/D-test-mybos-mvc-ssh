package com.fyc.bos.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_role", schema = "newbos")
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String keyword;
    private String description;
    @JSONField(serialize=false)
    private Set<Resource> resources = new HashSet<Resource>(0);
    @JSONField(serialize=false)
    private Set<User> users = new HashSet<User>(0);

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
    @Column(name = "keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, keyword, description);
    }

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_resource",schema = "newbos",joinColumns = {@JoinColumn(name = "role_id",updatable = false)},inverseJoinColumns = {@JoinColumn(name = "resource_id",updatable = false)})
    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    @JsonBackReference
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role(Integer id, String name, String keyword, String description) {
        this.id = id;
        this.name = name;
        this.keyword = keyword;
        this.description = description;
    }

    public Role() {
    }
}

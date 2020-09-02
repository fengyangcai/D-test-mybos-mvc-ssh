package com.fyc.bos.entity.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_sub_area", schema = "newbos", catalog = "")
public class SubArea {
    private Integer id;
    private String startNum;
    private String endNum;
    private String keyWords;
    private String assitKeyWords;
    private Area area;
    private FixedArea fixedarea;

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
    @Column(name = "start_num")
    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    @Basic
    @Column(name = "end_num")
    public String getEndNum() {
        return endNum;
    }

    public void setEndNum(String endNum) {
        this.endNum = endNum;
    }

    @Basic
    @Column(name = "key_words")
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Basic
    @Column(name = "assit_key_words")
    public String getAssitKeyWords() {
        return assitKeyWords;
    }

    public void setAssitKeyWords(String assitKeyWords) {
        this.assitKeyWords = assitKeyWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubArea subArea = (SubArea) o;
        return Objects.equals(id, subArea.id) &&
                Objects.equals(startNum, subArea.startNum) &&
                Objects.equals(endNum, subArea.endNum) &&
                Objects.equals(keyWords, subArea.keyWords) &&
                Objects.equals(assitKeyWords, subArea.assitKeyWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startNum, endNum, keyWords, assitKeyWords);
    }

    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @ManyToOne
    @JoinColumn(name = "fixedarea_id", referencedColumnName = "id")
    public FixedArea getFixedarea() {
        return fixedarea;
    }

    public void setFixedarea(FixedArea fixedarea) {
        this.fixedarea = fixedarea;
    }
}

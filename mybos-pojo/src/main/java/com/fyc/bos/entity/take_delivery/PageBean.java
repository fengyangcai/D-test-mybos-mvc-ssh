package com.fyc.bos.entity.take_delivery;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.List;
@XmlRootElement
@XmlSeeAlso(Promotion.class)
public class PageBean<T> implements Serializable {

    private List<T> content;

    private Long totalCount;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public PageBean() {
    }

    public PageBean(List<T> content, Long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }
}

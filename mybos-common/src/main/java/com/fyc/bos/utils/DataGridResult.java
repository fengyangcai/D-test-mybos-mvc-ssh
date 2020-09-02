package com.fyc.bos.utils;

import java.util.List;

/**
 * @Author: fyc
 * @Date: 2020/4/25 6:50
 */
public class DataGridResult {
    private static final Long serialVersionUID = 2851948860140585972L;

    private Long total;
    private List<?> rows;


    public DataGridResult() {
    }

    public DataGridResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }



    @Override
    public String toString() {
        return "DataGridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}

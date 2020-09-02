package com.fyc.crm.dao;

import com.fyc.bos.dao.BaseDao;
import com.fyc.crm.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends BaseDao<Customer> {

        public List<Customer>findByFixedAreaIdIsNull();

        public List<Customer> findByFixedAreaId(Integer fixedAreaId);

        @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
        @Modifying
        public void clearCustomers(Integer fixedAreaId);

        @Query("update Customer set fixedAreaId=? where id=?")
        @Modifying
        public void bindCustomerToFixedArea(Integer fixedAreadId,Integer custId);

        public Customer findByTelephone(String telephone);

        @Query("update Customer set type='1' where telephone=?")
        @Modifying
        public void activeCustomer(String telephone);


        public Customer findByAddress(String address);
}

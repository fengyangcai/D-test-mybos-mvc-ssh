package com.fyc.crm.service.impl;

import com.fyc.crm.dao.CustomerDao;
import com.fyc.crm.pojo.Customer;
import com.fyc.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer findByAddress(String address) {
        return customerDao.findByAddress(address);
    }

    @Override
    public List<Customer> findByNoAssociateCustomers() {
        return customerDao.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findByHasAssociateCustomers(Integer fixedAreaId) {
        return customerDao.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associateCustomersToFixedArea(Integer fixedAreaId, String custIds) {
        //1.先清理绑定过的定区
        customerDao.clearCustomers(fixedAreaId);
        if (custIds != null && !custIds.trim().equals("")) {
            String[] cstIdArray = custIds.split(",");
            for (String cstId : cstIdArray) {
                customerDao.bindCustomerToFixedArea(fixedAreaId, Integer.valueOf(cstId));
            }
        }

    }

    @Override
    public Customer checkTelephone(String telephone) {
        return customerDao.findByTelephone(telephone);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void activeCustomer(String telephone) {
        customerDao.activeCustomer(telephone);
    }
}

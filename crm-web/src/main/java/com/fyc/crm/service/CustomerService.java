package com.fyc.crm.service;

import com.fyc.crm.pojo.Customer;
import com.fyc.crm.service.impl.CustomerServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface CustomerService {
    /**
     * 根据地址查询客户
     */
    @GET
    @Path("/findByAddress")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer findByAddress(@QueryParam("address")String address);


    /**
     * 查询为关联定区的客户
     */
    @GET
    @Path("/findByNoAssociateCustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> findByNoAssociateCustomers();


    /**
     * 查询已经关联某个定区的客户
     */
    @GET
    @Path("/findByHasAssociateCustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> findByHasAssociateCustomers(@QueryParam("fixedAreaId")Integer fixedAreaId);

    /**
     * 保存定义与客户关系
     */
    @PUT
    @Path("/associateCustomersToFixedArea")
    public void associateCustomersToFixedArea(@QueryParam("fixedAreaId")Integer fixedAreaId,@QueryParam("custIds")String custIds);

    /**
     * 查询手机号的客户
     */
    @GET
    @Path("/checkTelephone")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer checkTelephone(@QueryParam("telephone")String telephone);

    /**
     * 保存客户信息
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCustomer(Customer customer);

    /**
     * 激活客户
     */
    @PUT
    public void activeCustomer(@QueryParam("telephone")String telephone);
}

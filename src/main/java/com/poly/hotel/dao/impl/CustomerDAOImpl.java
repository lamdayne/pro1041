/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.CustomerDAO;
import com.poly.hotel.entity.Customer;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author PHUONG LAM
 */
public class CustomerDAOImpl implements CustomerDAO {
    
    String createSql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Customer SET fullName=?, gender=?, phoneNumber=?, email=?, idNumber=?, address=? WHERE customerID=?";
    String deleteSql = "DELETE FROM Customer WHERE customerID=?";
    String findAllSql = "SELECT * FROM Customer";
    String findByIdSql = "SELECT * FROM Customer WHERE customerId=?";

    @Override
    public Customer create(Customer entity) {
        Object[] values = {
            entity.getCustomerID(),
            entity.getFullName(),
            entity.isGender(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getIdNumber(),
            entity.getAddress()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Customer entity) {
        Object[] values = {
            entity.getFullName(),
            entity.isGender(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getIdNumber(),
            entity.getAddress(),
            entity.getCustomerID()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Customer> findAll() {
        return XQuery.getBeanList(Customer.class, findAllSql);
    }

    @Override
    public Customer findById(String id) {
        return XQuery.getSingleBean(Customer.class, findByIdSql, id);
    }
    
}

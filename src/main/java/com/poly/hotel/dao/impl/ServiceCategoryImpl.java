/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.ServiceCategoryDAO;
import com.poly.hotel.entity.ServiceCategory;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author PC
 */
public class ServiceCategoryImpl implements ServiceCategoryDAO {

    String createSql = "INSERT INTO ServiceCategory VALUES ( ?, ?, ?)";
    String updateSql = "UPDATE ServiceCategory SET ServiceCategoryID=?, CategoryName=?, Desc=?, isActive AS Active=? WHERE ServiceCategoryID=?";
    String deleteSql = "DELETE FROM ServiceCategory WHERE ServiceCategoryID=?";
//    String findAllSql = "SELECT * FROM ServiceCategory";
    String findAllSql = "SELECT serviceCategoryID, categoryName, [desc], isActive AS Active FROM ServiceCategory;";
    String findByIdSql = "SELECT * FROM ServiceCategory WHERE ServiceCategoryID=?";

    @Override
    public ServiceCategory create(ServiceCategory entity) {
        Object[] value = {
           
            entity.getCategoryName(),
            entity.getDesc(),
            entity.isActive()};
        XJdbc.executeUpdate(createSql, value);
        return entity;
    }

    @Override
    public void update(ServiceCategory entity) {
        Object[] value = {
            entity.getCategoryName(),
            entity.getDesc(),
            entity.isActive(),
            entity.getServiceCategoryID(),};

        XJdbc.executeUpdate(updateSql, value);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<ServiceCategory> findAll() {
        return XQuery.getBeanList(ServiceCategory.class, findAllSql);
    }

    @Override
    public ServiceCategory findById(String id) {
        return XQuery.getSingleBean(ServiceCategory.class, findByIdSql, id);
    }

}

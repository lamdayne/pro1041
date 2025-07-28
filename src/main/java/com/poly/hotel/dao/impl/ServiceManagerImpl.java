/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.ServiceDAO;
import com.poly.hotel.entity.Service;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author Lenovo LOQ
 *
 */
public class ServiceManagerImpl implements ServiceDAO {

    String createSql = "INSERT INTO Service VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Service SET serviceName=?, serviceCategoryID=?, price=?, unit=?, description=?, isActive=? WHERE serviceID=?";
    String deleteSql = "DELETE FROM Service WHERE serviceID=?";
    String findAllSql = "SELECT serviceID, serviceName, serviceCategoryID, price, unit, description, isActive AS Active FROM Service";
    String findByIdSql = "SELECT serviceID, serviceName, serviceCategoryID, price, unit, description, isActive AS Active FROM Service WHERE serviceID=?";
    String findByCategoryId = "SELECT serviceID AS ServiceID, serviceName AS ServiceName, serviceCategoryID AS ServiceCategoryID, price AS Price, unit AS Unit, description AS Description, isActive AS Active FROM Service WHERE serviceCategoryID = ?";

    @Override
    public Service create(Service entity) {
        Object[] value = {
            entity.getServiceID(),
            entity.getServiceName(),
            entity.getServiceCategoryID(),
            entity.getPrice(),
            entity.getUnit(),
            entity.getDescription(),
            entity.isActive()
        };

        XJdbc.executeUpdate(createSql, value);
        return entity;
    }

    @Override
    public void update(Service entity) {
        Object[] value = {
            entity.getServiceName(),
            entity.getServiceCategoryID(),
            entity.getPrice(),
            entity.getUnit(),
            entity.getDescription(),
            entity.isActive(),
            entity.getServiceID()
        };
        XJdbc.executeUpdate(updateSql, value);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Service> findAll() {
        return XQuery.getBeanList(Service.class, findAllSql);
    }

    @Override
    public Service findById(String id) {
        return XQuery.getSingleBean(Service.class, findByIdSql, id);
    }
    
    @Override
    public List<Service> findByCategoryId(int categoryId) {
        return XQuery.getBeanList(Service.class, findByCategoryId, categoryId);
    }
}

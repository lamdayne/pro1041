/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.RoomCategoryDAO;
import com.poly.hotel.entity.RoomCategory;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author Windows
 */
public class RoomCategoryDAOImpl implements RoomCategoryDAO{
    String createSql = "INSERT INTO RoomCategory (CategoryName, [desc], baseHourPrice, baseDailyPrice, maxOccupancy, isActive) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE RoomCategory SET CategoryName = ?, [desc] = ?, baseHourPrice = ?, baseDailyPrice = ?, maxOccupancy = ?, isActive = ? WHERE CategoryID = ?";
    String deleteSql = "DELETE FROM RoomCategory WHERE CategoryID = ?";
    String findAllSql = "SELECT CategoryID, CategoryName, [desc], baseHourPrice AS BaseHourPrice, baseDailyPrice AS BaseDailyPrice, maxOccupancy, isActive AS Active FROM RoomCategory";
    String findByIdSql = "SELECT CategoryID, CategoryName, [desc], baseHourPrice AS BaseHourPrice, baseDailyPrice AS BaseDailyPrice, maxOccupancy, isActive AS Active FROM RoomCategory WHERE CategoryID = ?";

    @Override
    public RoomCategory create(RoomCategory entity) {
        Object[] values = { 
            entity.getCategoryName(),
            entity.getDesc(),
            entity.getBaseHourPrice(),
            entity.getBaseDailyPrice(),
            entity.getMaxOccupancy(),
            entity.isActive()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(RoomCategory entity) {
        Object[] values = {       
            entity.getCategoryName(),
            entity.getDesc(),
            entity.getBaseHourPrice(),
            entity.getBaseDailyPrice(),
            entity.getMaxOccupancy(),
            entity.isActive(),
            entity.getCategoryID()

        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String CategoryID) {
        XJdbc.executeUpdate(deleteSql, CategoryID);
    }

    @Override
    public List<RoomCategory> findAll() {
        return XQuery.getBeanList(RoomCategory.class, findAllSql);
    }

    @Override
    public RoomCategory findById(String CategoryID) {
        return XQuery.getSingleBean(RoomCategory.class, findByIdSql, CategoryID);
    }
}

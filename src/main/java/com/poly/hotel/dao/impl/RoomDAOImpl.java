/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.entity.Room;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;
import com.poly.hotel.dao.RoomDAO;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author PHUONG LAM
 */
public class RoomDAOImpl implements RoomDAO {

    String createSql = "INSERT INTO Room (roomID, categoryID, floor, status, [desc], isActive AS Active) VALUES (?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Room SET categoryID=?, floor=?, status=?, [desc]=?, isActive=? WHERE roomID=?";
    String deleteSql = "DELETE FROM Room WHERE roomID=?";
    String findAllSql = "SELECT roomID, categoryID, floor, status, [desc], isActive AS Active FROM Room";
    String findByIdSql = "SELECT categoryID, floor, status, [desc], isActive AS Active FROM Room WHERE roomID=?";

    @Override
    public Room create(Room entity) {
        Object[] values = {
            entity.getRoomID(),
            entity.getCategoryID(),
            entity.getFloor(),
            entity.getStatus(),
            entity.getDesc(),
            entity.isActive()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Room entity) {
        Object[] values = {
            entity.getCategoryID(),
            entity.getFloor(),
            entity.getStatus(),
            entity.getDesc(),
            entity.isActive(),
            entity.getRoomID()

        };
        XJdbc.executeUpdate(updateSql, values);
    }

    public List<Integer> findDistinctFloors() {
        List<Integer> floors = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery("SELECT DISTINCT floor FROM Room WHERE isActive = 1 ORDER BY floor")) {
            while (rs.next()) {
                floors.add(rs.getInt("floor"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return floors;
    }

    public List<String> findDistinctStatuses() {
        List<String> statuses = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery("SELECT DISTINCT status FROM Room WHERE isActive = 1 ORDER BY status")) {
            while (rs.next()) {
                statuses.add(rs.getString("status"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return statuses;
    }

    @Override
    public void deleteById(String roomID) {
        XJdbc.executeUpdate(deleteSql, roomID);
    }

    @Override
    public List<Room> findAll() {
        return XQuery.getBeanList(Room.class, findAllSql);
    }

    @Override
    public Room findById(String roomID) {
        return XQuery.getSingleBean(Room.class, findByIdSql, roomID);
    }
}

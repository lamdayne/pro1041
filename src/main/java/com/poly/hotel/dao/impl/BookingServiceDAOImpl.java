/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.BookingServiceDAO;
import com.poly.hotel.entity.BookingService;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author PHUONG LAM
 */
public class BookingServiceDAOImpl implements BookingServiceDAO {

    String createSql = "INSERT INTO BookingService (bookingServiceID, bookingID, serviceID, quantity, unitPrice, totalPrice) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    String updateSql = "UPDATE BookingService SET bookingID=?, serviceID=?, quantity=?, unitPrice=?, totalPrice=? "
            + "WHERE bookingServiceID=?";

    String deleteSql = "DELETE FROM BookingService WHERE bookingServiceID=?";

    String findAllSql = "SELECT * FROM BookingService";

    String findByIdSql = "SELECT * FROM BookingService WHERE bookingServiceID=?";

    @Override
    public BookingService create(BookingService entity) {
        Object[] value = {
            entity.getBookingServiceID(),
            entity.getBookingID(),
            entity.getServiceID(),
            entity.getQuantity(),
            entity.getUnitPrice(),
            entity.getTotalPrice()
        };
        XJdbc.executeUpdate(createSql, value);
        return entity;
    }

    @Override
    public void update(BookingService entity) {
        Object[] value = {
            entity.getBookingID(),
            entity.getServiceID(),
            entity.getQuantity(),
            entity.getUnitPrice(),
            entity.getTotalPrice(),
            entity.getBookingServiceID()
        };
        XJdbc.executeUpdate(updateSql, value);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<BookingService> findAll() {

        return XQuery.getBeanList(BookingService.class, findAllSql);
    }

    @Override
    public BookingService findById(String id) {
        return XQuery.getSingleBean(BookingService.class, findByIdSql, id);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.BookingDAO;
import com.poly.hotel.entity.Booking;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author Lenovo LOQ
 *
 */
public class BookingDAOImpl implements BookingDAO {

    String createSql = "INSERT INTO Booking (customerID, roomID, userName, checkInDate, checkOutDate, bookingDate, status, totalRoomAmount, totalServiceAmount, totalAmount) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String updateSql = "UPDATE Booking SET customerID=?, roomID=?, userName=?, checkInDate=?, checkOutDate=?, bookingDate=?, status=?, totalRoomAmount=?, totalServiceAmount=?, totalAmount=? "
            + "WHERE bookingID=?";

    String deleteSql = "DELETE FROM Booking WHERE bookingID=?";
    String findAllSql = "SELECT * FROM Booking";
    String findByIdSql = "SELECT * FROM Booking WHERE bookingID=?";

    @Override
    public Booking create(Booking entity) {
        Object[] values = {
            entity.getCustomerID(),
            entity.getRoomID(),
            entity.getUserName(),
            entity.getCheckInDate(),
            entity.getCheckOutDate(),
            entity.getBookingDate(),
            entity.getStatus(),
            entity.getTotalRoomAmount(),
            entity.getTotalServiceAmount(),
            entity.getTotalAmount()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(Booking entity) {
        Object[] values = {
            entity.getCustomerID(),
            entity.getRoomID(),
            entity.getUserName(),
            entity.getCheckInDate(),
            entity.getCheckOutDate(),
            entity.getBookingDate(),
            entity.getStatus(),
            entity.getTotalRoomAmount(),
            entity.getTotalServiceAmount(),
            entity.getTotalAmount(),
            entity.getBookingID() // điều kiện WHERE
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<Booking> findAll() {
        return XQuery.getBeanList(Booking.class, findAllSql);
    }

    @Override
    public Booking findById(String id) {
        return XQuery.getSingleBean(Booking.class, findByIdSql, id);
    }
}

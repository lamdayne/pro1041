/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.dao;

import com.poly.hotel.entity.Booking;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface BookingDAO extends CrudDAO<Booking, String> {
    Booking findByCustomerID(int customerID);
    List<Booking> findByRoomID(String roomId);
}

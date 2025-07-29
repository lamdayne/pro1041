/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.dao;

import com.poly.hotel.entity.BookingService;
import java.util.List;

/**
 *
 * @author PHUONG LAM
 */
public interface BookingServiceDAO extends CrudDAO<BookingService, String> {
    List<BookingService> findByBookingId(int bookingId);
}

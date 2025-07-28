/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.controller;

import com.poly.hotel.entity.Customer;
import com.poly.hotel.entity.Room;
import com.poly.hotel.entity.Service;

/**
 *
 * @author PHUONG LAM
 */
public interface BookingController {
    void fillRoomDetail(Room room);
    void fillService();
    void fillRoomService(Service service);
    void fillRoomSelected(Room room);
    Customer getCustomerInfo();
    void bookingRoom();
}

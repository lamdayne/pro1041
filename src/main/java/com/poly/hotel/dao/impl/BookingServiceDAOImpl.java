/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.BookingServiceDAO;
import com.poly.hotel.entity.BookingService;
import java.util.List;

/**
 *
 * @author PHUONG LAM
 */
public class BookingServiceDAOImpl implements BookingServiceDAO {
    
    String createSql = "";

    @Override
    public BookingService create(BookingService entity) {
        return entity;
    }

    @Override
    public void update(BookingService entity) {
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public List<BookingService> findAll() {
        return null;
    }

    @Override
    public BookingService findById(String id) {
        return null;
    }
    
}

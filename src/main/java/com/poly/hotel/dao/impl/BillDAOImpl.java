/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.BillDAO;
import com.poly.hotel.entity.Bill;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author Lenovo LOQ
 *
 */
public class BillDAOImpl implements BillDAO {

    String createSql = "INSERT INTO BILL (billID, bookingID, amount, paymentMethod, paymentDate, paymentStatus, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Bill SET bookingID=?, amount=?, paymentMethod=?, paymentDate=?, paymentStatus=?, username=? WHERE billID=?";
    String deleteSql = "DELETE FROM BILL WHERE billID=?";
    String findAllSql = "SELECT * FROM Bill";
    String findByIdSql = "SELECT * FROM Bill WHERE billID=?";

    @Override
    public Bill create(Bill bill) {
        Object[] values = {
            bill.getBillID(),
            bill.getBookingID(),
            bill.getAmount(),
            bill.getPaymentMethod(),
            bill.getPaymentDate(),
            bill.getPaymentStatus(),
            bill.getUsername()
        };
        XJdbc.executeUpdate(createSql, values);
        return bill;
    }

    @Override
    public void update(Bill bill) {
        Object[] values = {
            bill.getBillID(),
            bill.getBookingID(),
            bill.getAmount(),
            bill.getPaymentMethod(),
            bill.getPaymentDate(),
            bill.getPaymentStatus(),
            bill.getUsername()

        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String billID) {
        XJdbc.executeUpdate(deleteSql, billID);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, findAllSql);
    }

    @Override
    public Bill findById(String billID) {
        return XQuery.getSingleBean(Bill.class, findByIdSql, billID);
    }
      
}

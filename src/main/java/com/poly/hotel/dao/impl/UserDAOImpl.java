/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.dao.impl;

import com.poly.hotel.dao.UserDAO;
import com.poly.hotel.entity.User;
import com.poly.hotel.util.XJdbc;
import com.poly.hotel.util.XQuery;
import java.util.List;

/**
 *
 * @author PHUONG LAM
 */
public class UserDAOImpl implements UserDAO {

    String createSql = "INSERT INTO [User] VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE [User] SET password=?, fullName=?, gender=?, phoneNumber=?, email=?, role=?, isActive=? WHERE username=?";
    String deleteSql = "DELETE FROM [User] WHERE username=?";
    String findAllSql = "SELECT username, password, fullName, gender, phoneNumber, email, role, isActive AS Active FROM [User]";
    String findByIdSql = "SELECT username, password, fullName, gender, phoneNumber, email, role, isActive AS Active FROM [User] WHERE username=?";
    String findByEmailSql = "SELECT username, password, fullName, gender, phoneNumber, email, role, isActive AS Active FROM [User] WHERE email=?";

    @Override
    public User create(User entity) {
        Object[] value = {
            entity.getUsername(),
            entity.getPassword(),
            entity.getFullName(),
            entity.isGender(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getRole(),
            entity.isActive()
        };
        XJdbc.executeUpdate(createSql, value);
        return entity;
    }

    @Override
    public void update(User entity) {
        Object[] value = {
            entity.getPassword(),
            entity.getFullName(),
            entity.isGender(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getRole(),
            entity.isActive(),
            entity.getUsername()
        };
        XJdbc.executeUpdate(updateSql, value);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<User> findAll() {
        return XQuery.getBeanList(User.class, findAllSql);
    }

    @Override
    public User findById(String id) {
        return XQuery.getSingleBean(User.class, findByIdSql, id);
    }

    @Override
    public User findByEmail(String email) {
//        try {
//            return XQuery.getSingleBean(User.class, findByEmailSql, email);
//        } catch (Exception e) {
//            MsgBox.alert("Email không tồn tại");
//        }
        return XQuery.getSingleBean(User.class, findByEmailSql, email);
    }
}

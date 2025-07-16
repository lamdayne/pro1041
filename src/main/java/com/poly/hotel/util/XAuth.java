/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.util;

import com.poly.hotel.entity.User;

/**
 *
 * @author PHUONG LAM
 */
public class XAuth {
    public static User user = User.builder()
            .username("user1@gmail.com")
            .password("123")
            .fullName("Nguyen Van A")
            .gender(true)
            .phoneNumber("012345678")
            .email("admin@hotelmanager.com")
            .role("manager")
            .build();
}

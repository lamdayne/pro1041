    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
/**
 *
 * @author PC
 */
public class Booking {
    private int bookingID;
    private int customerID ;
    private String roomID ;
    private String useName;
    private Date checkInDate ;
    private Date checkOutDate;
    private Date bookingDate ;
    private String status;
    private float totalRoomAmount;
    private float totalServiceAmount;
    private float totalAmount;
}

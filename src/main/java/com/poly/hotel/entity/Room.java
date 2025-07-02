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
 * @author PHUONG LAM
 */
public class Room {
    private int roomID ;
    private String roomName ;
   private int floor ;
    private float hourPrice ;
    private float dailyPrice ;
    private String status;
    private String desc ;
    private int roomCategoryID;
}

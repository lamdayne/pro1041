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
public class RoomCategory {
    private int CategoryID ;
    private String CategoryName ;
    private String desc;
    private double baseHourPrice ;
    private double baseDailyPrice ;
    private int maxOccupancy;
    private boolean isActive;
}

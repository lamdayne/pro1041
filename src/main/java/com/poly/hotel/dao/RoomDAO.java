/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.dao;

import com.poly.hotel.entity.Room;
import java.util.List;

/**
 *
 * @author Windows
 */
public interface RoomDAO extends CrudDAO<Room, String> {

    List<Integer> findDistinctFloors();

    List<String> findDistinctStatuses();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.dao;

import com.poly.hotel.entity.RoomCategory;
import java.util.Optional;

/**
 *
 * @author Windows
 */
public interface RoomCategoryDAO extends CrudDAO<RoomCategory, String>{
    Optional<RoomCategory> findByName(String name);
}

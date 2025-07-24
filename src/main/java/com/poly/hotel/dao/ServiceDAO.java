/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.dao;

import com.poly.hotel.entity.Service;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface ServiceDAO extends CrudDAO<Service, String> {
    List<Service> findByCategoryId(int categoryId);
}

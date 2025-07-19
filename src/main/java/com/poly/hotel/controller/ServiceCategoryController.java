package com.poly.hotel.controller;

import com.poly.hotel.entity.ServiceCategory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author PC
 */
public interface ServiceCategoryController extends CrudController<ServiceCategory>{
 void moveFirst(); // Hiển thị thực thể đầu tiên
 void movePrevious(); // Hiển thị thực thể kế trước
 void moveNext(); // Hiển thị thực thể kế sau
 void moveLast(); // Hiển thị thực thể cuối cùng
 void moveTo(int rowIndex); // Hiển thị thực thể tại vị trí
}

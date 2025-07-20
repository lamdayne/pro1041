/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.util;

import javax.swing.JOptionPane;

/**
 *
 * @author PHUONG LAM
 */
public class MsgBox {
    
    public static void alert(String message) {
        JOptionPane.showMessageDialog(null, message, "Hệ thống quản lý khách sạn", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean comfirm(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return (result == JOptionPane.YES_OPTION);
    }

    public static String prompt(String message) {
        return JOptionPane.showInputDialog(null, message, "Nhập vào", JOptionPane.INFORMATION_MESSAGE);
    }
}

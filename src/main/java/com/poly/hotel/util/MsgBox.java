/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.hotel.util;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PHUONG LAM
 */
public class MsgBox {
        
    public static void alert(String message) {
        JOptionPane.showMessageDialog(null, message, "Hệ thống quản lý khách sạn", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void alertSuccess(String message) {
        ImageIcon icon = new ImageIcon(MsgBox.class.getResource("/com/poly/hotel/icons/accept.png"));
        JOptionPane.showMessageDialog(null, message, "Hệ thống quản lý khách sạn", JOptionPane.WARNING_MESSAGE, icon);
    }
    
    public static void alertFail(String message) {
        ImageIcon icon = new ImageIcon(MsgBox.class.getResource("/com/poly/hotel/icons/close.png"));
        JOptionPane.showMessageDialog(null, message, "Hệ thống quản lý khách sạn", JOptionPane.WARNING_MESSAGE, icon);
    }

    public static boolean comfirm(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return (result == JOptionPane.YES_OPTION);
    }

    public static String prompt(String message) {
        return JOptionPane.showInputDialog(null, message, "Nhập vào", JOptionPane.INFORMATION_MESSAGE);
    }
}

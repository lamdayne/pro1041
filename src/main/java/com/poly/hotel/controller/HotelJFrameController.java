/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.controller;

import com.poly.hotel.util.MsgBox;
import com.poly.hotel.view.HotelJFrame;
import com.poly.hotel.view.LoginJDialog;
import com.poly.hotel.view.WelcomeJDialog;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author PHUONG LAM
 */
public interface HotelJFrameController {
    
    void init();
    
    Frame getFrame();

    default void exit() {
        if (MsgBox.comfirm("Bạn muốn đăng xuất tài khoản?")) {
            getFrame().dispose(); // đóng frame hiện tại
            JFrame newFrame = new HotelJFrame();
            newFrame.setVisible(true); // hiển thị lại
        }
    }
    
    default void showJDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    default void showWelcomeJDialog(JFrame frame) {
        this.showJDialog(new WelcomeJDialog(frame, true));
    }

    default void showLoginJDialog(JFrame frame) {
        this.showJDialog(new LoginJDialog(frame, true));
    }
    
}

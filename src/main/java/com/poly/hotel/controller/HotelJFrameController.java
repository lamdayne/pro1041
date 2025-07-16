/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.poly.hotel.controller;

import com.poly.hotel.util.MsgBox;
import com.poly.hotel.view.LoginJDialog;
import com.poly.hotel.view.WelcomeJDialog;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author PHUONG LAM
 */
public interface HotelJFrameController {
    void init();

    default void exit() {
        if (MsgBox.comfirm("Bạn muốn đăng xuất tài khoản?")) {
            System.exit(0);
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

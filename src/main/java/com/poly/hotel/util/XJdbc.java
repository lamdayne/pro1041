package com.poly.hotel.util;

import java.sql.Connection;

/**
 * Tiện ích kết nối và truy vấn cơ sở dữ liệu SQL Server.
 * @author PHUONG LAM
 */
public final class XJdbc {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;trustServerCertificate=true;databaseName=HotelManager";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";
    private static Connection connection;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    
    
    
    
    
    
    
}
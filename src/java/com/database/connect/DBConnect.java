/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database.connect;

import java.io.Serializable;
import java.sql.Connection;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Jonh
 */
public class DBConnect implements Serializable {

    private final String db_url = "jdbc:mysql://localhost:13306/proyecto_vuelo";
    private final String db_username = "root";
    private final String db_password = "root";

    private Connection connection = null;
    protected PreparedStatement ps = null;

    protected ResultSet rs = null;

    public Connection connect() throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(db_url, db_username, db_password);
        return connection;

    }

    public void disconnect() {
        try {
            connection.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

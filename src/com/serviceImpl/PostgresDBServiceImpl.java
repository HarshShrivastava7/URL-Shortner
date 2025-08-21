package com.serviceImpl;

import com.helpers.ConnectionUtility;
import com.services.IURLDBService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PostgresDBServiceImpl implements IURLDBService {

    Connection conn;

    @Override
    public boolean insertIntoUrlMapping(String shortURL, String longURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "insert into url_mapping (short_url, long_url) values (?, ?)";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shortURL);
            pstmt.setString(2, longURL);
            int rowsAffected = pstmt.executeUpdate();
            return  rowsAffected == 1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return false;
    }

    @Override
    public String selectLongUrl(String shortURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "select long_url from url_mapping where short_url=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shortURL);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String longUrl = rs.getString("long_url");
                return longUrl;
            }
            else {
                throw new SQLException("Invalid Short URL");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return null;
    }

    @Override
    public boolean updateActiveStatus(boolean status, String shortURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "update url_mapping set active=? where short_url=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, status);
            pstmt.setString(2, shortURL);
            int rowsAffected = pstmt.executeUpdate();
            return  rowsAffected == 1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return false;
    }

    @Override
    public boolean addAnalytics(Map<String, Long> counters) {
        conn = ConnectionUtility.getConnection();
        String sql = "insert into url_analytics (short_url, count) values (?, ?) on conflict (short_url) do  update set count=url_analytics.count + EXCLUDED.count";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Map.Entry<String, Long> entry : counters.entrySet()) {
                pstmt.setString(1, entry.getKey());
                pstmt.setLong(2, entry.getValue());
                pstmt.addBatch();
            }
            int[] rowsAffected = pstmt.executeBatch();
            return   rowsAffected.length != 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return false;
    }
}

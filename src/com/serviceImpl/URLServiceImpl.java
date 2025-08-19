package com.serviceImpl;

import com.beans.URLMapping;
import com.exception.URLNotFoundException;
import com.helpers.Base62Encoder;
import com.helpers.ConnectionUtility;
import com.repository.URLRepository;
import com.services.IURLService;

import java.sql.*;
import java.util.Optional;

public class URLServiceImpl implements IURLService {

    Connection conn;
    @Override
    public String createShortURL(String longURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "insert into URLMapping (long_url) values (?)";
        String shortURL = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, longURL);
            int afftedRows = pstmt.executeUpdate();
            if (afftedRows == 0) {
                throw new SQLException("Creating short url failed, no row inerted");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int urlId = rs.getInt("url_id");
                shortURL = Base62Encoder.encode(urlId);
                updateShortUrlByID(shortURL, urlId);
            }
            else {
                throw new SQLException("Creating short url failed, no id obtained");
            }
        }
        catch (SQLException e) {
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return "/" + shortURL;
    }

    public void updateShortUrlByID(String shortURL, int urlId) {
        conn = ConnectionUtility.getConnection();
        String updateSql = "update URLMapping set short_url=? where url_id=?";
        try {
            PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql);

            pstmtUpdate.setString(1, shortURL);
            pstmtUpdate.setInt(2, urlId);

            int rowAffected = pstmtUpdate.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("URLMapping has been created");
            }
            else {
                throw new SQLException("Updating ShortUrl failed");
            }
        }
        catch (SQLException e) {
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }

    }

    @Override
    public String findLongURl(String shortURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "select long_url from URLMapping where short_url=?";
        String longURL = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shortURL);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                longURL = rs.getString("long_url");
            }
            else {
                throw new SQLException("Invalid ShortUrl");
            }
        }
        catch (SQLException e) {
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
        return "/" + longURL;

    }

    @Override
    public void deactivateShortURL(String shortURL) {
        conn = ConnectionUtility.getConnection();
        String sql = "update URLMapping set is_active=? where short_url=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setBoolean(1, false);
            pstmt.setString(2, shortURL);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                System.out.println("URLMapping has been deactivated");
            }
            else {
                throw new SQLException("Deactivating ShortUrl failed");
            }
        }
        catch (SQLException e) {
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement element : trace) {
                System.out.println("Exception at : " +  element);
            }
        }
    }
}

package com.example.plant.database;

import android.os.StrictMode;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    public static DatabaseService dbService = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private DatabaseService() {
    }

    public static DatabaseService getDbService() {
        if (dbService == null) {
            dbService = new DatabaseService();
        }
        return dbService;
    }

    //判断是否登录
    public Boolean isLogin() {
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select * from IS_LOGIN";
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    return rs.getInt("isLogin") == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return false;
    }

    //设为已登录状态
    public Boolean setLoginTrue() {
        conn = DatabaseConn.getConn("PLANT");
        String sql = "update IS_LOGIN set isLogin=1 where id=0";
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        DatabaseConn.closeAll(conn, ps);
        return true;
    }

    //判断登录名和密码是否匹配
    public Boolean login(String userName, String password) {
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select password from LOGIN where userName=\"" + userName + "\"";
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    String get_password = rs.getString("password");
                    if (get_password == null || get_password.equals("") || !get_password.equals(password)) {
                        DatabaseConn.closeAll(conn, ps, rs);
                        return false;
                    } else {
                        DatabaseConn.closeAll(conn, ps, rs);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return false;
    }

    //查
    public String getPassportData(String id) {
        String s = "";
        String sql = "select * from PASSPORT_RECORDS where passport_id=\"" + id + "\"";
        conn = DatabaseConn.getConn("PLANT");
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    ByteArrayInputStream msgContent = (ByteArrayInputStream) rs.getBinaryStream("pic");
                    byte[] byte_data = new byte[msgContent.available()];
                    msgContent.read(byte_data, 0, byte_data.length);
                    s = new String(byte_data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return s;
    }

    public String getIdcardData(String id) {
        String s = "";
        String sql = "select * from IDCARD_RECORDS where idcard_id = \"" + id + "\"";
        conn = DatabaseConn.getConn("PLANT");
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    ByteArrayInputStream msgContent = (ByteArrayInputStream) rs.getBinaryStream("pic");
                    byte[] byte_data = new byte[msgContent.available()];
                    msgContent.read(byte_data, 0, byte_data.length);
                    s = new String(byte_data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return s;
    }

    public String getMailData(String id) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String s="";
        String sql = "select * from plant where id =  \"" + id + "\"";
        conn = DatabaseConn.getConn("PLANT");
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    ByteArrayInputStream msgContent = (ByteArrayInputStream) rs.getBinaryStream("pic");
                    byte[] byte_data = new byte[msgContent.available()];
                    msgContent.read(byte_data, 0, byte_data.length);
                    s = new String(byte_data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return s;
    }


    public int[] getAllMailDataByTime() {

        conn = DatabaseConn.getConn("PLANT");

        String sql = "select time from plant";
        int[] d = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("time");
                        int fDot = result.indexOf(".");
                        int sDot = result.indexOf(".", fDot + 1);
                        if (fDot != -1 && sDot != -1) {
                            d[Integer.parseInt(result.substring(fDot + 1, sDot)) - 1]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return d;
    }

    public List<String> getSecondChapterData(int i) {
        List<String> list = new ArrayList<String>();
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select name from SECOND_CHAPTER_IN_KNOWLEDGE where first_class= " + i + " ORDER BY second_class";
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        list.add(name);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return list;
    }

    public Map getAllKnowledgeData(int i, int j) {
        Map<String, String> map = new HashMap<String, String>();
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select * from KNOWLEDGE  where class= " + i + " and second_class= " + j + " ORDER BY title";

        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String content;
                        ByteArrayInputStream msgContent = (ByteArrayInputStream) rs.getBinaryStream("content");
                        byte[] byte_data = new byte[msgContent.available()];
                        msgContent.read(byte_data, 0, byte_data.length);
                        content = new String(byte_data);
                        map.put(title, content);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return map;
    }

    public Map getAllMailDataByCountry() {
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select country from plant";
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("country");
                        if (result.equals("")) {
                            continue;
                        }
                        if (map.get(result) == null) {
                            map.put(result, 1);
                        } else {
                            map.put(result, map.get(result) + 1);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return map;
    }

    public int[] getAllMailDataByClass() {
        conn = DatabaseConn.getConn("PLANT");
        String sql = "select class from plant";
        int[] d = {0, 0, 0, 0, 0, 0};
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("class");
                        if (result.equals("日用品")) {
                            d[0]++;
                        } else if (result.equals("食品")) {
                            d[1]++;
                        } else if (result.equals("文件")) {
                            d[2]++;
                        } else if (result.equals("数码产品")) {
                            d[3]++;
                        } else if (result.equals("衣物")) {
                            d[4]++;
                        } else if (result.equals("其他")) {
                            d[5]++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return d;
    }

    public int insertMailData(String id,String s,String klass,String time,String country) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        int result = -1;
        conn = DatabaseConn.getConn("PLANT");
        String sql = "INSERT INTO plant (id,pic,class,time,country) VALUES(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes());
            ps.setBinaryStream(2, stream, stream.available());
            ps.setString(3,klass);
            ps.setString(4,time);
            ps.setString(5,country);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps);

        return result;
    }

    public int insertPassportData(String id, String s) {
        int result = -1;
        conn = DatabaseConn.getConn("PLANT");
        String sql = "INSERT INTO PASSPORT_RECORDS (passport_id,pic) values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes());
            ps.setBinaryStream(2, stream, stream.available());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps);
        return result;
    }

    public int insertIdcardData(String id, String s) {
        int result = -1;
        conn = DatabaseConn.getConn("PLANT");
        String sql = "INSERT INTO IDCARD_RECORDS (idcard_id,pic) values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes());
            ps.setBinaryStream(2, stream, stream.available());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps);
        return result;
    }


}

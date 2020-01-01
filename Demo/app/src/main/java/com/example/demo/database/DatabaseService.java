package com.example.demo.database;

import android.os.StrictMode;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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

    public Mail getMailData(String id) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Mail result = new Mail();
        String sql = "select * from MAIL_RECORDS where mail_id = " + id;
        conn = DatabaseConn.getConn("CUSTOMS");
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    rs.next();
                    result.setMail_id(rs.getString("Mail_id"));
                    result.setSender_name(rs.getString("sender_name"));
                    result.setSender_tel(rs.getString("sender_tel"));
                    result.setSending_country(rs.getString("sending_country"));
                    result.setSending_district(rs.getString("sending_district"));
                    result.setSending_district_in_detail(rs.getString("sending_district_in_detail"));
                    result.setReceiver_name(rs.getString("receiver_name"));
                    result.setReceiver_tel(rs.getString("receiver_tel"));
                    result.setReceiving_province(rs.getString("receiving_province"));
                    result.setReceiving_city(rs.getString("receiving_city"));
                    result.setReceiving_county(rs.getString("receiving_county"));
                    result.setReceiving_district_in_detail(rs.getString("receiving_district_in_detail"));
                    result.setPackage_class(rs.getString("package_class"));
                    result.setPackage_weight(rs.getDouble("package_weight"));
                    result.setCheck_conclusion(rs.getString("check_conclusion"));
                    result.setIs_destroy(rs.getBoolean("is_destroy"));
                    result.setIs_letgo(rs.getBoolean("is_letgo"));
                    result.setIs_letcheck(rs.getBoolean("is_letcheck"));
                    result.setIs_letback(rs.getBoolean("is_letback"));
                    result.setIn_storage_time(rs.getString("in_storage_time"));
                    result.setIn_storage_site(rs.getString("in_storage_site"));
                    result.setOut_storage_site(rs.getString("out_storage_site"));
                    result.setOut_storage_time(rs.getString("out_storage_time"));
                    result.setOperating_time(rs.getString("operating_time"));
                    result.setOperator(rs.getString("operator"));
                    //处理图片
                    result.setPic_path(rs.getString("pic_path"));
                    String targetPath = result.getPic_path();
                    boolean fileNotExist = false;
                    try {
                        File file = new File(result.getPic_path());
                        FileInputStream fis = new FileInputStream(file);
                    } catch (Exception e) {
                        fileNotExist = true;
                        e.printStackTrace();
                    }
                    if (fileNotExist && result.getPic_path() != null) {
                        InputStream in = rs.getBinaryStream("pic");
                        File file = new File(targetPath);
                        String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
                        if (!file.exists()) {
                            new File(path).mkdir();
                        }
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            int len = 0;
                            byte[] buf = new byte[1024];
                            while ((len = in.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                            }
                            fos.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != fos) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps, rs);
        return result;
    }


    public int[] getAllMailDataByTime() {

        conn = DatabaseConn.getConn("CUSTOMS");

        String sql = "select in_storage_time from MAIL_RECORDS";
        int[] d = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("in_storage_time");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
        String sql = "select * from KNOWLEDGE  where class= " + i + " and second_class= " + j + " ORDER BY title";

        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String content = rs.getString("content");
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
        conn = DatabaseConn.getConn("CUSTOMS");
        String sql = "select sending_country from MAIL_RECORDS";
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("sending_country");
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
        conn = DatabaseConn.getConn("CUSTOMS");
        String sql = "select package_class from MAIL_RECORDS";
        int[] d = {0, 0, 0, 0, 0, 0};
        try {
            ps = conn.prepareStatement(sql);
            if (ps != null) {
                rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String result = rs.getString("package_class");
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

    public int insertMailData(Mail m) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        int result = -1;
        conn = DatabaseConn.getConn("CUSTOMS");
        String sql = "INSERT INTO MAIL_RECORDS (mail_id,sender_name,sender_tel,sending_country," +
                "sending_district,sending_district_in_detail,receiver_name,receiver_tel," +
                "receiving_province,receiving_city,receiving_county,receiving_district_in_detail," +
                "package_class,package_weight,check_conclusion,is_destroy,is_letgo," +
                "is_letcheck,is_letback,in_storage_time,in_storage_site,out_storage_time,out_storage_site," +
                "operator,operating_time,pic,pic_path,pic_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, m.getMail_id());
            ps.setString(2, m.getSender_name());
            ps.setString(3, m.getSender_tel());
            ps.setString(4, m.getSending_country());
            ps.setString(5, m.getSending_district());
            ps.setString(6, m.getSending_district_in_detail());
            ps.setString(7, m.getReceiver_name());
            ps.setString(8, m.getReceiver_tel());
            ps.setString(9, m.getReceiving_province());
            ps.setString(10, m.getReceiving_city());
            ps.setString(11, m.getReceiving_county());
            ps.setString(12, m.getReceiving_district_in_detail());
            ps.setString(13, m.getPackage_class());
            ps.setDouble(14, m.getPackage_weight());
            ps.setString(15, m.getCheck_conclusion());
            ps.setBoolean(16, m.isIs_destroy());
            ps.setBoolean(17, m.isIs_letgo());
            ps.setBoolean(18, m.isIs_letcheck());
            ps.setBoolean(19, m.isIs_letback());
            ps.setString(20, m.getIn_storage_time());
            ps.setString(21, m.getIn_storage_site());
            ps.setString(22, m.getOut_storage_time());
            ps.setString(23, m.getOut_storage_site());
            ps.setString(24, m.getOperator());
            ps.setString(25, m.getOperating_time());
            if (m.getPic_path() != null) {
                try {
                    FileInputStream fis = new FileInputStream(new File(m.getPic_path()));
                    ps.setBlob(26, fis, fis.available());
                } catch (Exception e) {
                    e.printStackTrace();
                    ps.setBlob(26, (Blob) null);
                }
            } else {
                ps.setBlob(26, (Blob) null);
            }
            ps.setString(27, m.getPic_path());
            ps.setString(28, m.getType());
            // Mail tmp=getMailData(m.getMail_id());
//            if(tmp.getMail_id()!=null){
////                //删除
////            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseConn.closeAll(conn, ps);

        return result;
    }

    public int insertPassportData(String id, String s) {
        int result = -1;
        conn = DatabaseConn.getConn("CUSTOMS");
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
        conn = DatabaseConn.getConn("CUSTOMS");
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

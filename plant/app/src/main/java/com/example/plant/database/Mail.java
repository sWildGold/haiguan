package com.example.plant.database;

import java.io.InputStream;
import java.io.Serializable;

public class Mail implements Serializable {
    private String mail_id;
    private String sender_name;
    private String sender_tel;
    private String sending_country;
    private String sending_district;
    private String sending_district_in_detail;
    private String receiver_name;
    private String receiver_tel;
    private String receiving_province;
    private String receiving_city;
    private String receiving_county;
    private String receiving_district_in_detail;
    private String package_class;
    private Double package_weight;
    private String check_conclusion;

    private boolean is_destroy;
    private boolean is_letgo;
    private boolean is_letcheck;
    private boolean is_letback;
    private String in_storage_time;
    private String in_storage_site;
    private String out_storage_time;
    private String out_storage_site;
    private String operator;
    private String operating_time;
    private String pic_path;
    private InputStream pic_inputStream;
    private String type;

    public InputStream getPic_inputStream() {
        return pic_inputStream;
    }

    public void setPic_inputStream(InputStream pic_inputStream) {
        this.pic_inputStream = pic_inputStream;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_tel() {
        return sender_tel;
    }

    public void setSender_tel(String sender_tel) {
        this.sender_tel = sender_tel;
    }

    public String getSending_country() {
        return sending_country;
    }

    public void setSending_country(String sending_country) {
        this.sending_country = sending_country;
    }

    public String getSending_district() {
        return sending_district;
    }

    public void setSending_district(String sending_district) {
        this.sending_district = sending_district;
    }

    public String getSending_district_in_detail() {
        return sending_district_in_detail;
    }

    public void setSending_district_in_detail(String sending_district_in_detail) {
        this.sending_district_in_detail = sending_district_in_detail;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_tel() {
        return receiver_tel;
    }

    public void setReceiver_tel(String receiver_tel) {
        this.receiver_tel = receiver_tel;
    }

    public String getIn_storage_site() {
        return in_storage_site;
    }

    public void setIn_storage_site(String in_storage_site) {
        this.in_storage_site = in_storage_site;
    }

    public String getIn_storage_time() {
        return in_storage_time;
    }

    public void setIn_storage_time(String in_storage_time) {
        this.in_storage_time = in_storage_time;
    }

    public String getOperating_time() {
        return operating_time;
    }

    public void setOperating_time(String operating_time) {
        this.operating_time = operating_time;
    }

    public boolean isIs_destroy() {
        return is_destroy;
    }

    public void setIs_destroy(boolean is_destroy) {
        this.is_destroy = is_destroy;
    }

    public boolean isIs_letback() {
        return is_letback;
    }

    public void setIs_letback(boolean is_letback) {
        this.is_letback = is_letback;
    }

    public boolean isIs_letcheck() {
        return is_letcheck;
    }

    public void setIs_letcheck(boolean is_letcheck) {
        this.is_letcheck = is_letcheck;
    }

    public boolean isIs_letgo() {
        return is_letgo;
    }

    public void setIs_letgo(boolean is_letgo) {
        this.is_letgo = is_letgo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOut_storage_time() {
        return out_storage_time;
    }

    public void setOut_storage_time(String out_storage_time) {
        this.out_storage_time = out_storage_time;
    }

    public String getPackage_class() {
        return package_class;
    }

    public void setPackage_class(String package_class) {
        this.package_class = package_class;
    }

    public String getOut_storage_site() {
        return out_storage_site;
    }

    public void setOut_storage_site(String out_storage_site) {
        this.out_storage_site = out_storage_site;
    }

    public String getReceiving_county() {
        return receiving_county;
    }

    public void setReceiving_county(String receiving_county) {
        this.receiving_county = receiving_county;
    }

    public String getReceiving_district_in_detail() {
        return receiving_district_in_detail;
    }

    public void setReceiving_district_in_detail(String receiving_district_in_detail) {
        this.receiving_district_in_detail = receiving_district_in_detail;
    }

    public String getReceiving_city() {
        return receiving_city;
    }

    public void setReceiving_city(String receiving_city) {
        this.receiving_city = receiving_city;
    }

    public String getCheck_conclusion() {
        return check_conclusion;
    }

    public void setCheck_conclusion(String check_conclusion) {
        this.check_conclusion = check_conclusion;
    }

    public String getReceiving_province() {
        return receiving_province;
    }

    public void setReceiving_province(String receiving_province) {
        this.receiving_province = receiving_province;
    }

    public Double getPackage_weight() {
        return package_weight;
    }

    public void setPackage_weight(Double package_weight) {
        this.package_weight = package_weight;
    }
}

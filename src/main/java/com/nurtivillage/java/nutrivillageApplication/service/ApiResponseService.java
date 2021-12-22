package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ApiResponseService {
    private String msg;
    private boolean status;
    private List<?> data;
    private int totalPage; 

    public ApiResponseService(){};
    public ApiResponseService(String msg,boolean status,List<?> data,int page){
        this.msg = msg;
        this.status = status;
        this.data = data;
        this.totalPage = page;
    };
    public ApiResponseService(String msg,boolean status,List<?> data){
        this.msg = msg;
        this.status = status;
        this.data = data;
    };

    public List<?> getData() {
        return data;
    }
    public String getMsg() {
        return msg;
    }
    public boolean getStatus(){
        return status;
    }

    public int getTotalPage() {
        return totalPage;
    }


}

package com.chang.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountStatement implements Serializable {

    private static final long serialVersionUID = -5391627006116231237L;

    private String pid;
    private String outTradeNo;
    private Date orderTime;
    private String orderState;
    private String orderType;
    private String hisAmount;
    private String bmAmount;
    private String cardNo;
    private String outRefundNo;
    private String patientId;
    private String patientName;
    private String payType;
}

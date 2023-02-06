package com.m2u.pushnotif.consumer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class RDNModel {

    @JsonProperty("NotifListenerId")
    private String NotifListenerId;
    //private String SeqNumber;
    @JsonProperty("AccountNumber")
    private String AccountNumber;

    @JsonProperty("Currency")
    private String Currency;

    @JsonProperty("TxnDate")
    private String TxnDate;

    @JsonProperty("TxnCode")
    private String TxnCode;
    //private String AccountDebit;

    @JsonProperty("AccountCredit")
    private String AccountCredit;

    @JsonProperty("TxnAmount")
    private BigDecimal TxnAmount;

    @JsonProperty("CloseBalance")
    private BigDecimal CloseBalance;

    @JsonProperty("TxnDescription")
    private String TxnDesc;



    @JsonProperty("ExternalReference")
    private String ExternalReference;

    @JsonProperty("SeqNumber")
    private String SeqNumber;

    @JsonProperty("TxnType")
    private String TxnType;



    @JsonProperty("ExternalReference")
    public String getExternalReference() {
        return ExternalReference;
    }

    @JsonProperty("ExternalReference")
    public void setExternalReference(String externalReference) {
        ExternalReference = externalReference;
    }

    @JsonProperty("SeqNumber")
    public String getSeqNumber() {
        return SeqNumber;
    }

    @JsonProperty("SeqNumber")
    public void setSeqNumber(String seqNumber) {
        SeqNumber = seqNumber;
    }

    @JsonProperty("TxnType")
    public String getTxnType() {
        return TxnType;
    }

    @JsonProperty("TxnType")
    public void setTxnType(String txnType) {
        TxnType = txnType;
    }

    @JsonProperty("NotifListenerId")
    public String getNotifListenerId() {
        return NotifListenerId;
    }

    @JsonProperty("NotifListenerId")
    public void setNotifListenerId(String notifListenerId) {
        NotifListenerId = notifListenerId;
    }

    @JsonProperty("AccountNumber")
    public String getAccountNumber() {
        return AccountNumber;
    }
    @JsonProperty("AccountNumber")
    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    @JsonProperty("Currency")
    public String getCurrency() {
        return Currency;
    }
    @JsonProperty("Currency")
    public void setCurrency(String currency) {
        Currency = currency;
    }

    @JsonProperty("TxnDate")
    public String getTxnDate() {
        return TxnDate;
    }
    @JsonProperty("TxnDate")
    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }
    @JsonProperty("TxnCode")
    public String getTxnCode() {
        return TxnCode;
    }
    @JsonProperty("TxnCode")
    public void setTxnCode(String txnCode) {
        TxnCode = txnCode;
    }
    @JsonProperty("AccountCredit")
    public String getAccountCredit() {
        return AccountCredit;
    }
    @JsonProperty("AccountCredit")
    public void setAccountCredit(String accountCredit) {
        AccountCredit = accountCredit;
    }
    @JsonProperty("TxnAmount")
    public BigDecimal getTxnAmount() {
        return TxnAmount;
    }
    @JsonProperty("TxnAmount")
    public void setTxnAmount(BigDecimal txnAmount) {
        TxnAmount = txnAmount;
    }

    @JsonProperty("TxnDescription")
    public String getTxnDesc() {
        return TxnDesc;
    }
    @JsonProperty("TxnDescription")
    public void setTxnDesc(String txnDesc) {
        TxnDesc = txnDesc;
    }
    @JsonProperty("CloseBalance")
    public BigDecimal getCloseBalance() {
        return CloseBalance;
    }
    @JsonProperty("CloseBalance")
    public void setCloseBalance(BigDecimal closeBalance) {
        CloseBalance = closeBalance;
    }

}

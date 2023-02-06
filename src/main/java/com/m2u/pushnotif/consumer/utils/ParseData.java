package com.m2u.pushnotif.consumer.utils;

import com.m2u.pushnotif.consumer.models.ContentModel;

public class ParseData {

    public ContentModel parseContent(String data) {
        ContentModel contentModel = new ContentModel();
        contentModel.setMataUang(data.substring(0, 3));
        contentModel.setNoRekening(data.substring(3, 13));
        contentModel.setTanggalTransaksi(data.substring(13, 21));
        contentModel.setJamTransaksi(data.substring(21, 27));
        contentModel.setDebitKredit(data.substring(27, 28));
        contentModel.setNilaiTransaksi(data.substring(28, 43));
        contentModel.setDecimalTransaksi("."+data.substring(43,45));
        contentModel.setDeskripsiTransaksi(data.substring(45, 78));
        //sm.setFlag(data.substring(data.length()-2, data.length()-1));
        //sm.setCif(data.substring(data.length()-14, data.length()-2));
        contentModel.setFlag(data.substring(160, 161));
        System.out.println("get flag : " + contentModel.getFlag());
        contentModel.setCif(data.substring(100, 112).trim());
        if("4".equals(contentModel.getFlag())||"5".equals(contentModel.getFlag())||"6".equals(contentModel.getFlag())||"7".equals(contentModel.getFlag())) {
            String temp = data.substring(data.length()-19,data.length()-1);

            contentModel.setBalanceDecimal(temp.substring(temp.length()-2));
            contentModel.setBalance(temp.substring(0,temp.length()-2));
            contentModel.setDecimalTransaksi(data.substring(43,45));

        }
        return contentModel;
    }
}

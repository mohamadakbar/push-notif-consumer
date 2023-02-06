package com.m2u.pushnotif.consumer.utils;

import com.m2u.pushnotif.consumer.PushNotifConsumerApplication;
import com.m2u.pushnotif.consumer.models.AdditionalModel;
import com.m2u.pushnotif.consumer.models.ContentModel;
import com.m2u.pushnotif.consumer.models.PushModel;
import com.m2u.pushnotif.consumer.models.RDNModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllFunc {
    private static final Logger logger = LoggerFactory.getLogger(PushNotifConsumerApplication.class);

    public static Map map;
    static {
        map = new HashMap();
        map.put("016", "IDR");
        map.put("017", "AUD");
        map.put("021", "CAD");
        map.put("024", "GBP");
        map.put("025", "HKD");
        map.put("029", "JPY");
        map.put("032", "NZD");
        map.put("039", "USD");
        map.put("047", "EUR");
        map.put("360", "IDR");
        map.put("049", "CNY");
        map.put("051", "AMD");
        map.put("037", "CHF");
        map.put("187", "MYR");
        map.put("034", "SGD");

    }

    public void sendToPushNotif(ContentModel contentModel, String endpointPush) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddHHmmss");
        String msg = constructMessage(contentModel);
        // String msg = "Nasabah Yth,terima kasih telah melakukan transaksi sebesar
        // Rp."+removeLeadingZeroes(sm.getNilaiTransaksi()) +" tanggal
        // "+sm.getTanggalTransaksi();

        logger.info("construct data : {}", msg);
        try {
            AdditionalModel additionalModel = new AdditionalModel();
            additionalModel.setNotif_title("Notifikasi Transaksi");
            additionalModel.setFull_desc(msg);
            additionalModel.setType("nor");
            additionalModel.setMsgId(sdf2.format(new Date()));
            additionalModel.setCat_id("2");
            additionalModel.setCat_name("Transaction");
            additionalModel.setSubcat_id("0");
            additionalModel.setImg_url("");

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 30);

            String now = sdf.format(date);
            String fwd = sdf.format(calendar.getTime());
            additionalModel.setStart_date(now);
            additionalModel.setEnd_date(fwd);
            additionalModel.setInbox_expiry(fwd);
            additionalModel.setIs_sound("no");
            additionalModel.setRecipient_url("");
            additionalModel.setTemplate_id("");
            additionalModel.setPositive_btn_lbl("");
            additionalModel.setNegative_btn_lbl("");

            PushModel pushModel = new PushModel();
            pushModel.setCollapseKey("IDPush");
            pushModel.setAppId("1");
            pushModel.setGcifIds(contentModel.getCif());
            pushModel.setTitle("Notifikasi Transaksi");
            pushModel.setMsg("Notifikasi Transaksi");
            pushModel.setAdditionalData(additionalModel);

            JsonAPI js = new JsonAPI();
            js.callAPIJsonObject(pushModel, endpointPush);
            // js.callAPIJsonObject(pm, "http://10.230.66.12:3000/sendPush");
            // js.callAPIJsonObject(pm, "http://10.220.35.26:3000/sendPush");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String removeLeadingZeroes(String value) {
        return new BigDecimal(value).toString();
    }

    public void sendToRDN(ContentModel contentModel, String endpointRDN) {
        try {
            RDNModel rdn = new RDNModel();
            UUID uuid = UUID.randomUUID();
            // uuid.toString()
            rdn.setNotifListenerId(uuid.toString());
            logger.info("construct data");
            // rdn.setSeqNumber("1");
            rdn.setAccountNumber(contentModel.getNoRekening());
            rdn.setCurrency(map.get(contentModel.getMataUang()).toString());
            SimpleDateFormat sd1 = new SimpleDateFormat("MMddyyyy");
            Date date1 = new SimpleDateFormat("yyyyMMdd").parse(contentModel.getTanggalTransaksi());
            String tanggal = sd1.format(date1);
            rdn.setTxnDate(tanggal + " "+contentModel.getJamTransaksi());
            rdn.setTxnCode("C");
            //rdn.setAccountDebit(contentModel.getNoRekening());
            rdn.setAccountCredit(contentModel.getNoRekening());
            rdn.setTxnAmount(new BigDecimal(contentModel.getNilaiTransaksi()+"."+contentModel.getDecimalTransaksi()));
            rdn.setCloseBalance(new BigDecimal(contentModel.getBalance()+"."+contentModel.getBalanceDecimal()));
            rdn.setTxnDesc(contentModel.getDeskripsiTransaksi());
            rdn.setExternalReference(rdn.getNotifListenerId());
            rdn.setTxnType("NTRF");
            rdn.setSeqNumber("00000");
            JsonAPI js = new JsonAPI();
            // js.callAPIJsonObject(rdn,"http://10.230.71.42:8701/api/external/mke/Notification");
            // js.callAPIJsonObject(rdn,"http://172.17.23.25:8701/api/external/mke/Notification");
            js.callAPIJsonObject(rdn, endpointRDN);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTomobile(String data, String endpointMobile) {
        // String domainName = "016800390022920200402164517D00000000001500000PAY -
        // EXCELCO 0819010123232 164517ATM1 Y";
        //
        String hostname = endpointMobile;
        int port = 6900;
        Socket socket = null;
        OutputStream output = null;
        try {
            socket = new Socket(hostname, port);
            socket.setSoTimeout(10000);
            logger.info("start send");

            output = socket.getOutputStream();
            // PrintWriter writer = new PrintWriter(output, true);
            // writer.println(data);

            byte[] bytes = data.getBytes("UTF-8");

            output.write(bytes, 0, bytes.length);

            logger.info("start send" + data);
            // output.close();
            // writer.close();
            // socket.close();
            logger.info("end send");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            try {
                output.close();
                socket.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String constructMessage(ContentModel contentModel) {
        StringBuffer buff = new StringBuffer();
        String tanggal = "";
        String mataUang = "";
        String jam = contentModel.getJamTransaksi().substring(0, 2) + ":"
                + contentModel.getJamTransaksi().substring(2, 4) + ":" + contentModel.getJamTransaksi().substring(4, 6);

        double myNum = Double.parseDouble(contentModel.getNilaiTransaksi() + contentModel.getDecimalTransaksi());
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        if (map.get(contentModel.getMataUang()) != null) {
            mataUang = String.valueOf(map.get(contentModel.getMataUang()));
        } else {
            mataUang = contentModel.getMataUang();
        }
        formatRp.setCurrencySymbol(mataUang + " ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        // kursIndonesia.sede
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        try {
            SimpleDateFormat sd1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = new SimpleDateFormat("yyyyMMdd").parse(contentModel.getTanggalTransaksi());
            tanggal = sd1.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dk = contentModel.getDebitKredit();
        if ("K".equals(dk)) {
            dk = "Kredit";
        } else if ("D".equals(dk)) {
            dk = "Debet";
        }

        buff.append(
                "Terima kasih telah menggunakan Maybank untuk aktifitas perbankan Anda. Berikut ini adalah aktifitas transaksi yang terjadi pada rekening Maybank:<br><br>");
        buff.append("Tanggal Transaksi : " + tanggal + "<br>");
        buff.append("Jam Transaksi : " + jam + "<br>");
        buff.append("Jenis Mutasi : " + dk + " <br>");
        buff.append("Nominal Transaksi :" + kursIndonesia.format(myNum) + "<br>");
        buff.append("Nomor Rekening: ***"
                + contentModel.getNoRekening().substring(contentModel.getNoRekening().length() - 4) + "<br>");
        buff.append("Deskripsi Transaksi: " + contentModel.getDeskripsiTransaksi() + "<br>");
        buff.append("<br>");
        buff.append("Catatan Penting:<br>");
        buff.append(
                "Jaga kerahasiaan data pribadi &amp; perbankan Anda seperti <i>Transaction Activation Code</i><br>");
        buff.append("(TAC), PIN, User ID, password, dan OTP. <b>Jangan berikan kepada siapapun dengan alasan<br>");
        buff.append("apapun!</b><br>");
        buff.append("Hubungi 1500611 untuk informasi lebih lanjut terkait transaksi tidak dikenal.<br><br>");
        buff.append("Hormat Kami,<br>");
        buff.append("PT. Bank Maybank Indonesia, Tbk.</p>");
        return buff.toString();
    }
}

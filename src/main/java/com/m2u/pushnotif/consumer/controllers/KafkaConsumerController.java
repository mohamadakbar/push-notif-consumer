package com.m2u.pushnotif.consumer.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2u.pushnotif.consumer.PushNotifConsumerApplication;
import com.m2u.pushnotif.consumer.models.ContentModel;
import com.m2u.pushnotif.consumer.models.JsonModel;
import com.m2u.pushnotif.consumer.utils.AllFunc;
import com.m2u.pushnotif.consumer.utils.ParseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class KafkaConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(PushNotifConsumerApplication.class);

    @Value(value = "${endpoint.send.to.rdn}")
    private String apiSendRDN;

    @Value(value = "${endpoint.send.to.push.notif}")
    private String apiSendPushNotif;

    @Value(value = "${endpoint.send.to.mobile.ip}")
    private String apiSendMobile;

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumerListener(String message) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonModel jsonData = mapper.readValue(message, JsonModel.class);
            String rawData = jsonData.toString();

            logger.info("raw data : {}", rawData);

            ParseData parseData = new ParseData();
            ContentModel contentModel = parseData.parseContent(rawData);
            AllFunc allFunc = new AllFunc();

            switch (contentModel.getFlag()){
                case "3":
                    allFunc.sendTomobile(rawData.substring(0, 100), apiSendMobile);
                    allFunc.sendToPushNotif(contentModel, apiSendPushNotif);
                    logger.info("send to mobile and notif");
                    break;
                case "2":
                    allFunc.sendToPushNotif(contentModel, apiSendPushNotif);
                    logger.info("send to notif");
                    break;
                case"1":
                    allFunc.sendTomobile(rawData.substring(0, 100), apiSendMobile);
                    logger.info("send to mobile");
                    break;
                case "4":
                    allFunc.sendToRDN(contentModel, apiSendRDN);
                    logger.info("send to RDN");
                    break;
                case "5":
                    allFunc.sendToRDN(contentModel, apiSendRDN);
                    allFunc.sendTomobile(rawData.substring(0, 100), apiSendMobile);
                    logger.info("send to RDN and mobile");
                    break;
                case "6":
                    allFunc.sendToRDN(contentModel, apiSendRDN);
                    allFunc.sendToPushNotif(contentModel, apiSendPushNotif);
                    logger.info("send to RDN and notif");
                    break;
                case "7":
                    allFunc.sendToRDN(contentModel, apiSendRDN);
                    allFunc.sendToPushNotif(contentModel, apiSendPushNotif);
                    allFunc.sendTomobile(rawData.substring(0, 100), apiSendMobile);
                    logger.info("send to RDN, notif and mobile");
                    break;
                default:
                    logger.info("flag not match");
                    break;
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

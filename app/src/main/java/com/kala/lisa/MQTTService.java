package com.kala.lisa;

import android.content.ContentResolver;
import android.content.Context;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;

/**
 * Created by Kalaman on 16.12.2017.
 */

/**
 *
 */
public class MQTTService {
    MQTT mqtt;
    Context context;
    private static BlockingConnection connection;

    private final String SERVER_ADDRESS = "192.168.0.41";
    private final int SERVER_PORT = 1883;

    private final static String LOGIN_TOPIC = "login";

    public MQTTService (Context context) {
        mqtt = new MQTT();

        try {
            mqtt.setHost(SERVER_ADDRESS,SERVER_PORT);
            connection = mqtt.blockingConnection();
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean publishMessage (String payload,String topic){

        try {
            connection.publish(topic, payload.getBytes() ,QoS.EXACTLY_ONCE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

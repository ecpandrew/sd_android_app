package com.example.sdmonitorapp.ui.main.details;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sdmonitorapp.models.Sensor;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

public class SensorDetailViewModel extends ViewModel {

    private final String brokerUri = "tcp://192.168.15.55:1883";

    private MutableLiveData<String> temperatura;
    private MutableLiveData<String> timestamp;
    private Sensor sensor;
    private Context context;

    private MqttAndroidClient client;

    public SensorDetailViewModel(){
        temperatura = new MutableLiveData<>();
        timestamp = new MutableLiveData<>();
        client = null;
        sensor = null;

    }

    public MutableLiveData<String> getTemperatura() {
        return temperatura;
    }

    public MutableLiveData<String> getTimestamp() {
        return timestamp;
    }

    public void setSensor(Sensor sensor, Context context) {
        this.sensor = sensor;
        this.context = context;
    }

    public void connect(){
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, brokerUri,clientId);
        try {
            client.connect().setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("###", "Conexão Estabelecida");
                    try {
                        client.subscribe(sensor.getTopic(),0);
                        client.setCallback(new MqttCallback() {
                            @Override
                            public void connectionLost(Throwable cause) {

                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {
                                String response = new String(message.getPayload(), StandardCharsets.UTF_8);
                                String[] array = response.split(";");
                                temperatura.postValue(array[0]);
                                timestamp.postValue(array[1]);
                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



//    @Override
//    public void connectionLost(Throwable cause) {
//
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//
//    }
}

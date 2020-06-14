package org.techtown.firedetection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity {


    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            mqttClient = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId(), new MemoryPersistence());

            try {
                //showMessage();
                //mqttClient.subscribe("hello/fire");
                mqttClient.connect();
                System.out.println("연결완료");
                //showMessage();
                mqttClient.subscribe("hello/fire");

                mqttClient.setCallback(new MqttCallback() {
                                           @Override
                                           public void connectionLost(Throwable cause) {

                                           }

                                           @Override
                                           public void messageArrived(String topic, MqttMessage message) throws Exception {
                                               System.out.println("메세지받음");
                                               runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       showMessage();
                                                   }
                                               });
                                           }

                                           @Override
                                           public void deliveryComplete(IMqttDeliveryToken token) {

                                           }
                                       }
                );

                //mqttClient.publish("JJYHELLO", new MqttMessage("JJYHELLO".getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }}

    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("화재가 인식되었습니다");
        builder.setMessage("실시간 영상을 확인하시겠습니까?");
        //builder.setIcon();



        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://114.108.69.93:8888/"));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "메인 화면으로 돌아갑니다";
                //textView.setText(message);
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /*
    private MqttClient client = new MqttClient("114.108.98.48:1883", MqttClient.generateClientId(), new MemoryPersistence());

    try{
        client.connect();
        client.publish("test", new MqttMessage(new String("Hello Mqtt!").getBytes()));
    } catch (MqttException ex) {
        ex.printStackTrace();
    }
*/



    public void onButton1Clicked(View view){
        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("http://114.108.69.93:8888/"));
        startActivity(intent);
    }









}

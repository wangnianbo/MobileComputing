package com.mobilecomputing.game.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mobilecomputing.game.AndroidLauncher;
import com.mobilecomputing.game.R;
import com.mobilecomputing.game.slitherio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BluetoothFragment.BecomeServer, ChatFragment.Communication {
    ViewPager pager;
    MyPageAdapter adapter;
    BluetoothFragment bf;
    InputStream ServerInStream = null;
    OutputStream ServerOutStream = null;

    InputStream ClientInStream = null;
    OutputStream ClientOutStream = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    private static final UUID MY_UUID = UUID.randomUUID();//.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ServerThread serverThread;
    private ClientThread clientThread ;
    private Boolean isSeverRunning = true;
    private final int MESSAGE_RECEIVED = 0;
    private final int CONNECTION_SUCCESSFUL = 1;
    private android.os.Handler handler_process = new android.os.Handler(){
        public void handleMessage(Message msg){
            if (msg.what==MESSAGE_RECEIVED){
                ChatFragment cf = (ChatFragment) adapter.getItem(1);
                cf.addText(msg.obj.toString());
            }else if (msg.what==CONNECTION_SUCCESSFUL){
                Toast.makeText(getApplicationContext(),"connect successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AndroidLauncher.class);
                BluetoothStream.ClientInStream = ClientInStream;
                BluetoothStream.ClientOutStream = ClientOutStream;
                BluetoothStream.ServerInStream = ServerInStream;
                BluetoothStream.ServerOutStream = ServerOutStream;
                BluetoothStream.isNetGame = true;
                slitherio.jumpInMultiplayer=true;

                startActivity(intent);
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        bf = (BluetoothFragment) adapter.getItem(0);
    }

    @Override
    public void becomeServer(BluetoothAdapter bluetoothAdapter) {
        serverThread = new ServerThread();
        serverThread.start();
    }

    @Override
    public void ConnectToDevice(BluetoothDevice device) {
        clientThread = new ClientThread(device);
        clientThread.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void sendData(String data) {
        StringBuffer sb = new StringBuffer();
        sb.append(data);
        sb.append("\n");
        if (ServerOutStream!=null){
            try {
                ServerOutStream.write(sb.toString().getBytes());
                ServerOutStream.flush();
                System.out.println("@ "+sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("@ Server sending fail");
            }
        }
        if (ClientOutStream!=null){
            try {
                ClientOutStream.write(sb.toString().getBytes());
                ClientOutStream.flush();
                System.out.println("@ "+sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("@ Client sending fail");
            }


        }
    }

    private class ClientThread extends Thread{
        private final BluetoothSocket mySocket;

        public ClientThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e("Bluetooth", "Could not connect");
            }
            mySocket = tmp;
        }
        public void run() {
            try {
                mySocket.connect();
                Message message = new Message();
                message.what=CONNECTION_SUCCESSFUL;
                handler_process.sendMessage(message);
            } catch (IOException e) {
                System.out.println("@ closed"+e.getMessage());
                try {
                    mySocket.close();
                } catch (IOException e1) {
                    this.destroy();
                }
            }

            String line = "";

            try {
                ClientInStream = mySocket.getInputStream();
                ClientOutStream = mySocket.getOutputStream();
                 /*
                BufferedReader br = new BufferedReader(new InputStreamReader(ClientInStream));
                System.out.println("@ "+ClientInStream);
                while ((line = br.readLine()) != null) {
                    Message message = new Message();
                    message.obj = line;
                    message.what = MESSAGE_RECEIVED;
                    handler_process.sendMessage(message);

                }
                 */
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("@ client exception");
            }

        }
    }

    private class ServerThread extends Thread{
        private final BluetoothServerSocket serverSocket;

        private ServerThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bf.mBluetoothAdapter.listenUsingRfcommWithServiceRecord("myServer", MY_UUID);
                System.out.println("@ UUID "+MY_UUID);
            } catch (IOException e) {
                Log.e("Bluetooth", "Server establishing failed");
            }
            serverSocket = tmp;
        }
        public void run() {
            Log.e("Bluetooth", "Begin waiting for connection");
            BluetoothSocket connectSocket = null;
            String line = "";
            InputStream inStream = null;
            OutputStream outStream = null;
            while (true) {
                    try {
                        System.out.println("@ w8 for connection");
                        connectSocket = serverSocket.accept();
                        bf.mBluetoothAdapter.cancelDiscovery();
                        System.out.println("@ Connection Successfully");
                        Message message = new Message();
                        message.what=CONNECTION_SUCCESSFUL;
                        handler_process.sendMessage(message);
                    } catch (IOException e) {
                        Log.e("Bluetooth", "Connection failed");
                        break;
                    }

                    try {
                        System.out.println("@ line1"+line);
                        inStream = connectSocket.getInputStream();
                        outStream = connectSocket.getOutputStream();
                        ServerInStream =inStream;
                        ServerOutStream = outStream;
                         /*
                        BufferedReader br = new BufferedReader(new InputStreamReader(ServerInStream));
                        while ((line = br.readLine()) != null) {
                            System.out.println("@message "+line);
                           Message message = new Message();
                            message.what=MESSAGE_RECEIVED;
                            message.obj=line;
                            handler_process.sendMessage(message);
                        }
                        */
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("@ exception");
                        break;
                    }


            }

        }

    }

    class MyPageAdapter extends FragmentStatePagerAdapter{
        private BluetoothFragment bluetoothFragment;
        private ChatFragment chatFragment;
        public MyPageAdapter(FragmentManager fm) {
            super(fm);
            this.bluetoothFragment = new BluetoothFragment();
            this.chatFragment = new ChatFragment();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return bluetoothFragment;
                case 1:
                    return chatFragment;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

}

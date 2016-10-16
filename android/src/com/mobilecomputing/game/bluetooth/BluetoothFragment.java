package com.mobilecomputing.game.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mobilecomputing.game.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xun Hu on 17/08/2016.
 */
public class BluetoothFragment extends Fragment {
    ListView listView;
    EditText editText;
    List<String> bluetoothList = new ArrayList<>();
    List<BluetoothDevice> devices = new ArrayList<>();
    BluetoothAdapter mBluetoothAdapter;
    Button button;
    ArrayAdapter adapter;
    BecomeServer becomeServer;
    Boolean isServer = false;

    /**
     * Haddle Create event
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluetooth_fragment_layout,container,false);
        listView = (ListView) view.findViewById(R.id.lvBluetooth);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(devices.get(i).getName());
                becomeServer.ConnectToDevice(devices.get(i));
            }
        });
        button = (Button) view.findViewById(R.id.btnBluetooth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothList.clear();
                devices.clear();
                adapter.notifyDataSetChanged();
                startBluetooth();
                if (isServer==false){
                    becomeServer.becomeServer(mBluetoothAdapter);
                    isServer=true;
                }
            }
        });
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,bluetoothList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        becomeServer = (BecomeServer) activity;

    }


    /**
     * Enable bluetooth on Mobile Phone
     */
    public void startBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(btIntent);
        }
        mBluetoothAdapter.startDiscovery();
        System.out.println("@ start");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);
    }

    /**
     * Destroy object!
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            getActivity().unregisterReceiver(mReceiver);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

    }

    /**
     * Broadcast message receiver
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                long timestamp = System.currentTimeMillis();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int RSSI = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                int bondStateFlag = device.getBondState();
                String bondState = "";
                if (deviceName!=null){
                    bluetoothList.add(deviceName);
                    devices.add(device);
                    System.out.println("@ device "+deviceName);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    /**
     * Get Text
     * @param data
     */
    public void getText(String data){
        editText.setText(data);
    }

    /**
     * Server Interface
     */
    public interface BecomeServer{
        public void becomeServer(BluetoothAdapter bluetoothAdapter);
        public void ConnectToDevice(BluetoothDevice device);
    }
}

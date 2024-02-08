package com.imooc.baidumap0924;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ITEM_ID_SATELITE_MAP = 102;
    private static final int ITEM_LOCATION =103;
    private MapView mMapView;
    private static final int ITEM_ID_NORMAL_MAP = 101;
    private BaiduMap mBaiduMap;
    
    private LocationInstance mLocationInstance;

    private BDLocation mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();
        
        initData();
    }

    private void initData() {
        initLocationDetect();
    }

    private void initLocationDetect() {

        mLocationInstance = new LocationInstance(this,new LocationInstance.MyLocationListener(){
            @Override
            public void onReceiveLocation(BDLocation location) {
                super.onReceiveLocation(location);
                mLastLocation=location;
                Log.e(TAG, "onReceiveLocation: "+location.getAddrStr()+" , "+mLastLocation.getLatitude()+" , "+mLastLocation.getLongitude());
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mLocationInstance.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationInstance.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, ITEM_ID_NORMAL_MAP, 0, "切换为普通地图");
        menu.add(Menu.NONE, ITEM_ID_SATELITE_MAP, 0, "切换为卫星地图");
        menu.add(Menu.NONE, ITEM_LOCATION, 0, "定位我的位置");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case ITEM_ID_NORMAL_MAP:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case ITEM_ID_SATELITE_MAP:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case ITEM_LOCATION:
                //定义Maker坐标点
                LatLng point = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.navi_map_gps_locked);
//构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
//在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));

        }
        return super.onOptionsItemSelected(item);
    }
}
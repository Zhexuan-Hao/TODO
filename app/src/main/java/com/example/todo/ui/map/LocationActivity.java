package com.example.todo.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.amap.api.maps.AMap;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.model.BitmapDescriptorFactory;
//import com.amap.api.maps.model.MyLocationStyle;
import com.example.todo.NavigationDrawerActivity;
import com.example.todo.R;
import com.example.todo.Room.Entity.Event;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

public class LocationActivity extends AppCompatActivity {
    private WebView mWebView;
    private Button btn_map;
    private String positionname;//位置名
    private String rcity="";//城市
    private String address="未获取到位置";//详细地址
    private String latng="";//经纬度，纬度在前
    private String lat="";//纬度
    private String lng="";//经度
    private Uri uri;

    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        event = (Event) getIntent().getSerializableExtra("event");

        mWebView=findViewById(R.id.web_map);
        btn_map=findViewById(R.id.choose_button);
        String mUrl = "https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://callback&key=QGWBZ-FXLKH-HOZDV-WGZD2-74P36-INBBN&referer=todo";
        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.setVerticalScrollbarOverlay(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http://callback")) {
                    view.loadUrl(url);
                } else {
                    try {
                        //转utf-8编码
                        String decode = URLDecoder.decode(url, "UTF-8");
                        // LogUtil.i(decode);

                        //转uri，然后根据key取值
                        uri = Uri.parse(decode);
                        Log.e("address",uri.toString());
                        latng = uri.getQueryParameter("latng");//纬度在前，经度在后，以逗号分隔
                        String[] split = latng.split(",");
                        DecimalFormat decimalFormat=new DecimalFormat(".00");
                        lat=decimalFormat.format(Float.parseFloat(split[0]));//纬度
                        lng=decimalFormat.format(Float.parseFloat(split[1]));//经度
                        rcity = uri.getQueryParameter("city");//城市
                        address = uri.getQueryParameter("addr");//地理位置
                        positionname=uri.getQueryParameter("name");//地址名
                        //name是地址名，addr是详细地址，city是城市，latng是经纬度（不过纬度在前边）
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        mWebView.loadUrl(mUrl);

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LocationActivity.this,address.toString()+latng.toString(),Toast.LENGTH_LONG).show();
                System.out.println("信息："+uri.toString());
                Intent intent = new Intent(LocationActivity.this, NavigationDrawerActivity.class);
                intent.putExtra("id",1);
                event.setAddress(positionname+"("+address+")");
                event.setLatitude(Double.valueOf(lat));
                event.setLongitude(Double.valueOf(lng));
                intent.putExtra("city", rcity);
//                intent.putExtra("address", address);
//                intent.putExtra("lat", lat);
//                intent.putExtra("lng", lng);
                intent.putExtra("event", event);

                startActivity(intent);
                //intent.putExtra("city", rcity);
                //intent.putExtra("address", address);
                //intent.putExtra("lat", lat);
                //intent.putExtra("lng", lng);

                //MappingActivity.this.setResult(0,intent);
                //MappingActivity.this.finish();
            }
        });
    }
//
//    private AMap aMap;
//    private MapView mapView;
//    private RadioGroup mGPSModeGroup;
//
//    private TextView mLocationErrText;
//    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
//    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
//        setContentView(R.layout.activity_location);
//        mapView = (MapView) findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);// 此方法必须重写
//        init();
//    }
//
//    /**
//     * 初始化
//     */
//    private void init() {
//        if (aMap == null) {
//            aMap = mapView.getMap();
//            setUpMap();
//        }
//        mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
//        mGPSModeGroup.setVisibility(View.GONE);
//        mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
//        mLocationErrText.setVisibility(View.GONE);
//    }
//
//    /**
//     * 设置一些amap的属性
//     */
//    private void setUpMap() {
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        setupLocationStyle();
//    }
//
//    /**
//     * 设置自定义定位蓝点
//     */
//    private void setupLocationStyle(){
//        // 自定义系统定位蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
//                fromResource(R.drawable.gps_point));
//        // 自定义精度范围的圆形边框颜色
//        myLocationStyle.strokeColor(STROKE_COLOR);
//        //自定义精度范围的圆形边框宽度
//        myLocationStyle.strokeWidth(5);
//        // 设置圆形的填充颜色
//        myLocationStyle.radiusFillColor(FILL_COLOR);
//        // 将自定义的 myLocationStyle 对象添加到地图上
//        aMap.setMyLocationStyle(myLocationStyle);
//    }
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
}
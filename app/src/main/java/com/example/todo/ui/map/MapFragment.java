package com.example.todo.ui.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

public class MapFragment extends Fragment {

//    private WebView mWebView;
//    private Button btn_map;
//    private String positionname;//位置名
//    private String rcity="";//城市
//    private String address="未获取到位置";//详细地址
//    private String latng="";//经纬度，纬度在前
//    private String lat="";//纬度
//    private String lng="";//经度
//    private Uri uri;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mapping);
//
//        mWebView=findViewById(R.id.web_map);
//        btn_map=findViewById(R.id.choose_button);
//        String mUrl = "https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://callback&key=LCPBZ-SN6WT-PLAXC-VG5NO-NE7UF-BUB5C&referer=todolist";
//        WebSettings settings = mWebView.getSettings();
//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        settings.setSupportMultipleWindows(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setSavePassword(false);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
//        settings.setAllowFileAccess(false);
//        settings.setTextSize(WebSettings.TextSize.NORMAL);
//        mWebView.setVerticalScrollbarOverlay(true);
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (!url.startsWith("http://callback")) {
//                    view.loadUrl(url);
//                } else {
//                    try {
//                        //转utf-8编码
//                        String decode = URLDecoder.decode(url, "UTF-8");
//                        // LogUtil.i(decode);
//
//                        //转uri，然后根据key取值
//                        uri = Uri.parse(decode);
//                        Log.e("address",uri.toString());
//                        latng = uri.getQueryParameter("latng");//纬度在前，经度在后，以逗号分隔
//                        String[] split = latng.split(",");
//                        DecimalFormat decimalFormat=new DecimalFormat(".00");
//                        lat=decimalFormat.format(Float.parseFloat(split[0]));//纬度
//                        lng=decimalFormat.format(Float.parseFloat(split[1]));//经度
//                        rcity = uri.getQueryParameter("city");//城市
//                        address = uri.getQueryParameter("addr");//地理位置
//                        positionname=uri.getQueryParameter("name");//地址名
//                        //name是地址名，addr是详细地址，city是城市，latng是经纬度（不过纬度在前边）
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return true;
//            }
//        });
//        mWebView.loadUrl(mUrl);
//
//        btn_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MappingActivity.this,address.toString()+latng.toString(),Toast.LENGTH_LONG).show();
//                System.out.println("信息："+uri.toString());
//                Intent intent = new Intent(MappingActivity.this, HomeFragment.class);
//                intent.putExtra("name", positionname);
//                startActivity(intent);
//                //intent.putExtra("city", rcity);
//                //intent.putExtra("address", address);
//                //intent.putExtra("lat", lat);
//                //intent.putExtra("lng", lng);
//
//                //MappingActivity.this.setResult(0,intent);
//                //MappingActivity.this.finish();
//            }
//        });
//    }

}

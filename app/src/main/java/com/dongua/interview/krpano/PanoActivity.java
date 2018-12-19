package com.dongua.interview.krpano;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.common.BaseActivity;
import com.dongua.interview.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import butterknife.BindView;

/**
 * Created by lewis.weng on 2018/5/21.
 */
public class PanoActivity extends BaseActivity {

    @BindView(R.id.wv_pano)
    WebView mWebView;

    @Override
    public int getLayoutID() {
        return R.layout.activity_pano;
    }

    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + File.separator + "dji_display";
    public static final String WWW_FOLDER = ROOT_PATH + File.separator + "www" + File.separator;
    public static final String PANOS_FOLDER = WWW_FOLDER + "panos" + File.separator;

    String tourPath = "/storage/emulated/0/dji_display/www/tour.xml";
    String photoPath = "/storage/emulated/0/dji_display/www/panos";


    @Override
    public void init() {

        List<String> tmp = new ArrayList<>();
        tmp.add("/storage/emulated/0/dji_display/photo/gugong.jpg");
        tmp.add("/storage/emulated/0/dji_display/photo/dji.jpg");



        for (String path:tmp){
            try {
                FileUtil.copySDFile(path,photoPath+File.separator+getName(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        editXML(tmp);



//        File f = new File(WWW_FOLDER);
//        if (!f.exists()) {
//            try {
//                FileUtil.copyAssets(this, "www", WWW_FOLDER);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        initWebViewSettings();

////        editFile(tmp);
//
////        for (String path : tmp) {
////            try {
////                FileUtil.copy(path, PANOS_FOLDER + getName(path));
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////
////        }
//        String s = "file:///"+WWW_FOLDER+"krpano.html";
//
//        Log.i("asdasd", getExternalCacheDir().getAbsolutePath());
//        Log.i("asdasd", s);
//        mWebView.loadUrl(s);

    }

    private void editXML(List<String> pathList) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            //对旧的scene进行处理
            document = builder.parse(new File(tourPath));


            Element root = document.getDocumentElement();

            NodeList scenes = root.getElementsByTagName("scene");
            int len = scenes.getLength();

            for (int i=0;i<len;i++){
                Node n = scenes.item(i);
                n.getParentNode().removeChild(n);
            }
//            while (len >= 0){
//                Node n = scenes.item(len--);
//                n.getParentNode().removeChild(n);
//
//            }
//                scenes.item(0).removeChild(scenes.item(0));

            for (String path : pathList) {
                Element scene = document.createElement("scene");
                scene.setAttribute("name", getName(path));
                Element image = document.createElement("image");
                image.setAttribute("type", "SPHERE");
                Element sphere = document.createElement("sphere");
                sphere.setAttribute("url", "panos/" + getName(path));
                image.appendChild(sphere);
                scene.appendChild(image);
                root.appendChild(scene);
            }


            TransformerFactory tfs = TransformerFactory.newInstance();
            //创建Transformer对象
            Transformer tf = tfs.newTransformer();
            //将document输出到输出流中。
            tf.transform(new DOMSource(document), new StreamResult(new FileOutputStream(new File(tourPath))));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getName(String path) {
        return path.substring(path.lastIndexOf("/") + 1, path.length());
    }

    private void initWebViewSettings() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setAllowFileAccess(true);
        webSetting.setNeedInitialFocus(true);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //新加的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSetting.setAllowUniversalAccessFromFileURLs(true);
        }
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setAllowUniversalAccessFromFileURLs(true);
    }
}

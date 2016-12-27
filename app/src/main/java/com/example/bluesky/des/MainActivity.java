package com.example.bluesky.des;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.example.bluesky.des.BinaryCastUtils.parseByte2HexStr;

public class MainActivity extends AppCompatActivity {
    public String data = "阿狸好萌";
    public String password ="12345678";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void urlEncodeAnDecode(View view ){
        try {
            String str = URLEncoder.encode(data,"utf-8");
            Log.e("======","urlEncodeAnDecode编码的结果是："+ str);
            String str1 = URLDecoder.decode(str,"utf-8");
            Log.e("======","urlEncodeAnDecode解码的结果是："+ str1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
    public void base64(View view ){
        byte [] b = Base64.encode(data.getBytes(),Base64.DEFAULT);
        String str = new String(b);
        Log.e("======","第一种base64编码的结果是："+ str);
        byte[] b1= Base64.decode(str,Base64.DEFAULT);
        Log.e("======","第一种base64解码的结果是："+ new String(b1));

        String str1 = Base64.encodeToString(data.getBytes(),Base64.DEFAULT);
        Log.e("======","第二种base64编码的结果是："+ str1);
        byte[] b2 = Base64.decode(str1.getBytes(),Base64.DEFAULT);
        Log.e("======","第二种base64解码的结果是："+ new String(b2));
    }
    public void md5(View view){
        //MD5加密只能加密不能解密,一般用于用户密码加密后比对字符串信息，不直接给出用户名密码
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(data.getBytes());
            Log.e("======","md5加密的结果是1："+Base64.encodeToString(bytes,Base64.DEFAULT));
            Log.e("======","md5加密的结果是2："+ parseByte2HexStr(bytes));
            //        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String s = new BigInteger(1, messageDigest.digest()).toString(16);
            Log.e("======","md5加密的结果是3："+s);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public void des(View view){
        try {
            Log.e("===des加密结果是==", DESUtils.encrypt(data, password));
            Log.e("==des解密结果是===", DESUtils.decrypt(DESUtils.encrypt(data, password), password));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void aes(View view){
       byte[] encode = AESUtils.encrypt(data,password);
       String str =  Base64.encodeToString(encode,Base64.DEFAULT);
        Log.e("======", "aes加密的结果是："+str);
        //解密
        byte[] decode = AESUtils.decrypt(Base64.decode(str.getBytes(),Base64.DEFAULT),password);
        Log.e("======", "aes解密的结果是："+new String(decode));
    }
    public void rsa(View view){

        try {
            Map<String, Object>  map = RSAUtils01.genKeyPair();
            byte[] b = RSAUtils01.encrypt( RSAUtils01.getPublicKey(map),data.getBytes());
           String str =  BinaryCastUtils.parseByte2HexStr(b);
            Log.e("======", "rsa加密的结果是："+str);
           byte[] b1 = RSAUtils01.decrypt(RSAUtils01.getPrivateKey(map),BinaryCastUtils.parseHexStr2Byte(str));
            Log.e("======", "rsa解密的结果是："+new String(b1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

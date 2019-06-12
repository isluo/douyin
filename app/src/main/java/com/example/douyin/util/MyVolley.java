package com.example.douyin.util;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.douyin.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 01 on 2018/12/7.
 */

public class MyVolley {
    static String path = "http://%s/wla/dyin/%s";
    public enum B{
        //登陆
        login("{\"username\":\"%s\",\"password\":\"%s\"}"),
        //注册
        register("{\"username\":\"%s\",\"password\":\"%s\",\"gender\":\"%s\",\"introduce\":\"%s\",\"nname\":\"%s\"}"),
        //修改用户信息
        edit("{\"username\":\"%s\",\"password\":\"%s\",\"gender\":\"%s\",\"introduce\":\"%s\",\"nname\":\"%s\"}"),
        //视频点赞
        updateVideoNum("{\"videoid\":\"%s\"}"),
        //获取所有视频
        findAllVideo("{\"userid\":\"%s\"}"),
        //用户查看自己的视频
        findVideoByUserID("{\"userid\":\"%s\"}"),
        //用户删除自己的视频
        removeVideo("{\"userid\":\"%s\",\"videoid\":\"%s\"}"),
        //添加评论
        addPl("{\"videoid\":\"%s\",\"userid\":\"%s\",\"comment\":\"%s\"}"),
        //给评论点赞
        updatePlNum("{\"id\":\"%s\"}"),
        //获取某视频的所有评论内容
        selectPlByVideoId("{\"videoid\":\"%s\"}"),
        //删除评论
        removePl("{\"id\":\"%s\",\"userid\":\"%s\"}"),
        //关注
        addGz("{\"userid\":\"%s\",\"userid2\":\"%s\"}"),
        //用户查看自己关注的用户
        findByUser("{\"userid\":\"11\"}"),
        //取消关注
        removeGz("{\"id\":\"%s\"}")
        ;

        public String str;

        B(String str) {
            this.str = str;
        }

        public C exec(Object...obj){
            return new C(this.name(),String.format(str,obj));
        }
    }

    public static class C{
        private String methodName;//方法名
        private String param;   //json参数
        public C(String methodName,String param){
            this.methodName = methodName;
            this.param = param;
        }
        public void exec(final Object clazz, final Object...obj){
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JsonObjectRequest.Method.POST,
                        String.format(path, App.ip, methodName),
                        new JSONObject(param),
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //获取方法名
                        for (Method method : clazz.getClass().getDeclaredMethods()) {
                            if(methodName.equals(method.getName())){
                                if(obj.length == 0){
                                    try {
                                        method.invoke(clazz,jsonObject);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Object[] obj2 = new Object[obj.length+1];
                                    obj2[0] = jsonObject;
                                    for (int i = 0; i < obj.length; i++) {
                                        obj2[i+1] = obj[i];
                                    }
                                    try {
                                        Object obj3 = obj2;
                                        method.invoke(clazz,obj3);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        Log.e("onErrorResponse",  String.format(path, App.ip, methodName)+"====="+"onErrorResponse: " + methodName + " : " + param);
                    }
                });
                getMyVolley().requset.add(jsonObjectRequest);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    static RequestQueue requset = Volley.newRequestQueue(App.context);

    private MyVolley(){}
    public static MyVolley myVolley = null;

    public static MyVolley getMyVolley(){
        if(myVolley == null){
            synchronized (MyVolley.class){
                if(myVolley == null){
                    myVolley = new MyVolley();
                }
            }
        }
        return myVolley;
    }
}

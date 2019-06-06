package com.example.douyin.wenl;

import android.util.Log;

import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.pojo.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDate {


    /**
     * 注册
     *          MyVolley.B.register.exec("asdfadghaa","aaaaaaaa","男","123435asdf","nnmae").exec(this);
     *                 public void register(JSONObject jsonObject){
     *                 Map<String,Object> maps = GetDate.getRegister(jsonObject);
     *                 Log.e("AAAAA",maps.get("msg").toString() );
     *                 if((boolean)maps.get("msg")){
     *                     Log.e("BBB","注册成功" );
     *                 }else{
     *                     Log.e("CCC","注册失败"+maps.get("ERROR").toString() );
     *
     *                 }
     *             }
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> getRegister(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                maps.put("msg",true);
            }else{
                maps.put("msg",false);
                maps.put("ERROR",jsonObject.getString("ERROR"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maps;
    }







    /**
     * {
     *     "msg": "1",
     *     "user": {
     *         "id": 28,
     *         "username": "s3333333333",
     *         "password": "11111111111",
     *         "gender": "男",
     *         "introduce": "简介",
     *         "head": null,
     *         "nname": "昵称"
     *     }
     * }
     * 登录
     *      public void login(JSONObject jsonObject){
     *         Map<String,Object> maps = GetDate.getLogin(jsonObject);
     *         Log.e("AAAAA",maps.get("msg").toString() );
     *         if((boolean)maps.get("msg")){
     *             User user = (User) maps.get("user");
     *             Log.e("BBB","登录成功" );
     *             Log.e("user",""+ user.toString());
     *         }else{
     *             Log.e("CCC","登录失败"+maps.get("ERROR").toString() );
     *
     *         }
     *     }
     */
    public static Map<String,Object> getLogin(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        User user = null;
        try {
            if(jsonObject.getString("msg").equals("1")){
                JSONObject object = jsonObject.getJSONObject("user");
                user = new User();
                user.setId(object.getInt("id"));
                user.setUsername(object.getString("username"));
                user.setGender(object.getString("gender"));
                user.setIntroduce(object.getString("introduce"));
                if(!object.getString("head").equals("null")){
                    user.setHead(object.getString("head"));
                }
                user.setNname(object.getString("nname"));

                maps.put("msg",true);
                maps.put("user",user);
            }else{
                maps.put("msg",false);
                maps.put("ERROR",jsonObject.getString("ERROR"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maps;
    }

    /**
     * 修改信息
     *
     *                  MyVolley.B.edit.exec("asdfadghaa1","aaaaaaaa","女","111111111","NNAME").exec(this);
     *
     *                 public void edit(JSONObject jsonObject){
     *                 Map<String,Object> maps = GetDate.getEdit(jsonObject);
     *                 Log.e("AAAAA",maps.get("msg").toString() );
     *                 if((boolean)maps.get("msg")){
     *                     Log.e("BBB","修改信息成功" );
     *                 }else{
     *                     Log.e("CCC","修改信息失败"+maps.get("ERROR").toString() );
     *                 }
     *             }
     */
    public static Map<String,Object> getEdit(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                maps.put("msg",true);
            }else{
                maps.put("msg",false);
                maps.put("ERROR",jsonObject.getString("ERROR"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maps;
    }




}

package com.example.douyin.wenl;

import android.util.Log;

import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.pojo.Gz;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.User;
import com.example.douyin.wenl.pojo.Video;
import com.example.douyin.wenl.pojo.Videos;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import org.json.JSONArray;
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
     *                }
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

    public static Map<String,Object> getEditPwd(JSONObject jsonObject){
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
     * 视频点赞
     * @param jsonObject
     * @return
     *            MyVolley.B.updateVideoNum.updateVideoNum("17").exec(this);
     *              public void updateVideoNum(JSONObject jsonObject){
     *              Map<String,Object> maps = GetDate.updateVideoNum(jsonObject);
     *              Log.e("AAAAA",maps.get("msg").toString() );
     *              if((boolean)maps.get("msg")){
     *                  Log.e("BBB","点赞成功" );
     *              }else{
     *                Log.e("CCC","点赞失败"+maps.get("ERROR").toString() );
     *              }
     *            }
     *
     */
    public static Map<String,Object> updateVideoNum(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                maps.put("msg",true);
            }else{
                maps.put("msg",false);
                maps.put("ERROR","视频不存在");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maps;
    }

    /**
     * 获取所有视频
     * @param jsonObject
     * @return
     *
     *            MyVolley.B.findAllVideo.exec(App.user).exec(this);
     *
     *           public void findAllVideo(JSONObject jsonObject){
     *         Map<String,Object> maps = GetDate.findAllVideo(jsonObject);
     *         Log.e("AAAAA",maps.get("msg").toString() );
     *         if((boolean)maps.get("msg")){
     *             List<Video> lists = (List<Video>) maps.get("list_video");
     *             for (Video video : lists) {
     *                 Log.e("video",video.toString());
     *             }
     *             Log.e("BBB","获取视频成功" );
     *         }else{
     *             Log.e("CCC","修改信息失败"+maps.get("ERROR").toString() );
     *         }
     *      }
     */
    public static Map<String,Object> findAllVideo(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                JSONArray array = jsonObject.getJSONArray("lostType");
                List<Videos> list_video = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.get(i).toString());
                    Videos video = new Videos();
                    video.setHead(ImgPath.getImgs(object.getString("head")));
                    video.setDz(object.getLong("dz"));
                    video.setUserid(object.getString("userid"));
                    video.setVideoid(object.getLong("videoid"));
                    video.setVideoIntro(object.getString("videoIntro"));
                    video.setVideopath(object.getString("videopath"));
                    list_video.add(video);
                }
                maps.put("list_video",list_video);
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
     * 根据用户id获取视频
     * @param jsonObject
     * @return
     *
     *            MyVolley.B.findVideoByUserID.exec(App.user).exec(this);
     *
     *           public void findAllVideo(JSONObject jsonObject){
     *          Map<String,Object> maps = GetDate.findVideoByUserID(jsonObject);
     *         Log.e("AAAAA",maps.get("msg").toString() );
     *         if((boolean)maps.get("msg")){
     *             List<Video> lists = (List<Video>) maps.get("list_video");
     *             for (Video video : lists) {
     *                 Log.e("video",video.toString());
     *             }
     *             Log.e("BBB","获取视频成功" );
     *         }else{
     *             Log.e("CCC","修改信息失败"+maps.get("ERROR").toString() );
     *         }
     *      }
     */
    public static Map<String,Object> findVideoByUserID(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                JSONArray array = jsonObject.getJSONArray("lostType");
                List<Video> list_video = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.get(i).toString());
                    Video video = new Video();
                    video = new Gson().fromJson(object.toString(),Video.class);
                    list_video.add(video);
                }
                maps.put("list_video",list_video);
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
     * 根据用户id 视频id 删除视频
     *
     *       MyVolley.B.removeVideo.exec(App.user,18).exec(this);
     *
     *        public void removeVideo(JSONObject jsonObject){
     *          Map<String,Object> maps = GetDate.removeVideo(jsonObject);
     *       Log.e("AAAAA",maps.get("msg").toString() );
     *       if((boolean)maps.get("msg")){
     *          Log.e("BBB","删除成功" );
     *          }else{
     *            Log.e("CCC","删除成功"+maps.get("ERROR").toString() );
     *          }
     *         }
     */
    public static Map<String,Object> removeVideo(JSONObject jsonObject){
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
     *
     * 添加评论     MyVolley.B.removeVideo.exec(App.user,18).exec(this);
     * @param jsonObject
     * @return
     *
     *
     */
    public static Map<String,Object> addPl(JSONObject jsonObject){
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
     * 评论点赞
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> updatePlNum(JSONObject jsonObject){
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
     *
     * 根据视频id获取评论
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> selectPlByVideoId(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                maps.put("msg",true);
                JSONArray array = jsonObject.getJSONArray("lostType");
                List<Pl> list_pl = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.get(i).toString());
                    Pl pl = new Pl();
                    pl = new Gson().fromJson(object.toString(),Pl.class);
                    list_pl.add(pl);
                }
                maps.put("list_pl",list_pl);
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
     * 根据id   用户id删除pl
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> removePl(JSONObject jsonObject){
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
     * 添加关注
     * @param jsonObject
     * @return
     */

    public static Map<String,Object> addGz(JSONObject jsonObject){
        Map<String,Object> maps = new HashMap<>();
        try {
            if(jsonObject.getString("msg").equals("1")){
                maps.put("msg",true);
                JSONArray array = jsonObject.getJSONArray("lostType");
                List<Gz> list_gz = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = new JSONObject(array.get(i).toString());
                    Gz gz = new Gz();
                    gz = new Gson().fromJson(object.toString(),Gz.class);
                    list_gz.add(gz);
                }
                maps.put("list_gz",list_gz);
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
     * 根据ｉｄ　取消关注        {"id":"13"}
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> removeGz(JSONObject jsonObject){
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

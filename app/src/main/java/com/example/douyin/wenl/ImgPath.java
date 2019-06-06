package com.example.douyin.wenl;

import com.example.douyin.App;

public class ImgPath {
    /**
     *      wenl121/wla/static/img/XXX
     *      wenl121/wla/static/imgs/XXX
     *      wenl121/wla/static/mp4/XXX.mp4
     *      wenl121/wla/static/mp4s/XXX.jpg
     */

    private static String rootPath = App.ip+"/wla/static/";

    /**
     * 获取图片原图
     * @param filename
     * @return
     */
    public static String getImg(String filename){
        return rootPath+"img/"+filename;
    }

    /**
     * 获取图片缩略图
     * @param filename
     * @return
     */
    public static String getImgs(String filename){
        return rootPath+"imgs/"+filename;
    }

    /**
     * 获取MP4路径
     * @param filename
     * @return
     */
    public static String getMp4(String filename){
        return rootPath+"mp4/"+filename+".mp4";
    }

    /**
     * 获取MP4缩略图
     * @param filename
     * @return
     */
    public static String getMp4s(String filename){
        return rootPath+"mp4s/"+filename+".jpg";
    }

}

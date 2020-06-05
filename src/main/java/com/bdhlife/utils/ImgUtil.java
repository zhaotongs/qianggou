package com.bdhlife.utils;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ImgUtil {
    /***
     * 上传图片
     *
     * @param file 数组是接收多个base64
     * @param imgFilePath 存在硬盘的地址 "E:\\BBS\\+业务id\\"
     * @return imagePath[i]=E:\BBS\业务id\a95dd0e9-1556-45f8-aeb2-d5e17ca673d2.jpg
     */
    public static  String[] savePic(String file, String imgFilePath )  {
        System.out.println("接到的值："+file.toString());
        String[] field = null;
        if (file != null && file.length() > 0) {
            if(file.split(",").length>1){
            }
            field = file.split(",");
        }
        String[] sArray=file.split(",");
        String[] imagePath=new String[sArray.length ];
        if (StringUtils.isNotBlank(file)) { // 图像数据为空
            String base64Pic = "";
            try {
                for (int f = 0; f<sArray.length; f++) {
                    //接收图片数组
                    base64Pic = sArray[f];
                    BASE64Decoder decoder = new BASE64Decoder();
                    String baseValue = base64Pic.replaceAll(" ", "+");//前台在用Ajax传base64值的时候会把base64中的+换成空格，所以需要替换回来。
                    // byte[] b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", ""));//去除base64中无用的部分
                    byte[] b = decoder.decodeBuffer(baseValue);//去除base64中无用的部分
                    // base64Pic = base64Pic.replace("base64,", "");
                    String fileName = UUID.randomUUID().toString()+ ".jpg";
                    for (int i = 0; i < b.length; ++i) {
                        if (b[i] < 0) {// 调整异常数据
                            b[i] += 256;
                        }
                    }
                    File newFile = new File(imgFilePath,fileName);
                    //如果路径不存在，新建
                    if (!newFile.exists()) {
                        newFile.getParentFile().mkdirs();
                        //创建文件
                        newFile.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(newFile);
                    out.write(b);
                    out.flush();
                    out.close();
                    String newss=imgFilePath+fileName;
                    for (int a = 0; a < imagePath.length; a++) {
                        if(imagePath[a]==null) {
                            imagePath[a] = newss;
                            System.out.println("添加成功" + newss); //①
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("图片添加失败");
            }
        }
        return  imagePath;
    }

    /***
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径  "E:\\BBS\\业务id";
     */
    public  static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    private static  boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }


}

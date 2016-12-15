package com.demo.fuzhucheng.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ${符柱成} on 2016/8/27.
 */

//保存bitmap到本地文件,用途是可以配合鲁班压缩框架，进行对本地图片压缩然后上传
public class SavePhoto {
    public static boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File("./sdcard/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File("./sdcard/DCIM/Camera/" + bitName + ".png");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

}

package com.home.app.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.os.Environment;
import android.util.Log;

/**
 * 缓存数据到SD卡
 * @author Ritty
 *
 */
public class ObjectSerializable {
	
	
	private static void deleteFile() {
		try {
			File file = new File(getSDCardPath() + "/home/info.tmp");
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 将对象序列化到磁盘文件中
	 * @param o
	 * @param path : 例如：/u1wan/yaonimei.tmp
	 */
	public static void writeObject(Object o) {
		deleteFile();
		ObjectOutputStream oos = null;
		FileOutputStream os = null;
		try {
			File file = new File(getSDCardPath() + "/home/info.tmp");
					
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			} else {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
            oos = new ObjectOutputStream(os);
            oos.writeObject(o);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(oos, os);
		}
	}
	
	/**
     *反序列化,将磁盘文件转化为对象
     *@paramf
     *@return
     *@throwsException
     */
    public static Object readObject() {
    	File file = new File(getSDCardPath() + "/home/info.tmp");
    	InputStream is = null;
    	ObjectInputStream ois = null;
       try {
    	   is = new FileInputStream(file);
           ois = new ObjectInputStream(is);
           return ois.readObject();
       } catch (Exception e) {
    	   e.printStackTrace();
    	   return null;
       } finally {
    	   close(ois, is);
       }
    }
    
    /**
     * 获取外置SD卡路径
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure", "");
                        return result;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    Log.e("CommonUtil:getSDCardPath", "命令执行失败!");
                }
            }
            if (inBr != null)
            	inBr.close();
            if (in != null)
            	in.close();
        } catch (Exception e) {
        	Log.e("CommonUtil:getSDCardPath", e.toString());
            return Environment.getExternalStorageDirectory().getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }
    
    
    private static void close(ObjectInputStream ois, InputStream is) {
    	try {
    		if (is != null)
    			is.close();
    		if (ois != null)
    			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void close(ObjectOutputStream oos, FileOutputStream os) {
    	try {
    		if (oos != null)
    			oos.close();
    		if (os != null)
    			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
}

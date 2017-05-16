package com.example.recyclerdirection.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 创建时间： 2017/3/13 15:03
 * 编写人：
 * 功能描述：安装辅助apk
 */
public class InstallAssistApk {

    private Context context;

    public InstallAssistApk(Context context) {
        this.context = context;
    }

    /**
     * @方法描述 安装辅助程序
     */
    public void installAppreceiverApp() {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    "com.example.appreceiver", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            // 启用安装新线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //KLog.e("assist", "未安装进行安装");
                    slientInstall(); // 未安装进行安装
                }
            }).start();
        } else {
            //KLog.e("assist", "已经安装");
        }
    }


    /**
     * 安装辅助apk
     *
     * @return
     */
    public int slientInstall() {
        createFile(); // 进行资源的转移 将assets下的文件转移到可读写文件目录下
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/appreceiver.apk");

        int result = install2(file.getPath());

        if (0 == result) {
            //KLog.i("assist","success");
        } else {
           // KLog.i("assist","failed");
        }
        return result;
    }

    private int install2(String filePath) {

        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0
                || (file = new File(filePath)) == null
                || file.length() <= 0 || !file.exists() || !file.isFile()) {
            return 1;
        }

        String[] args = {"pm", "install", "-r", filePath};
        ProcessBuilder processBuilder = new ProcessBuilder(args);

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        int result;
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String s;

            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = 2;
        } catch (Exception e) {
            e.printStackTrace();
            result = 3;
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
            result = 4;
        }

        // TODO should add memory is not enough here
        if (successMsg.toString().contains("Success")
                || successMsg.toString().contains("success")) {
            result = 0;
        }
        return result;
    }

    public void createFile() {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open("appreceiver.apk");
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/appreceiver.apk");
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }


}

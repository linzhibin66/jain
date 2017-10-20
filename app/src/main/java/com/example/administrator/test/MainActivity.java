package com.example.administrator.test;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.zip.ZipInputStream;

public class MainActivity extends Activity {
    MediaPlayer mediaPlayer;
    int[] music = new int[]{R.raw.dengguang1};
    Context context = MainActivity.this;
    SoundPool soundPool;
    BroadcastReceiver receiver;//下载广播

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Button bt_sound = (Button) findViewById(R.id.bt_sound);
        Button bt_stop = (Button) findViewById(R.id.bt_stop);
        Button bt_test = (Button) findViewById(R.id.bt_test);
        Button bt_jump = (Button) findViewById(R.id.bt_jump);
        Unzip("/mnt/sdcard/chejidoal/chejimusic.zip", "/mnt/sdcard/chejidoal/chejim/");

        boolean b = fileIsExists("/sdcard/chejidoal/");

        if (b) {
            Toast.makeText(context, "有存在", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "不存在", Toast.LENGTH_SHORT).show();
            downFile("http://oh0svz15g.bkt.clouddn.com/chejizip.zip");
        }
//         mediaPlayer = new MediaPlayer();

//        mediaPlayer = MediaPlayer.create(MainActivity.this, music[0]);
////        mediaPlayer.prepareAsync();
//        soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
//        soundPool.load(MainActivity.this, R.raw.dengguang1,1);

//        try {
//            mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.t15));
////            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        bt_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//           mediaPlayer.start();
                String url = "/sdcard/chaoche_22.ogg";
                new IsMediaPlayer(context, url).isplay();
            }
        });
        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashReport.testJavaCrash();
            }
        });
        bt_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context,RecyclerActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }


    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists() && f.list().length > 0) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    public void downFile(String url) {

        File destDir = new File("/cdcard/chejidoal");
        //先判断是否有之前下载的文件，有则删除，
        if (!destDir.exists()) {
            destDir.mkdirs();
        } else {
            File[] files = destDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    File appfile = new File(files[i].getPath());
                    appfile.delete();
                }
            }
            destDir.mkdirs();
        }
        final DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir("chejidoal", "cheji.zip");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDescription("语音文件正在下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setMimeType("application/vnd.android.package-archive");
        request.setAllowedOverRoaming(false);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
        final long refernece = dManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
//                                Intent install = new Intent(Intent.ACTION_VIEW);
//                                Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
//                                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(install);

//                    Unzip("/mnt/sdcard/chejidoal/cheji.zip", "/sdcard/chehjidoal");

                }
            }
        };
        registerReceiver(receiver, filter);
    }


    /**
     * 解压zip文件
     * */
    private static void Unzip(String zipFile, String targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            java.util.zip.ZipEntry entry; //每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    Log.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    //删除文件
    public static void delFile(String fileName){
        File file = new File(fileName);
        if(file.isFile()){
            file.delete();
        }
        file.exists();
    }
}

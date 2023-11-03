package com.tencent.could.codedemo.util;

import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 本地写文件的帮助类
 *
 * @author jerrydong
 * @since 2021/3/26
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    private static final String DIR_NAME = "cloud-huiyan";
    private static final String VIDEO_FILE_DIR = "result";

    // 文件的扩展名
    private static final String FILE_END = ".hy";
    private static final String DEFAULT_NAME = "hy_result_";

    private static final String FILE_SELECT_DATA = "select_data_";
    private static final String FILE_COMPILE_RESULT = "compile_result_";
    private static final String FILE_BYTE_DATA = "compile_byte_code_";

    /**
     * 将结果写到文件里面
     *
     * @param data 结果数据
     */
    public static void writeResultToFile(final Context context, final String data, final boolean isCompile) {
        ThreadPoolUtil.getInstance().addWork(() -> {
            String fileType = FILE_COMPILE_RESULT;
            if (!isCompile) {
                fileType = FILE_SELECT_DATA;
            }
            String fileName = DEFAULT_NAME + fileType + System.currentTimeMillis() + FILE_END;
            String cachePath = DIR_NAME + File.separator + VIDEO_FILE_DIR + File.separator + fileName;
            String filePath = context.getExternalFilesDir(null) + File.separator + cachePath;
            File file = new File(filePath);
            // 创建对应的文件夹
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    Log.e(TAG, "Create parent dir error!");
                }
            }
            // 如果有文件删除之前文件
            if (file.exists()) {
                if (!file.delete()) {
                    Log.e(TAG, "delete last video error!");
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(data);
                writer.flush();
            } catch (IOException e) {
                Log.e(TAG, "writeResultToFile IOException");
                e.printStackTrace();
            }
        });
    }

    /**
     * 将byte写一个文件提供
     *
     * @param context 上下文信息
     * @param data 数据
     */
    public static void writeByteDataToFile(final Context context, final byte[] data) {
        ThreadPoolUtil.getInstance().addWork(() -> {
            // 二进制文件
            String fileType = FILE_BYTE_DATA;
            String fileName = DEFAULT_NAME + fileType + System.currentTimeMillis() + FILE_END;
            String cachePath = DIR_NAME + File.separator + VIDEO_FILE_DIR + File.separator + fileName;
            String filePath = context.getExternalFilesDir(null) + File.separator + cachePath;
            Log.e(TAG, "byte file path: " + filePath);
            File file = new File(filePath);
            // 创建对应的文件夹
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    Log.e(TAG, "Create parent dir error!");
                }
            }
            // 如果有文件删除之前文件
            if (file.exists()) {
                if (!file.delete()) {
                    Log.e(TAG, "delete last video error!");
                }
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
                fileOutputStream.write(data, 0, data.length);
                fileOutputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "writeByteDataToFile IOException");
                e.printStackTrace();
            }
        });
    }
}

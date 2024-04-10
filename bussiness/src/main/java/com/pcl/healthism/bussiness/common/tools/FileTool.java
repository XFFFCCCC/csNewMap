package com.pcl.healthism.bussiness.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileTool {

    private final static Logger LOG = LoggerFactory.getLogger(FileTool.class);


    public static List<String> listFileNames(String dir) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            return Collections.emptyList();
        }
        File[] files =  file.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(files).map(l -> l.getName()).collect(Collectors.toList());
    }

    /***
     * 文件拷贝
     */
    public static long  copyFile(String srcPath, String targetPath) {
        try {
            FileInputStream in = new FileInputStream(srcPath);
            FileOutputStream out = new FileOutputStream(targetPath);
            long size = FileCopyUtils.copy(in, out);
            in.close();
            out.close();
            return size;
        }catch (Exception e) {
            LOG.error("copy file error", e);
            return -1;
        }
    }

    /***
     * url拷贝到文件
     */
    public static boolean urlToFile(String srcUrl, String targetFile) {
        if (!isUrl(srcUrl)) {
            return copyFile(srcUrl, targetFile) > 0;
        }
        try {
            URL url = new URL(srcUrl);
            InputStream in = url.openStream();
            FileOutputStream out = new FileOutputStream(targetFile);
            FileCopyUtils.copy(in ,out);
            in.close();
            out.close();
        }catch (Exception e) {
            LOG.error("copy url to file error", e);
            return false;
        }
        return true;
    }

    private static boolean isUrl(String source) {
        return source.startsWith("file://")
                || source.startsWith("http://")
                || source.startsWith("ftp://")
                || source.startsWith("https://");
    }

    public static boolean createDir(String dir) {
        File file = new File(dir);
        if (!file.exists() && !file.isDirectory()) {
            boolean ok = file.mkdirs();
            if (!ok) {
                LOG.error("create directory failed,directory={}", dir);
            }
        }
        return true;
    }

    public static boolean delete(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
               return file.delete();
            }
        } catch (Exception e) {
            LOG.error("remove file failed,path={}", filePath);
        }
        return false;
    }

    public static long length(String file) {
        File file1 = new File(file);
        return file1.length();
    }

    public static void addRead(String file) {
        File file1 = new File(file);
        file1.setReadable(true);
    }

}

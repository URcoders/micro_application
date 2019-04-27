package com.linxu.microapp.core;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import com.linxu.microapp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author linxu
 * @date 2019/3/2
 */
@Slf4j
public class OSUtil {
    private final static String SLASH = "/";
    private final static String SUFFIX = ".py";

    /**
     * 判断是否存在存储空间
     *
     * @param bucketName 存储空间命名
     * @param client     客户端
     * @return boolean
     */
    public static boolean doesBucketExist(@NotNull String bucketName, @NotNull OSSClient client) {
        return client.doesBucketExist(bucketName);
    }

    /**
     * 在OS仓库创建新的文件夹
     *
     * @param folderName 文件夹名称
     * @param client     客户端
     * @param bucketName 存储空间
     */
    public static void createFolder(@NotNull String folderName, @NotNull OSSClient client, @NotNull String bucketName) {
        client.putObject(bucketName, folderName + SLASH, new ByteArrayInputStream(new byte[0]));
        log.info("created folder ->{}", folderName);
    }

    /**
     * 上传文件到云端
     * 上传完毕自动关闭客户端链接
     *
     * @param file       文件
     * @param bucketName 仓库名
     * @param client     客户端
     * @param filePrefix 文件名前缀
     * @return 是否成功上传
     */
    public static boolean upload(@NotNull MultipartFile file, @NotNull String bucketName, @NotNull OSSClient client, @NotNull String filePrefix) {
        try {
            client.putObject(bucketName, filePrefix + SUFFIX, converter(file));
        } catch (OSSException e) {
            log.error("upload happening error ->{}", "OSS ERROR");
            return false;
        } catch (ClientException e) {
            log.error("upload happening error ->{}", "client error");
            return false;
        } finally {
            client.shutdown();
        }
        return true;
    }
    public static boolean upload(@NotNull File file, @NotNull String bucketName, @NotNull OSSClient client, @NotNull String filePrefix) {
        try {
            client.putObject(bucketName, filePrefix + SUFFIX, file);
        } catch (OSSException e) {
            log.error("upload happening error ->{}", "OSS ERROR");
            return false;
        } catch (ClientException e) {
            log.error("upload happening error ->{}", "client error");
            return false;
        } finally {
            client.shutdown();
        }
        return true;
    }

    /**
     * 用于批量上传，因此：
     * 上传完毕之后，需要手动关闭客户端
     *
     * @param file       文件
     * @param bucketName 仓库名
     * @param client     客户端
     * @param filePrefix 文件名前缀
     * @return 是否成功上传
     */
    public static boolean uploadMulti(@NotNull String bucketName, @NotNull MultipartFile file, @NotNull OSSClient client, @NotNull String filePrefix) {
        try {
            client.putObject(bucketName, filePrefix + SUFFIX, converter(file));
        } catch (OSSException e) {
            log.error("uploadMul happening error ->{}", "OSS ERROR");
            return false;
        } catch (ClientException e) {
            log.error("uploadMul happening error ->{}", "client error");
            return false;
        }
        return true;
    }

    /**
     * 文件类型转换器
     *
     * @param file multipart file
     * @return file
     */
    private static File converter(@NotNull MultipartFile file) {
        File f1 = null;
        try {
            f1 = File.createTempFile(FileUtil.getUuid(), SUFFIX);
            FileUtils.copyInputStreamToFile(file.getInputStream(), f1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close(file.getInputStream());
            } catch (IOException e) {
                log.warn("流不存在，无须关闭！");
            }
        }
        return f1;
    }

    private static void close(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("流关闭失败！");
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName 仓库名称
     * @param client     客户端
     * @param objectName 对象全称
     * @return boolean
     */
    public static boolean delete(@NotNull String bucketName, @NotNull OSSClient client, @NotNull String objectName) {
        try {
            client.deleteObject(bucketName, objectName);
        } catch (OSSException e) {
            log.error("delete  happening error ->{}", "oss error");
            return false;
        } catch (ClientException e) {
            log.error("delete  happening error ->{}", "client error");
            return false;
        } finally {
            client.shutdown();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param bucketName 仓库名称
     * @param client     客户端
     * @param objectName 对象全称
     * @return boolean
     */
    public static boolean deleteMulti(@NotNull String bucketName, @NotNull OSSClient client, @NotNull String objectName) {
        try {
            client.deleteObject(bucketName, objectName);
        } catch (OSSException e) {
            log.error("deleteMulti happening error ->{}", "oss error");
            return false;
        } catch (ClientException e) {
            log.error("deleteMulti happening error ->{}", "client error");
            return false;
        }
        return true;
    }
}

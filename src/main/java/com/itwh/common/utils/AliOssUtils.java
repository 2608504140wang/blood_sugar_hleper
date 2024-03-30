package com.itwh.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AliOssUtils {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String uploadOneFile(MultipartFile file) {
        if (!(file.getOriginalFilename().endsWith(".png")) && !(file.getOriginalFilename().endsWith(".jpg")) && !(file.getOriginalFilename().endsWith(".PNG"))) {
            try {
                throw new Exception("文件类型错误，只能为png或者jpg");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //设置文件名
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取原始文件名的后缀，用于创造新名字
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //构造新文件名称
        String fileName = UUID.randomUUID().toString() + extension;

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));

            String url = "http://" + bucketName + "." + endpoint + "/" + fileName;
            //System.out.println(url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public List<String> uploadArrayFile(MultipartFile[] files) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();

        try {
            //设置文件名
            for (MultipartFile file : files) {
                String fileName = new Date()
                        + UUID.randomUUID().toString().replace("-", "")
                        + file.getOriginalFilename();
                // 创建PutObject请求。
                ossClient.putObject(bucketName, fileName, file.getInputStream());

                String url = "http://" + bucketName + "." + endpoint + "/" + fileName;
                // System.out.println(url);
                list.add(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return list;

    }

    public boolean deleteFile(String fileUrl) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        /** oss删除文件是根据文件完成路径删除的，但文件完整路径中不能包含Bucket名称。
         * 比如文件路径为：http://edu-czf.oss-cn-guangzhou.aliyuncs.com/2022/08/abc.jpg",
         * 则完整路径就是：2022/08/abc.jpg
         */
        int begin = ("http://" + bucketName + "." + endpoint + "/").length(); //找到文件路径的开始下标
        String deleteUrl = fileUrl.substring(begin);

        try {
            // 删除文件请求
            ossClient.deleteObject(bucketName, deleteUrl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}

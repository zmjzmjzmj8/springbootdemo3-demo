/**
 * 示例说明
 * 
 * HelloOSS是OSS Java SDK的示例程序，您可以修改endpoint、ACCESS_KEY_ID、ACCESS_KEY_SECRET、bucketName后直接运行。
 * 运行方法请参考README。
 * 
 * 本示例中的并不包括OSS Java SDK的所有功能，详细功能及使用方法，请参看“SDK手册 > Java-SDK”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/preface.html?spm=5176.docoss/sdk/java-sdk/。
 * 
 * 调用OSS Java SDK的方法时，抛出异常表示有错误发生；没有抛出异常表示成功执行。
 * 当错误发生时，OSS Java SDK的方法会抛出异常，异常中包括错误码、错误信息，详细请参看“SDK手册 > Java-SDK > 异常处理”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/exception.html?spm=5176.docoss/api-reference/error-response。
 * 
 * OSS控制台可以直观的看到您调用OSS Java SDK的结果，OSS控制台地址是：https://oss.console.aliyun.com/index#/。
 * OSS控制台使用方法请参看文档中心的“控制台用户指南”， 指南的来链接地址是：https://help.aliyun.com/document_detail/oss/getting-started/get-started.html?spm=5176.docoss/user_guide。
 * 
 * OSS的文档中心地址是：https://help.aliyun.com/document_detail/oss/user_guide/overview.html。
 * OSS Java SDK的文档地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/install.html?spm=5176.docoss/sdk/java-sdk。
 * 
 */

package org.zmj.springbootdemo.demo.commmon.aliOSS;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.List;

public class HelloOSS {
    static Logger logger = Logger.getLogger(HelloOSS.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com/";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    public static final String ACCESS_KEY_ID = "LTAI4w0LeQBGvIw0";
    public static final String ACCESS_KEY_SECRET = "sONkAHy1yUY4kZoFub46FQ069FugKE";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    public static final String BUCKET_NAME = "zmj-user-image";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
    public static String firstKey = "my-first-key";

    public static void main(String[] args) {

        // 日志配置，OSS Java SDK使用log4j记录错误信息。示例程序会在工程目录下生成“oss-demo.log”日志文件，默认日志级别是INFO。
        // 日志的配置文件是“conf/log4j-AliOSS.properties”，如果您不需要日志，可以没有日志配置文件和下面的日志配置。
        PropertyConfigurator.configure("conf/log4j-AliOSS.properties");

        logger.info("Started");

        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {

            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(BUCKET_NAME)) {
                System.out.println("您已经创建Bucket：" + BUCKET_NAME + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + BUCKET_NAME + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(BUCKET_NAME);
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(BUCKET_NAME);
            System.out.println("Bucket " + BUCKET_NAME + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            // 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
            ossClient.putObject(BUCKET_NAME, firstKey, is);
            System.out.println("Object：" + firstKey + "存入OSS成功。");

            // 下载文件。详细请参看“SDK手册 > Java-SDK > 下载文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
            OSSObject ossObject = ossClient.getObject(BUCKET_NAME, firstKey);
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                objectContent.append(line);
            }
            inputStream.close();
            System.out.println("Object：" + firstKey + "的内容是：" + objectContent);

            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "README.md";
            ossClient.putObject(BUCKET_NAME, fileKey, new File("README.md"));
            System.out.println("Object：" + fileKey + "存入OSS成功。");

            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ObjectListing objectListing = ossClient.listObjects(BUCKET_NAME);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }

            // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ossClient.deleteObject(BUCKET_NAME, firstKey);
            System.out.println("删除Object：" + firstKey + "成功。");
            ossClient.deleteObject(BUCKET_NAME, fileKey);
            System.out.println("删除Object：" + fileKey + "成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        logger.info("Completed");
    }

}

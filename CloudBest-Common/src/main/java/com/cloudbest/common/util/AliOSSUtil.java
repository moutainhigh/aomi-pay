package com.cloudbest.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AliOSSUtil {

//	@Value("${alioss.endpoint}")
//	private String endpoint = "http://oss-cn-shanghai-internal.aliyuncs.com";
//	@Value("${alioss.out-endpoint}")
//	private String outEndpoint = "http://oss-cn-shanghai.aliyuncs.com";
//	@Value("${alioss.access_key_id}")
//	private String access_key_id = "LTAIZG5FsYakKii5";
//	@Value("${alioss.access_key_secret}")
//	private String access_key_secret = "YfLz3cYOUN8GjhBDw7vfNLc3hbdZKE";
//	@Value("${alioss.bucket_name}")
//	private String bucket_name = "ka-bo-shi";
	
	
	@Value("${alioss.endpoint}")
//	private String endpoint = "https://oss-cn-shanghai-internal.aliyuncs.com";
	private String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	@Value("${alioss.out-endpoint}")
	private String outEndpoint = "http://oss-cn-shanghai.aliyuncs.com";
	@Value("${alioss.access_key_id}")
	private String access_key_id = "LTAI4FzVTyi2XjJTKH6cHcWa";
	@Value("${alioss.access_key_secret}")
	private String access_key_secret = "AgoNiSSAdZslCHMku53FYNpVxiCaBf";
	@Value("${alioss.bucket_name}")
	private String bucket_name = "cloudbest";
	

	
//	@Value("${alioss.endpoint}")
//	private String endpoint;
//	@Value("${alioss.out-endpoint}")
//	private String outEndpoint;
//	@Value("${alioss.access_key_id}")
//	private String access_key_id;
//	@Value("${alioss.access_key_secret}")
//	private String access_key_secret;
//	@Value("${alioss.bucket_name}")
//	private String bucket_name;


	public static final String REAL_NAME = "Realname";
	public static final String SHOPS = "shops";
	public static final String APP_ANDROID = "app-android";
	public static final String APP_SYS_IMAGETEXT = "appsys-image";
	public static final String BACKGROUND = "background";
	public static final String EXCHANGECOIN = "exchangecoin";
	
	private OSSClient getOSSClient(){
		return new OSSClient(endpoint, access_key_id, access_key_secret);
	}
	
	private OSSClient getOSSClient(String endPoint){
		return new OSSClient(endPoint, access_key_id, access_key_secret);
	}
	
	
//	上传本地文件
	public void uploadStreamToOss(String objectName,String filePath) throws FileNotFoundException{
		OSSClient ossClient = this.getOSSClient();
		// 上传文件流。
		InputStream inputStream = new FileInputStream(filePath);
		ossClient.putObject(bucket_name,objectName, inputStream);
		// 关闭Client。
		ossClient.shutdown();
	}
	
//	上传文件流
	public void uploadStreamToOss(String objectName,InputStream inputStream){
		OSSClient ossClient = this.getOSSClient();
		// 上传文件流。
		ossClient.putObject(bucket_name,objectName, inputStream);
		// 关闭Client。
		ossClient.shutdown();
	}
	
//	上传文件
	public void uploadFileToOss(String objectName,File file){
		OSSClient ossClient = this.getOSSClient();
		// 上传文件。
		ossClient.putObject(bucket_name,objectName, file);
		// 关闭Client。
		ossClient.shutdown();
	}
	
	public InputStream downloadStream(String objectName){
		// 创建OSSClient实例。
		OSSClient ossClient = this.getOSSClient();
		//ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = ossClient.getObject(bucket_name, objectName);
		// 读取文件内容。
		System.out.println("Object content:");
		InputStream inputStream = null;
		inputStream = ossObject.getObjectContent();
		/*BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
		while (true) {
		    String line = reader.readLine();
		    if (line == null) break;
		    System.out.println("\n" + line);
		}
		//数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
		reader.close();*/
		// 关闭Client。
		ossClient.shutdown();
		return inputStream;
	}
	
//	删除文件
	public void deleteFileFromOss(String objectName){
		OSSClient ossClient = this.getOSSClient();
		// 删除Object。
		ossClient.deleteObject(bucket_name, objectName);
		// 关闭Client。
		ossClient.shutdown();
	}
	
//	获取文件的访问url
	public String getFileUrl(String objectName){
		// 创建OSSClient实例。
		OSSClient ossClient = this.getOSSClient(outEndpoint);
		// 设置URL过期时间为1小时
		// 关闭Client。
		ossClient.shutdown();
	    Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 5);
		return ossClient.generatePresignedUrl(bucket_name, objectName, expiration).toString();
	}
	
//	确定是否有该文件
	public boolean doesHaveFile(String objectName){
		// 创建OSSClient实例。
		OSSClient ossClient = this.getOSSClient();
		// Object是否存在。
		boolean found = ossClient.doesObjectExist(bucket_name, objectName);
		System.out.println(found);
		// 关闭Client。
		ossClient.shutdown();
		return found;
	}
	
//	获取指定前缀的文件列表
	public List<String> listFiles(String keyPrefix){
		// 创建OSSClient实例。
		OSSClient ossClient = this.getOSSClient();
		//指定前缀
		ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(bucket_name).withPrefix(keyPrefix));
		List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		List<String> filenames = null;
		if (sums.size() > 0) {
			filenames = new ArrayList<String>();
		}
		for (OSSObjectSummary s : sums) {
			filenames.add(s.getKey());
//		    System.out.println("\t" + s.getKey());
		}
		return filenames;
	}
	
	/*public static void main(String[] args) throws Exception {
		String sourceFilePath = "D:\\back.jpg";
		File file = new File(sourceFilePath);
		OutputStream os = new ByteArrayOutputStream();
		InputStream inputStream = new FileInputStream(file);
		try {
			PhotoCompressUtil.compressPhoto(inputStream, os, 0.2f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStream inputStream2 = FileUtils.parse(os);
		new AliOSSUtil().uploadStreamToOss("11111111111111111.jpg",inputStream2);
    	File  dest = new File("D:\\back.jpg");
		PhotoCompressUtil.compressPhoto("D:\\back.jpg", "D:\\back.jpg", 0.5f);
		try {
			new AliOSSUtil().uploadStreamToOss("xinli-juhe", "2-realname-22265-123546.jpg", "D:\\back.jpg");
			new AliOSSUtil().uploadStreamToOss("xinli-juhe", "2-realname-22265-45678.jpg", "D:\\back.jpg");
			new AliOSSUtil().uploadStreamToOss("xinli-juhe", "2-realname-22265-85123.jpg", "D:\\back.jpg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> listFiles = new AliOSSUtil().listFiles("2-realname-22265");
		System.out.println(listFiles);
	}*/
	
	
}

package com.revature.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.tika.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class AmazonClient {
	
	private AmazonS3 s3client;
		
	private final String bucketName = "neolitic";
	
	private final String accessKey = "AKIASOJED6US6XADRUSO";
	
	private final String secretKey = "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";
	
	@PostConstruct
	public void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		
		this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2).build();
	}
	
	public File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		
		return convertedFile;
	}
	
	public PutObjectResult uploadFileToS3Bucket(String filename, File file) {
		return this.s3client.putObject(new PutObjectRequest(bucketName, filename, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}
	
	public byte[] getFileFromS3Bucket(String filename) throws IOException {
		S3Object object = this.s3client.getObject(new GetObjectRequest(bucketName, filename));
		return IOUtils.toByteArray(object.getObjectContent());
	}
}

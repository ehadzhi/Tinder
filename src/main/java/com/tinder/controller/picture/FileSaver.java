package com.tinder.controller.picture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.info.UserViewParam;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.pojo.User;

@Component
public class FileSaver {
	
	@Autowired
	private AtomicInteger numPictures;

	@Autowired
	private IPictureDAO pictureDAO;
	
	public String saveFile(MultipartFile file, User user) throws IOException {
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf('.'));
		fileName = numPictures.incrementAndGet() + fileName;
		file.transferTo(new File("C:/resources/images/" + fileName));
		pictureDAO.addPicture(fileName, user.getId());
		return "redirect:/Profile";
	}

	 public String saveFileAmazon(MultipartFile file, User user) throws IOException, S3ServiceException {
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf('.'));
		fileName = numPictures.incrementAndGet() + fileName;
		saveImageInAmazon(file, fileName);
		pictureDAO.addPicture(fileName, user.getId());
		return "redirect:/Profile";
	}

	private void saveImageInAmazon(MultipartFile image, String filename) throws IOException, S3ServiceException {
			AWSCredentials awsCredentials = new AWSCredentials(UserViewParam.S3ACCESSKEY, UserViewParam.S2SECRETKEY);
			S3Service s3 = new RestS3Service( awsCredentials);
			S3Bucket bucket = s3.getBucket("tinderbucket");
			S3Object imageObject = new S3Object(filename);
			imageObject.setDataInputStream(image.getInputStream());
			imageObject.setContentLength(image.getSize());
			imageObject.setContentType(image.getContentType());
			AccessControlList acl = new AccessControlList();
			acl.setOwner(bucket.getOwner());
			acl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			imageObject.setAcl(acl);
			s3.putObject(bucket, imageObject);
	}

	

}

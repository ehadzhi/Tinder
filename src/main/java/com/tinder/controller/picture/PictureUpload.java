package com.tinder.controller.picture;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tinder.info.UserParam;
import com.tinder.info.PictureParam;
import com.tinder.model.dao.picture.IPictureDAO;
import com.tinder.model.pojo.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping(value = "/PictureUpload")
public class PictureUpload {

	private static String bucketName = "tinderbucket";
	private static String keyName = "AKIAJFLBQNS6WEO3LSUA";

	@Autowired
	private AtomicInteger numPictures;

	@Autowired
	private IPictureDAO pictureDAO;

	@RequestMapping(method = RequestMethod.POST)
	public String uploadPicture(@RequestParam(PictureParam.TO_UPOAD) MultipartFile file, HttpServletRequest req)
			throws IllegalStateException, IOException, S3ServiceException {
		User user = (User) req.getSession().getAttribute(UserParam.USER);
		if (!file.isEmpty()) {
			return saveFile(file, user);
		}
		return "redirect:/Profile?error=empty-file";
	}

	 public String saveFile(MultipartFile file, User user) throws IOException, S3ServiceException {
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf('.'));
		fileName = numPictures.incrementAndGet() + fileName;
		saveImageInAmazon(file, fileName);
		pictureDAO.addPicture(fileName, user.getId());
		return "redirect:/Profile";
	}

	private void saveImageInAmazon(MultipartFile image, String filename) throws IOException, S3ServiceException {
			AWSCredentials awsCredentials = new AWSCredentials(UserParam.S3ACCESSKEY, UserParam.S2SECRETKEY);
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

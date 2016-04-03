package com.tinder.model.dao.picture;

import com.tinder.model.pojo.User;

public interface IPictureDAO {

	void addPicture(String pictureName, int ownerId);

	void setProfilePicture(String pictureName, User toSet);

	void deletePicture(String pictureName, User owner);

	int getLastPhotoId();

}
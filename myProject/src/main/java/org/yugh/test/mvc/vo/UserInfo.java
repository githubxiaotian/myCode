package org.yugh.test.mvc.vo;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 8458587871740665286L;

	private int id;
	private String userName;
	private String passWord;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userName=" + userName + ", passWord=" + passWord + "]";
	}
	

}

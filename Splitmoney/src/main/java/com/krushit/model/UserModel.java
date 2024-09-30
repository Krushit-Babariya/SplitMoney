package com.krushit.model;

import lombok.Data;

@Data
public class UserModel {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Long phone;
	private byte[] picture;
	private boolean customPicture;
	private String defaultCurrency;
	private String locale;
	private boolean active = true;
}

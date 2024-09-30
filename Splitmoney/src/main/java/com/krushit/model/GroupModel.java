package com.krushit.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class GroupModel {
    private Long id;
    private String name;
    private String groupType;
    private boolean simplifyByDefault; 
    private List<UserModel> members;
    private List<DebtModel> originalDebts;
    private List<DebtModel> simplifiedDebts;
    private byte[] avatar;
    private byte[] coverPhoto;
    private String inviteLink;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;
	private String createdBy;
	private String updatedBy;
}

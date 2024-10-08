package com.krushit.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.Data;

@Data
public class GroupModel {
    private Long id;
    private String name;
    private String groupType;
    private boolean simplifyByDefault; 
    private List<Integer> members;
    private List<Integer> originalDebts;
    private List<Integer> simplifiedDebts;

    private byte[] avatar = getDefaultAvatar();
    private byte[] coverPhoto = getDefaultCoverPhoto();
    
    private String inviteLink = "https://default.invite.link"; 

    private static byte[] getDefaultAvatar() {
        return loadImageAsByteArray("images/avatar.png");
    }

    private static byte[] getDefaultCoverPhoto() {
        return loadImageAsByteArray("images/banner.jpg");
    }

    private static byte[] loadImageAsByteArray(String path) {
        try (InputStream inputStream = GroupModel.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                return inputStream.readAllBytes();
            } else {
                throw new IOException("Image not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0]; 
        }
    }
}

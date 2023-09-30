package com.donatoordep.debver.builders.dto.request;

import com.donatoordep.debver.dto.request.ProfileUserRequestDTO;
import com.donatoordep.debver.dto.request.UserRequestDTO;
import com.donatoordep.debver.entities.ProfileUser;

public class UserRequestDTOBuilder {

    private UserRequestDTO user;

    private UserRequestDTOBuilder() {
        this.user = new UserRequestDTO();
    }

    public static UserRequestDTOBuilder builder() {
        return new UserRequestDTOBuilder();
    }

    public UserRequestDTO build() {
        return this.user;
    }

    public UserRequestDTOBuilder profile(String imgUrl, String bio) {
        this.user.getProfile().setBio(bio);
        this.user.getProfile().setImgUrl(imgUrl);
        return this;
    }

    public UserRequestDTOBuilder profile(ProfileUserRequestDTO profileUser) {
        this.user.setProfile(profileUser);
        return this;
    }

    public UserRequestDTOBuilder profile(ProfileUser profileUser) {
        this.user.setProfile(new ProfileUserRequestDTO());
        this.user.getProfile().setBio(profileUser.getBio());
        this.user.getProfile().setImgUrl(profileUser.getImgUrl());
        return this;
    }

    public UserRequestDTOBuilder name(String name) {
        this.user.setName(name);
        return this;
    }

    public UserRequestDTOBuilder email(String email) {
        this.user.setEmail(email);
        return this;
    }

    public UserRequestDTOBuilder role(String role) {
        this.user.setRole(role);
        return this;
    }

    public UserRequestDTOBuilder password(String password) {
        this.user.setPassword(password);
        return this;
    }
}

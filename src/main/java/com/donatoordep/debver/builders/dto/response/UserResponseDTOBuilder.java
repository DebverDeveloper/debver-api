package com.donatoordep.debver.builders.dto.response;

import com.donatoordep.debver.dto.RoleDTO;
import com.donatoordep.debver.dto.response.AccountStatsResponseDTO;
import com.donatoordep.debver.dto.response.CartResponseDTO;
import com.donatoordep.debver.dto.response.ProfileUserResponseDTO;
import com.donatoordep.debver.dto.response.UserResponseDTO;
import com.donatoordep.debver.entities.AccountStats;
import com.donatoordep.debver.entities.Cart;
import com.donatoordep.debver.entities.ProfileUser;

import java.util.List;

public class UserResponseDTOBuilder {

    private UserResponseDTO user;

    private UserResponseDTOBuilder() {
        this.user = new UserResponseDTO();
    }

    public static UserResponseDTOBuilder builder() {
        return new UserResponseDTOBuilder();
    }

    public UserResponseDTO build() {
        return this.user;
    }

    public UserResponseDTOBuilder id(Long id) {
        this.user.setId(id);
        return this;
    }

    public UserResponseDTOBuilder profile(String imgUrl, String bio) {
        this.user.getProfile().setBio(bio);
        this.user.getProfile().setImgUrl(imgUrl);
        return this;
    }

    public UserResponseDTOBuilder profile(ProfileUserResponseDTO profileUser) {
        this.user.setProfile(profileUser);
        return this;
    }

    public UserResponseDTOBuilder profile(ProfileUser profileUser) {
        this.user.setProfile(new ProfileUserResponseDTO(profileUser));
        return this;
    }

    public UserResponseDTOBuilder accountStats(AccountStats accountStats){
        this.user.getProfile().setAccountStats(new AccountStatsResponseDTO(accountStats));
        return this;
    }

    public UserResponseDTOBuilder cart(Cart entity) {
        this.user.setCart(new CartResponseDTO(entity));
        return this;
    }

    public UserResponseDTOBuilder cart(CartResponseDTO cart) {
        this.user.setCart(cart);
        return this;
    }

    public UserResponseDTOBuilder cart() {
        this.user.setCart(new CartResponseDTO(new Cart()));
        return this;
    }

    public UserResponseDTOBuilder name(String name) {
        this.user.setName(name);
        return this;
    }

    public UserResponseDTOBuilder email(String email) {
        this.user.setEmail(email);
        return this;
    }

    public UserResponseDTOBuilder roles(List<RoleDTO> roles) {
        this.user.setRoles(roles);
        return this;
    }
}

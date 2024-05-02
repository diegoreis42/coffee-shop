package com.time3.api.domains.User.dtos;

import com.time3.api.domains.User.UserRolesEnum;

public record RegisterDto(String email, String name, String password, UserRolesEnum role) {

}
package com.donatoordep.debver.services.business.rules.user.register;

import com.donatoordep.debver.dto.request.UserRequestDTO;
import com.donatoordep.debver.repositories.UserRepository;

public record RegisterUserArgs(UserRequestDTO userDTO, UserRepository userRepository) {
}

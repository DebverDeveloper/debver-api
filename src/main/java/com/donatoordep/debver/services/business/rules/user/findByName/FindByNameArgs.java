package com.donatoordep.debver.services.business.rules.user.findByName;

import com.donatoordep.debver.repositories.UserRepository;
import org.springframework.data.domain.Pageable;

public record FindByNameArgs(UserRepository repository, String name, Pageable pageable) {
}

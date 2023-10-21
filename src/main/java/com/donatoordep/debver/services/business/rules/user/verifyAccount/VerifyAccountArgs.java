package com.donatoordep.debver.services.business.rules.user.verifyAccount;

import com.donatoordep.debver.entities.Code;
import com.donatoordep.debver.repositories.UserRepository;

public record VerifyAccountArgs(UserRepository repository, Code code) {
}
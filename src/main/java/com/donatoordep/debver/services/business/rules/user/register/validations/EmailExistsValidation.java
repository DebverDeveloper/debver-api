package com.donatoordep.debver.services.business.rules.user.register.validations;

import com.donatoordep.debver.services.business.rules.user.register.RegisterUserArgs;
import com.donatoordep.debver.services.business.rules.user.register.RegisterUserValidation;
import com.donatoordep.debver.services.exceptions.UserExistsInDatabaseException;
import org.springframework.stereotype.Component;

@Component
public class EmailExistsValidation implements RegisterUserValidation {

    @Override
    public void verification(RegisterUserArgs args) {

        if(args.userRepository().findEmailForUser(args.userDTO().getEmail()).isPresent()){
            throw new UserExistsInDatabaseException();
        }
    }
}

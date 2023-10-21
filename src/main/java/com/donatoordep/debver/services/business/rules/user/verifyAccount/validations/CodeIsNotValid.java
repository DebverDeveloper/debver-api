package com.donatoordep.debver.services.business.rules.user.verifyAccount.validations;

import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.services.business.rules.user.verifyAccount.VerifyAccountArgs;
import com.donatoordep.debver.services.business.rules.user.verifyAccount.VerifyAccountValidations;
import com.donatoordep.debver.services.exceptions.CodeNotValidException;
import org.springframework.stereotype.Component;

@Component
public class CodeIsNotValid implements VerifyAccountValidations {

    @Override
    public void verification(VerifyAccountArgs args) {
        User user = args.repository().findByCode(args.code().getCode());
        if (user != null) {
            user.setEnabled(true);
            args.repository().save(user);
        } else {
            throw new CodeNotValidException();
        }
    }
}
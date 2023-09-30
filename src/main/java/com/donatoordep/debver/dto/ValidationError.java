package com.donatoordep.debver.dto;

import com.donatoordep.debver.services.exceptions.CustomizedException;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomizedException {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(String error, Integer status, String path) {
        super(error, status, path);
    }

    public void add(FieldMessage fieldMessage) {
        errors.add(fieldMessage);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldMessage> errors) {
        this.errors = errors;
    }
}

package org.mgs.TestApps.TransactionsApi;

import java.util.Collection;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class CollectionValidator implements Validator {

    private final Validator validator;

    public CollectionValidator(LocalValidatorFactoryBean validatorFactory) {
        this.validator = validatorFactory;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Collection collection = (Collection) object;
        for (Object obj : collection) {
            ValidationUtils.invokeValidator(validator, obj, errors);
        }
    }
}

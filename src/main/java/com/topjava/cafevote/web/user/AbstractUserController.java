package com.topjava.cafevote.web.user;

import com.topjava.cafevote.HasId;
import com.topjava.cafevote.model.User;
import com.topjava.cafevote.repository.UserRepository;
import com.topjava.cafevote.util.UserUtil;
import com.topjava.cafevote.web.UniqueMailValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import static com.topjava.cafevote.util.ValidationUtil.assureIdConsistent;
import static com.topjava.cafevote.util.ValidationUtil.checkSingleModification;


@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @Autowired
    private LocalValidatorFactoryBean defaultValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        checkSingleModification(repository.delete(id), "User id=" + id + " not found");
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, defaultValidator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}

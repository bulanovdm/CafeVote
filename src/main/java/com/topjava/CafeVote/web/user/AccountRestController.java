package com.topjava.CafeVote.web.user;

import com.topjava.CafeVote.config.security.AuthUser;
import com.topjava.CafeVote.model.Role;
import com.topjava.CafeVote.model.User;
import com.topjava.CafeVote.to.UserTo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

import static com.topjava.CafeVote.util.UserUtil.createNewFromTo;
import static com.topjava.CafeVote.util.UserUtil.updateFromTo;
import static com.topjava.CafeVote.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(value = AccountRestController.REST_ACCOUNT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Api(tags = "Account Controller")
public class AccountRestController extends AbstractUserController {

    public static final String REST_ACCOUNT_URL = "/api/account";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get(@ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return super.get(authUser.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiIgnore @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        super.delete(authUser.id());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = super.prepareAndSave(createNewFromTo(userTo));
        created.setRoles(Set.of(Role.USER));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_ACCOUNT_URL)
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @ApiIgnore @AuthenticationPrincipal AuthUser authUser) throws BindException {
        log.info("update {} to {}", authUser, userTo);
        validateBeforeUpdate(userTo, authUser.id());
        User user = repository.getExisted(userTo.id());
        super.prepareAndSave(updateFromTo(user, userTo));
    }
}

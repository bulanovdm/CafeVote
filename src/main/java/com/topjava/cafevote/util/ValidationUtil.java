package com.topjava.cafevote.util;


import com.topjava.cafevote.HasId;
import com.topjava.cafevote.error.ErrorType;
import com.topjava.cafevote.error.IllegalRequestDataException;
import com.topjava.cafevote.error.NotFoundException;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static com.topjava.cafevote.util.DateTimeUtil.getCurrentDate;
import static com.topjava.cafevote.util.DateTimeUtil.getCurrentTime;

@UtilityClass
public class ValidationUtil {
    public static final LocalTime EXPIRED = LocalTime.of(11, 0);

    private static final Validator validator;

    static {
        //  From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //  From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean) {
        // https://alexkosarev.name/2018/07/30/bean-validation-api/
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static void checkExpiredDateWithTime(LocalDate date, int restaurantId) {
        boolean expired = getCurrentTime().isAfter(EXPIRED);
        checkExpiredDate(date, restaurantId);
        if (expired) {
            throw new IllegalRequestDataException("Expired time voting for restaurant with id = " + restaurantId);
        }
    }

    public static void checkExpiredDate(LocalDate date, int menuId) {
        LocalDate today = getCurrentDate();
        if (!date.equals(today)) {
            throw new IllegalRequestDataException("Expired date voting for menu with id=" + menuId);
        }
    }

    public static boolean isNotExpired(@NonNull LocalTime voteTime) {
        return voteTime.isBefore(EXPIRED);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFoundWithIdOptional(Optional<T> optional, int id) {
        return checkNotFoundWithIdOptional(optional, "Entity with id=" + id + " not found");
    }

    public static <T> T checkNotFoundWithIdOptional(Optional<T> optional, String msg) {
        return optional.orElseThrow(() -> new NotFoundException(msg));
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new NotFoundException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new NotFoundException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logStackTrace, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logStackTrace) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

    public static void checkSingleModification(int count, String msg) {
        if (count != 1) {
            throw new NotFoundException(msg);
        }
    }
}

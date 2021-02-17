package com.topjava.cafevote.to;

import com.topjava.cafevote.HasIdAndEmail;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends BaseTo implements Serializable, HasIdAndEmail {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    protected String firstName;

    @NotBlank
    @Size(min = 2, max = 100)
    protected String lastName;

    @Email
    @NotBlank
    @Size(max = 100)
    protected String email;

    @NotBlank
    @Size(min = 5, max = 255)
    protected String password;

    public UserTo(Integer id, @NotBlank @Size(min = 2, max = 100) String firstName, @NotBlank @Size(min = 2, max = 100) String lastName,
                  @Email @NotBlank @Size(max = 100) String email, @NotBlank @Size(min = 5, max = 255) String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

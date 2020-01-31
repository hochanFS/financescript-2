package com.financescript.springapp.dto;

import com.financescript.springapp.validation.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class PasswordDto {
    @NotNull(message = "is required")
    @Size(min = 6, message = "Minimum of 6 characters is required")
    private String password;

    private String matchingPassword;
}

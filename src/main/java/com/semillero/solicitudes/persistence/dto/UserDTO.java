package com.semillero.solicitudes.persistence.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class UserDTO {

    private Integer nmIdUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "usuario requerido")
    private String dsUser;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "password requerido")
    private String dsPassword;

    @Email
    @NotEmpty(message = "email requerido")
    private String dsEmail;

    @NotEmpty(message = "Estado requerido")
    private String dsStatus;

    private LocalDate feCreated;
}

package com.bycnit.socle.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Holds the password to set
 *
 * @author S.BENDRIOUE
 */
@Data
public class PasswordDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** The verification token */
    private String token;

    /** The password to set */
    private String password;
}

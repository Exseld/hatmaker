package com.hat.maker.security.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends APIException {
        public UserNotFoundException() {
            super(HttpStatus.NOT_FOUND,"Utilisateur non trouvé");

        }
}

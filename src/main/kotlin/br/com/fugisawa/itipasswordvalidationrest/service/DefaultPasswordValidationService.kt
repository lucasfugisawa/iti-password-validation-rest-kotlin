package br.com.fugisawa.itipasswordvalidationrest.service

import br.com.fugisawa.itipasswordvalidationrest.domain.PasswordValidator
import br.com.fugisawa.itipasswordvalidationrest.domain.PredicatesPasswordValidator
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("strongValidation", "default")
class DefaultPasswordValidationService : PasswordValidationService {
    private val passwordValidator: PasswordValidator

    init {
        passwordValidator = PredicatesPasswordValidator.builder()
            .withMinLength(9)
            .withSpecialChar(PredicatesPasswordValidator.DEFAULT_SPECIAL_CHARACTERS)
            .withDigit()
            .withUpperCase()
            .withLowerCase()
            .withNoRepeatedChars()
            .withNoWhiteSpaces()
            .build()
    }

    override fun validate(password: CharSequence) = passwordValidator.isValid(password)
}

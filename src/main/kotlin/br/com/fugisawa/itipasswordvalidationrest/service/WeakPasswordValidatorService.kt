package br.com.fugisawa.itipasswordvalidationrest.service

import br.com.fugisawa.itipasswordvalidationrest.domain.PasswordValidator
import br.com.fugisawa.itipasswordvalidationrest.domain.PredicatesPasswordValidator
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("weakValidation")
class WeakPasswordValidatorService : PasswordValidationService {

    private val passwordValidator by lazy {
        PredicatesPasswordValidator.builder()
            .withMinLength(4)
            .withNoWhiteSpaces()
            .build()
    }

    override fun validate(password: CharSequence) = passwordValidator.isValid(password)
}

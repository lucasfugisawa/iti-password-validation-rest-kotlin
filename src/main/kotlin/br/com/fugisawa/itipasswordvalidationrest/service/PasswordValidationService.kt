package br.com.fugisawa.itipasswordvalidationrest.service

interface PasswordValidationService {
    fun validate(password: CharSequence): Boolean
}
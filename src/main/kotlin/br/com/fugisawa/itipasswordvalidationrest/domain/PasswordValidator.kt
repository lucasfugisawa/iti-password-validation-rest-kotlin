package br.com.fugisawa.itipasswordvalidationrest.domain

interface PasswordValidator {
    fun isValid(password: CharSequence): Boolean
}
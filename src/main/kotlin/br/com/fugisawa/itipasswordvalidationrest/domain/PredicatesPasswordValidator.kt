package br.com.fugisawa.itipasswordvalidationrest.domain

class PredicatesPasswordValidator(
    val predicates: MutableSet<((CharSequence) -> Boolean)>,
) : PasswordValidator {

    companion object {
        val DEFAULT_SPECIAL_CHARACTERS: CharSequence = "!@#$%^&*()-+"
        val HAS_UPPERCASE: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[A-Z].*".toRegex()) }
        val HAS_LOWERCASE: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[a-z].*".toRegex()) }
        val HAS_DIGIT: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[0-9].*".toRegex()) }
        val NO_REPEATED_CHARS: (CharSequence) -> Boolean = { password: CharSequence -> !password.matches(".*(.).*\\1.*".toRegex()) }
        val NO_WHITE_SPACES: (CharSequence) -> Boolean = { password: CharSequence -> !password.matches(".*\\s.*".toRegex()) }

        fun builder() = PredicatesPasswordValidatorBuilder()

        class PredicatesPasswordValidatorBuilder {
            private val predicates = mutableSetOf<((CharSequence) -> Boolean)>()

            fun withPredicate(predicate: ((CharSequence) -> Boolean)): PredicatesPasswordValidatorBuilder {
                predicates.add(predicate)
                return this
            }

            fun withMinLength(minLength: Int): PredicatesPasswordValidatorBuilder {
                val p = { s: CharSequence -> s.length >= minLength }
                predicates.add(p)
                return this
            }

            fun withMaxLength(maxLength: Int): PredicatesPasswordValidatorBuilder {
                val p = { s: CharSequence -> s.length <= maxLength }
                predicates.add(p)
                return this
            }

            fun withSpecialChar(specialChars: CharSequence): PredicatesPasswordValidatorBuilder {
                val p = { s: CharSequence -> s.matches(".*[$DEFAULT_SPECIAL_CHARACTERS].*".toRegex()) }
                predicates.add(p)
                return this
            }

            fun withUpperCase(): PredicatesPasswordValidatorBuilder {
                predicates.add(HAS_UPPERCASE)
                return this
            }

            fun withLowerCase(): PredicatesPasswordValidatorBuilder {
                predicates.add(HAS_LOWERCASE)
                return this
            }

            fun withDigit(): PredicatesPasswordValidatorBuilder {
                predicates.add(HAS_DIGIT)
                return this
            }

            fun withNoRepeatedChars(): PredicatesPasswordValidatorBuilder {
                predicates.add(NO_REPEATED_CHARS)
                return this
            }

            fun withNoWhiteSpaces(): PredicatesPasswordValidatorBuilder {
                predicates.add(NO_WHITE_SPACES)
                return this
            }

            fun build() = PredicatesPasswordValidator(predicates)
        }
    }

    override fun isValid(password: CharSequence) = predicates.all { it(password) }

    override fun toString() = "PredicatesPasswordValidator(predicates=$predicates)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PredicatesPasswordValidator
        return predicates == other.predicates
    }

    override fun hashCode() = predicates.hashCode()

}
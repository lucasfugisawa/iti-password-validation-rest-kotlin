package br.com.fugisawa.itipasswordvalidationrest.domain

class PredicatesPasswordValidator(
    predicates: Set<(CharSequence) -> Boolean> = emptySet(),
    minimumLength: Int? = null,
    maximumLength: Int? = null,
    specialCharacters: CharSequence? = null,
    hasUpperCase: Boolean = false,
    hasLowerCase: Boolean = false,
    hasDigit: Boolean = false,
    noRepeatedChars: Boolean = false,
    noWhiteSpaces: Boolean = false,
) : PasswordValidator {

    val predicates: Set<(CharSequence) -> Boolean>

    init {
        val p = mutableSetOf<(CharSequence) -> Boolean>()
        if (minimumLength != null) p.add { s: CharSequence -> s.length >= minimumLength }
        if (maximumLength != null) p.add { s: CharSequence -> s.length <= maximumLength }
        if (specialCharacters != null) p.add { s: CharSequence -> s.matches(".*[$specialCharacters].*".toRegex()) }
        if (hasUpperCase) p.add(HAS_UPPERCASE)
        if (hasLowerCase) p.add(HAS_LOWERCASE)
        if (hasDigit) p.add(HAS_DIGIT)
        if (noRepeatedChars) p.add(NO_REPEATED_CHARS)
        if (noWhiteSpaces) p.add(NO_WHITE_SPACES)
        this.predicates = predicates + p
    }


    companion object {
        val DEFAULT_SPECIAL_CHARACTERS: CharSequence = "!@#$%^&*()-+"
        val HAS_UPPERCASE: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[A-Z].*".toRegex()) }
        val HAS_LOWERCASE: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[a-z].*".toRegex()) }
        val HAS_DIGIT: (CharSequence) -> Boolean = { password: CharSequence -> password.matches(".*[0-9].*".toRegex()) }
        val NO_REPEATED_CHARS: (CharSequence) -> Boolean = { password: CharSequence -> !password.matches(".*(.).*\\1.*".toRegex()) }
        val NO_WHITE_SPACES: (CharSequence) -> Boolean = { password: CharSequence -> !password.matches(".*\\s.*".toRegex()) }
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
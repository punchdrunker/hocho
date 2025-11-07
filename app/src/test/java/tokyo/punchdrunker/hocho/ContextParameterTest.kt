@file:MustUseReturnValues

package tokyo.punchdrunker.hocho

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import tokyo.punchdrunker.hocho.helper.ConsoleLogger
import tokyo.punchdrunker.hocho.helper.Country
import tokyo.punchdrunker.hocho.helper.GuardConditionUseCase
import tokyo.punchdrunker.hocho.helper.InMemoryUserDataSource
import tokyo.punchdrunker.hocho.helper.NestedTypeAliasTest
import tokyo.punchdrunker.hocho.helper.UpdateUserUseCase
import tokyo.punchdrunker.hocho.helper.UserRole

// tests to try new functions for Kotlin 2.2, 2.3
class ContextParameterTest {
    @Test
    fun testContextParameter() = runTest {
        val target = UpdateUserUseCase()
        target.hi()

        val logger = ConsoleLogger()
        val ds = InMemoryUserDataSource()
        context(logger, ds) {
            target.invoke("Paul")
        }
    }

    @Test
    fun testGuardCondition() {
        val target = GuardConditionUseCase()
        target(null)
    }

    @Test
    fun testGuardConditionForType() {
        val target = GuardConditionUseCase()
        target.guardConditionForType(Country.Japan("japanese"))
    }

    @Test
    fun testNestedTypeAlias() {
        val target = NestedTypeAliasTest()
        val tokens: NestedTypeAliasTest.Token = setOf("abc")
        assertFalse(
            target.hasToken(
                existing = tokens,
                token = "new",
            )
        )
    }

    @Test
    fun testExhaustiveness() {
        assertEquals(
            10,
            getPermissionLevel(UserRole.MEMBER)
        )
    }

    @Test
    fun testUnusedReturnValue() {
        formatGreeting("punchdrunker")
    }

    private fun formatGreeting(name: String): String {
        if (name.isBlank()) return "Hello, anonymous user!"
        if (!name.contains(' ')) {
            // The checker reports a warning that this result is ignored.
            // Need to add return to fix this warn
            "Hello, " + name.replaceFirstChar(Char::titlecase) + "!"
        }
        val (first, last) = name.split(' ')
        return "Hello, $first! Or should I call you Dr. $last?"
    }

    private fun getPermissionLevel(role: UserRole): Int {
        // Covers the Admin case outside of the when expression
        if (role == UserRole.ADMIN) return 99

        return when (role) {
            UserRole.MEMBER -> 10
            UserRole.GUEST -> 1
            // You no longer have to include this else branch
            // else -> throw IllegalStateException()
        }
    }
}

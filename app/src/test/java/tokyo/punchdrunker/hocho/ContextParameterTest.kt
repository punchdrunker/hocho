package tokyo.punchdrunker.hocho

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tokyo.punchdrunker.hocho.helper.ConsoleLogger
import tokyo.punchdrunker.hocho.helper.Country
import tokyo.punchdrunker.hocho.helper.GuardConditionUseCase
import tokyo.punchdrunker.hocho.helper.InMemoryUserDataSource
import tokyo.punchdrunker.hocho.helper.UpdateUserUseCase

// tests to try new functions for Kotlin 2.2
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
}

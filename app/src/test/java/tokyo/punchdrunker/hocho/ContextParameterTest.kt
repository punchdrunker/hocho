package tokyo.punchdrunker.hocho

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tokyo.punchdrunker.hocho.helper.ConsoleLogger
import tokyo.punchdrunker.hocho.helper.InMemoryUserDataSource
import tokyo.punchdrunker.hocho.helper.UpdateUserUseCase

class ContextParameterTest {
    @Test
    fun testUpdate() = runTest {
        val target = UpdateUserUseCase()
        target.hi()

        val logger = ConsoleLogger()
        val ds = InMemoryUserDataSource()
        context(logger, ds) {
            target.invoke("Paul")
        }
    }
}

package tokyo.punchdrunker.hocho.helper

// new features in Kotlin 2.x
class UpdateUserUseCase {
    context(logger: Logger, ds: UserDataSource)
    suspend operator fun invoke(name: String) {
        ds.saveUser(User("1", "John"))

        val repo = UserRepository()

        context(logger, ds) {
            logger.log(repo.loadUser("1").toString())
            repo.updateName("1", name)
            logger.log(repo.loadUser("1").toString())
        }
    }

    fun hi() {
        println("hi!")
    }
}

interface Logger {
    fun log(message: String)
}

class ConsoleLogger : Logger {
    override fun log(message: String) {
        val mark = "##########"
        println("$mark $message $mark")
    }
}

interface UserDataSource {
    suspend fun getUser(id: String): User?
    suspend fun saveUser(user: User)
}

class InMemoryUserDataSource : UserDataSource {
    private val map = mutableMapOf<String, User>()

    override suspend fun getUser(id: String): User? = map[id]

    override suspend fun saveUser(user: User) {
        map[user.id] = user
    }
}

data class User(val id: String, val name: String)

class UserRepository {
    context(logger: Logger, dataSource: UserDataSource)
    suspend fun loadUser(id: String): User? {
        logger.log("invoke loadUser($id)")
        return dataSource.getUser(id)
    }

    context(logger: Logger, dataSource: UserDataSource)
    suspend fun updateName(id: String, newName: String) {
        logger.log("invoke updateName($id, $newName)")
        val current = dataSource.getUser(id) ?: return
        dataSource.saveUser(current.copy(name = newName))
    }
}

sealed interface Country {
    data class Japan(val language: String) : Country
    data class Us(val language: String, val currency: String) : Country
    data class Korea(val language: String, val population: Int) : Country
}

class GuardConditionUseCase() {
    operator fun invoke(user: User?) {
        if (user == null) {
            println("user is null")
            return
        }
        // available as non-optional
        println(user.name)
    }

    fun guardConditionForType(country: Country) {
        if (country !is Country.Japan) {
            println("$country is not Japan")
            return
        }

        // smart cast
        println(country.language)
    }
}

class NestedTypeAliasTest {
    typealias Token = Set<String>

    fun hasToken(existing: Token, token: String): Boolean = existing.contains(token)
}

enum class UserRole { ADMIN, MEMBER, GUEST }

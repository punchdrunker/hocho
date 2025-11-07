package tokyo.punchdrunker.hocho.helper

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

package tokyo.punchdrunker.hocho

class KotlinSandbox {
    private val name = "Jake"
    inner class Inner {
        fun sayHi() : String {
            return "Hi " + name + "!"
        }
    }
}
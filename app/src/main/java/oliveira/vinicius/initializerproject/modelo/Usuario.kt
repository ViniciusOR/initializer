package oliveira.vinicius.initializerproject.modelo

class Usuario {

    var login: String? = null
    var senha: String? = null

    override fun toString(): String {
        return "Usuario{" +
                "login='" + login + '\''.toString() +
                ", senha='" + senha + '\''.toString() +
                '}'.toString()
    }
}
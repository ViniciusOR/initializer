package oliveira.vinicius.initializerproject.modelo

import kotlin.math.pow

class Calculadora {
    var capital : Double = 0.0
    var taxa : Double = 0.0
    var periodo : Double = 0.0
    var periodoProporcional : Double = 0.0
    var montante : Double = 0.0
    var taxaAdministrativa : Double = 0.0
    var capitalizacoes : Double = 0.0
    var juros : Double = 0.0
    var desconto : Double = 0.0
    var valorAtual : Double = 0.0
    var valorDescontadoComercial : Double = 0.0

    fun CalcularMontante() : Double {
        return capital * (1 + (taxa / 100) * periodo)
    }

    fun calcularJuroExato() : Double {
        return (taxa / 100) / 365
    }

    fun calcularJuroComercial() : Double {
        return (taxa / 100) / 360
    }

    fun calcularTaxasProporcionais() : Double {
        return taxa / (periodo / periodoProporcional)
    }

    fun calcularMontanteJurosCompostos() : Double {
        return capital * (1 + (taxa / 100)).pow(periodo)
    }

    fun calcularJurosCompostos() : Double {
        return calcularMontanteJurosCompostos() - capital
    }

    fun calcularDescontoRacional() : Double {
        return (montante * (taxa / 100) * periodo) / (1 + (taxa / 100) * periodo)
    }

    fun calcularDescontoComercial() : Double {
        return montante * (taxa / 100) * periodo
    }

    fun calcularValorReal() : Double {
        return montante - calcularDescontoRacional()
    }

    fun calcularValorDescontadoComercial() : Double {
        return montante - calcularDescontoComercial()
    }

    fun calcularDescontoBancario() : Double {
        return montante * ((taxa / 100) * periodo + taxaAdministrativa)
    }

    fun calcularTaxaEfetiva() : Double {
        return ((1 + (taxa / 100) / capitalizacoes).pow(capitalizacoes)) - 1
    }

}
package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tela_taxa_efetiva.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaTaxaEfetiva : AppCompatActivity() {

    lateinit var telaTaxaEfetivaTextViewResultado : TextView
    lateinit var telaTaxaEfetivaEditTextCapitalizacoes : EditText
    lateinit var telaTaxaEfetivaEditTextMontante : EditText
    lateinit var telaTaxaEfetivaEditTextTaxa : EditText
    lateinit var telaTaxaEfetivaEditTextPeriodo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_taxa_efetiva)

    telaTaxaEfetivaTextViewResultado = findViewById(R.id.telaTaxaEfetivaTextViewResultado)
    telaTaxaEfetivaEditTextCapitalizacoes = findViewById(R.id.telaTaxaEfetivaEditTextCapitalizacoes)
    telaTaxaEfetivaEditTextMontante = findViewById(R.id.telaTaxaEfetivaEditTextMontante)
    telaTaxaEfetivaEditTextPeriodo = findViewById(R.id.telaTaxaEfetivaEditTextPeriodo)
    telaTaxaEfetivaEditTextTaxa = findViewById(R.id.telaTaxaEfetivaEditTextPeriodo)


    telaTaxaEfetivaButtonCalcular.setOnClickListener {
        if (telaTaxaEfetivaTextViewResultado.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextCapitalizacoes.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextMontante.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextTaxa.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextPeriodo.text.toString().isNotEmpty() &&

            telaTaxaEfetivaTextViewResultado.text.toString().toDouble() > 0 &&
            telaTaxaEfetivaEditTextCapitalizacoes.text.toString().toDouble() > 0 &&
            telaTaxaEfetivaEditTextMontante.text.toString().toDouble() > 0 &&
            telaTaxaEfetivaEditTextTaxa.text.toString().toDouble() > 0 &&
            telaTaxaEfetivaEditTextPeriodo.text.toString().toDouble() > 0) {

            var calculadora =
                Calculadora()
            calculadora.capitalizacoes = telaTaxaEfetivaEditTextCapitalizacoes.text.toString().toDouble()
            calculadora.montante = telaTaxaEfetivaEditTextMontante.text.toString().toDouble()
            calculadora.taxa = telaTaxaEfetivaEditTextTaxa.text.toString().toDouble()
            calculadora.periodo = telaTaxaEfetivaEditTextPeriodo.text.toString().toDouble()
            telaTaxaEfetivaTextViewResultado.text = calculadora.calcularTaxaEfetiva().toString()

        } else {
            toast("Por favor, preencha corretamente para efetuar o cadastro.")
        }
    }

    telaTaxaEfetivaButtonLimpar.setOnClickListener {
        if (telaTaxaEfetivaTextViewResultado.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextCapitalizacoes.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextMontante.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextTaxa.text.toString().isNotEmpty() &&
            telaTaxaEfetivaEditTextPeriodo.text.toString().isNotEmpty()) {
                limpar()
        }
    }

    }

    fun limpar() {
        telaTaxaEfetivaEditTextTaxa.text = null
        telaTaxaEfetivaEditTextPeriodo.text = null
        telaTaxaEfetivaEditTextMontante.text = null
        telaTaxaEfetivaEditTextCapitalizacoes.text = null
        telaTaxaEfetivaTextViewResultado.text = null
    }

}

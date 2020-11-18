package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tela_taxa_proporcional.*
import oliveira.vinicius.initializerproject.modelo.Calculadora

class TelaTaxaProporcional : AppCompatActivity() {

    lateinit var telaTaxaProporcionalTextViewResultado : TextView
    lateinit var  telaTaxaProporcionalEditTextPeriodo : EditText
    lateinit var  telaTaxaProporcionalEditTextPeriodoConvertido : EditText
    lateinit var  telaTaxaProporcionalEditTextTaxa : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_taxa_proporcional)

    telaTaxaProporcionalTextViewResultado = findViewById(R.id.telaTaxaProporcionalTextViewResultado)
    telaTaxaProporcionalEditTextPeriodo = findViewById(R.id.telaTaxaProporcionalEditTextPeriodo)
    telaTaxaProporcionalEditTextPeriodoConvertido = findViewById(R.id.telaTaxaProporcionalEditTextPeriodoConvertido)
    telaTaxaProporcionalEditTextTaxa = findViewById(R.id.telaTaxaProporcionalEditTextTaxa)

    telaTaxaProporcionalButtonCalcular.setOnClickListener {
        var calculadora =
            Calculadora()
        calculadora.periodo = telaTaxaProporcionalEditTextPeriodo.text.toString().toDouble()
        calculadora.periodoProporcional = telaTaxaProporcionalEditTextPeriodoConvertido.text.toString().toDouble()
        calculadora.taxa = telaTaxaProporcionalEditTextTaxa.text.toString().toDouble()
        telaTaxaProporcionalTextViewResultado.text = calculadora.calcularTaxasProporcionais().toString()
    }

    telaTaxaProporcionalButtonLimpar.setOnClickListener {
    if (telaTaxaProporcionalTextViewResultado.text.toString().isNotEmpty() &&
        telaTaxaProporcionalEditTextPeriodo.text.toString().isNotEmpty() &&
        telaTaxaProporcionalEditTextPeriodoConvertido.text.toString().isNotEmpty() &&
        telaTaxaProporcionalEditTextTaxa.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaTaxaProporcionalTextViewResultado.text = null
        telaTaxaProporcionalEditTextPeriodo.text = null
        telaTaxaProporcionalEditTextPeriodoConvertido.text = null
        telaTaxaProporcionalEditTextTaxa.text = null
    }
}

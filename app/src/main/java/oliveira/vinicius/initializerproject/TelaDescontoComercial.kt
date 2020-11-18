package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_desconto_comercial.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaDescontoComercial : AppCompatActivity() {

    lateinit var telaDescontoComercialTextViewResultado : TextView
    lateinit var telaDescontoComercialTextViewValorComercial : TextView
    lateinit var telaDescontoComercialEditTextMontante : EditText
    lateinit var telaDescontoComercialEditTextTaxa : EditText
    lateinit var telaDescontoComercialEditTextPeriodo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_desconto_comercial)
        val database = FirebaseFirestore.getInstance()

    telaDescontoComercialTextViewResultado = findViewById(R.id.telaDescontoComercialTextViewResultado)
    telaDescontoComercialTextViewValorComercial = findViewById(R.id.telaDescontoComercialTextViewValorComercial)
    telaDescontoComercialEditTextMontante = findViewById(R.id.telaDescontoComercialEditTextMontante)
    telaDescontoComercialEditTextTaxa = findViewById(R.id.telaDescontoComercialEditTextTaxa)
    telaDescontoComercialEditTextPeriodo = findViewById(R.id.telaDescontoComercialEditTextPeriodo)

    telaDescontoComercialButtonCalcular.setOnClickListener {
        if (telaDescontoComercialEditTextMontante.text.toString().isNotEmpty() &&
            telaDescontoComercialEditTextMontante.text.toString().toDouble() > 0 &&
            telaDescontoComercialEditTextTaxa.text.toString().isNotEmpty() &&
            telaDescontoComercialEditTextTaxa.text.toString().toDouble() > 0 &&
            telaDescontoComercialEditTextPeriodo.text.toString().isNotEmpty() &&
            telaDescontoComercialEditTextPeriodo.text.toString().toDouble() > 0) {
            var calculadora =
                Calculadora()
                calculadora.montante = telaDescontoComercialEditTextMontante.text.toString().toDouble()
                calculadora.taxa = telaDescontoComercialEditTextTaxa.text.toString().toDouble()
                calculadora.periodo = telaDescontoComercialEditTextPeriodo.text.toString().toDouble()
                calculadora.desconto = calculadora.calcularDescontoComercial()
                calculadora.valorDescontadoComercial = calculadora.calcularValorDescontadoComercial()

            GenericDialog.Builder(this)
                .setDialogFont(R.font.nunito_bold)
                .setDialogTheme(R.style.GenericDialogTheme)
                .setIcon(android.R.drawable.btn_radio)
                .setTitle("Desconto comercial: R$ ${(calculadora.calcularDescontoComercial().toString())}" +
                    "\n Valor descontado: R$ ${(calculadora.calcularValorDescontadoComercial().toString())}")
                .setTitleAppearance(R.color.colorPrimaryDark, 16f)
                .setMessage(getString(R.string.como_deseja_proceder))

                .addNewButton(R.style.PositiveButton) {
                    database.collection("contas")
                        .add(calculadora)
                        .addOnSuccessListener { documentReference ->
                            toast(getString(R.string.conta_adicionada))
                        }
                        .addOnFailureListener { e ->
                            toast(getString(R.string.conta_nao_possivel_adicionar))
                        }
                }
                .addNewButton(R.style.NegativeButton) {
                    return@addNewButton
                }
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(true)
                .generate()

                } else {
            toast(getString(R.string.preencha_corretamente))
        }
        }

    telaDescontoComercialButtonLimpar.setOnClickListener {
        if (telaDescontoComercialEditTextMontante.text.toString().isNotEmpty() &&
            telaDescontoComercialEditTextTaxa.text.toString().isNotEmpty() &&
            telaDescontoComercialEditTextPeriodo.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaDescontoComercialTextViewResultado.text = null
        telaDescontoComercialTextViewValorComercial.text = null
        telaDescontoComercialEditTextMontante.text = null
        telaDescontoComercialEditTextTaxa.text = null
        telaDescontoComercialEditTextPeriodo.text = null
    }

}

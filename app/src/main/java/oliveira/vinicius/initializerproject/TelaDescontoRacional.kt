package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_desconto_racional.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaDescontoRacional : AppCompatActivity() {

    private lateinit var telaDescontoRacionalTextViewResultado : TextView
    private lateinit var telaDescontoRacionalTextViewValorReal : TextView
    private lateinit var telaDescontoRacionalEditTextMontante : EditText
    private lateinit var telaDescontoRacionalEditTextTaxa : EditText
    private lateinit var telaDescontoRacionalEditTextPeriodo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_desconto_racional)
        val database = FirebaseFirestore.getInstance()

    telaDescontoRacionalTextViewResultado = findViewById(R.id.telaDescontoRacionalTextViewResultado)
    telaDescontoRacionalTextViewValorReal = findViewById(R.id.telaDescontoRacionalTextViewValorReal)
    telaDescontoRacionalEditTextMontante = findViewById(R.id.telaDescontoRacionalEditTextMontante)
    telaDescontoRacionalEditTextTaxa = findViewById(R.id.telaDescontoRacionalEditTextTaxa)
    telaDescontoRacionalEditTextPeriodo = findViewById(R.id.telaDescontoRacionalEditTextPeriodo)


    telaDescontoRacionalButtonCalcular.setOnClickListener {

        if (telaDescontoRacionalEditTextMontante.text.toString().isNotEmpty() &&
            telaDescontoRacionalEditTextMontante.text.toString().toDouble() > 0 &&
            telaDescontoRacionalEditTextPeriodo.text.toString().isNotEmpty() &&
            telaDescontoRacionalEditTextPeriodo.text.toString().toDouble() > 0 &&
            telaDescontoRacionalEditTextTaxa.text.toString().toDouble() > 0 &&
            telaDescontoRacionalEditTextTaxa.text.toString().isNotEmpty()) {
                var calculadora =
                    Calculadora()
                calculadora.montante = telaDescontoRacionalEditTextMontante.text.toString().toDouble()
                calculadora.taxa = telaDescontoRacionalEditTextTaxa.text.toString().toDouble()
                calculadora.periodo = telaDescontoRacionalEditTextPeriodo.text.toString().toDouble()
                calculadora.desconto = calculadora.calcularDescontoRacional()
                calculadora.valorAtual = calculadora.calcularValorReal()

            GenericDialog.Builder(this)
                .setDialogFont(R.font.nunito_bold)
                .setDialogTheme(R.style.GenericDialogTheme)
                .setIcon(android.R.drawable.btn_radio)
                .setTitle("Desconto Racional: R$ ${(calculadora.calcularDescontoRacional().toString())} " +
                        "\n  Valor atual: R$ ${(calculadora.calcularValorReal().toString())}")
                .setTitleAppearance(R.color.colorPrimaryDark, 16f)
                .setMessage(getString(R.string.como_deseja_proceder))
                .addNewButton(R.style.PositiveButton) {
                  database.collection("contas")
                      .add(calculadora)
                      .addOnSuccessListener { documentReference ->
                          toast(getString(R.string.conta_adicionada))
                      }
                      .addOnFailureListener { e ->
                          toast((getString(R.string.conta_nao_possivel_adicionar)))
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

    telaDescontoRacionalButtonLimpar.setOnClickListener {
        if (telaDescontoRacionalEditTextMontante.text.toString().isNotEmpty() &&
            telaDescontoRacionalEditTextTaxa.text.toString().isNotEmpty() &&
            telaDescontoRacionalEditTextPeriodo.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaDescontoRacionalTextViewResultado.text = null
        telaDescontoRacionalTextViewValorReal.text = null
        telaDescontoRacionalEditTextMontante.text = null
        telaDescontoRacionalEditTextTaxa.text = null
        telaDescontoRacionalEditTextPeriodo.text = null
    }

}

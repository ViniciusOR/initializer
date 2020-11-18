package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_juros_compostos.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaJurosCompostos : AppCompatActivity() {

    lateinit var textViewResultado : TextView
    lateinit var textViewResultadoJuro : TextView
    lateinit var telaJurosCompostosEditTextCapital : EditText
    lateinit var telaJurosCompostosEditTextTaxa : EditText
    lateinit var TelaJurosCompostosEditTextPeriodo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_juros_compostos)
        val database = FirebaseFirestore.getInstance()

    textViewResultado = findViewById(R.id.telaJurosCompostosTextViewResultado)
    textViewResultadoJuro = findViewById(R.id.TelaJurosCompostosTextViewResultadoJuros)
    telaJurosCompostosEditTextCapital = findViewById(R.id.telaJurosCompostosEditTextCapital)
    telaJurosCompostosEditTextTaxa = findViewById(R.id.telaJurosCompostosEditTextTaxa)
    TelaJurosCompostosEditTextPeriodo = findViewById(R.id.TelaJurosCompostosEditTextPeriodo)

    telaJurosCompostosButtonCalcular.setOnClickListener {

        if(telaJurosCompostosEditTextCapital.text.toString().isNotEmpty() && telaJurosCompostosEditTextCapital.text.toString().toDouble() > 0 &&
           TelaJurosCompostosEditTextPeriodo.text.toString().isNotEmpty() && TelaJurosCompostosEditTextPeriodo.text.toString().toDouble() > 0 &&
           telaJurosCompostosEditTextTaxa.text.toString().isNotEmpty() && telaJurosCompostosEditTextTaxa.text.toString().toDouble() > 0) {

            var calculadora =
                Calculadora()
            calculadora.capital = telaJurosCompostosEditTextCapital.text.toString().toDouble()
            calculadora.taxa = telaJurosCompostosEditTextTaxa.text.toString().toDouble()
            calculadora.periodo = TelaJurosCompostosEditTextPeriodo.text.toString().toDouble()
            calculadora.montante = calculadora.calcularMontanteJurosCompostos()
            calculadora.juros = calculadora.calcularJurosCompostos()


            GenericDialog.Builder(this)
                .setDialogFont(R.font.nunito_bold)
                .setDialogTheme(R.style.GenericDialogTheme)
                .setIcon(android.R.drawable.btn_radio)
                .setTitle("Montante: R$ ${(calculadora.calcularMontanteJurosCompostos().toString())}" +
                    "\nJuros: R$ ${(calculadora.calcularJurosCompostos().toString())}")
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

        telaJurosCompostosButtonLimpar.setOnClickListener {
        if (telaJurosCompostosEditTextCapital.text.toString().isNotEmpty() &&
            TelaJurosCompostosEditTextPeriodo.text.toString().isNotEmpty() &&
            telaJurosCompostosEditTextTaxa.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        textViewResultado.text = null
        textViewResultadoJuro.text = null
        telaJurosCompostosEditTextCapital.text = null
        TelaJurosCompostosEditTextPeriodo.text = null
        telaJurosCompostosEditTextTaxa.text = null
    }

}

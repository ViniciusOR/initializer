package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class TelaMontante : AppCompatActivity() {

    private lateinit var telaMontanteTextViewResultado: TextView
    private lateinit var telaMontanteEditTextCapital: EditText
    private lateinit var telaMontanteEditTextTaxaJuroComercial: EditText
    private lateinit var telaMontanteEditTextPeriodo: EditText
    private lateinit var telaMontanteButtonCalcular: Button
    private lateinit var telaMontanteButtonLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_montante)
        val database = FirebaseFirestore.getInstance()
        telaMontanteTextViewResultado = findViewById(R.id.telaMontanteTextViewResultado)
        telaMontanteEditTextCapital = findViewById(R.id.telaMontanteEditTextCapital)
        telaMontanteEditTextTaxaJuroComercial = findViewById(R.id.telaMontanteEditTextTaxa)
        telaMontanteEditTextPeriodo = findViewById(R.id.telaMontanteEditTextPeriodo)
        telaMontanteButtonCalcular = findViewById(R.id.telaMontanteButtonCalcular)
        telaMontanteButtonLimpar = findViewById(R.id.telaMontanteButtonLimpar)

        telaMontanteButtonCalcular.setOnClickListener {
            if (telaMontanteEditTextCapital.text.toString().isNotEmpty() && telaMontanteEditTextTaxaJuroComercial.text.toString().isNotEmpty()
                && telaMontanteEditTextPeriodo.text.toString().isNotEmpty() && telaMontanteEditTextCapital.text.toString().toDouble() > 0 &&
                telaMontanteEditTextTaxaJuroComercial.text.toString().toDouble() > 0 && telaMontanteEditTextPeriodo.text.toString().toDouble() > 0
            ) {
                var calculadora =
                    Calculadora()
                calculadora.capital = telaMontanteEditTextCapital.text.toString().toDouble()
                calculadora.taxa = telaMontanteEditTextTaxaJuroComercial.text.toString().toDouble()
                calculadora.periodo = telaMontanteEditTextPeriodo.text.toString().toDouble()
                calculadora.montante = calculadora.calcularMontanteJurosCompostos()

                GenericDialog.Builder(this)
                    .setDialogFont(R.font.nunito_bold)
                    .setDialogTheme(R.style.GenericDialogTheme)
                    .setIcon(android.R.drawable.btn_radio)
                    .setTitle("${(calculadora.CalcularMontante().toString() + " R$")}")
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
                longToast(getString(R.string.preencha_corretamente))
            }

        }

        telaMontanteButtonLimpar.setOnClickListener {
            if (telaMontanteEditTextCapital.text.toString().isNotEmpty() && telaMontanteEditTextTaxaJuroComercial.text.toString().isNotEmpty()
                && telaMontanteEditTextPeriodo.text.toString().isNotEmpty()
            ) {
                limpar()
            }
        }
    }

    fun limpar() {
        telaMontanteTextViewResultado.text = null
        telaMontanteEditTextCapital.text = null
        telaMontanteEditTextTaxaJuroComercial.text = null
        telaMontanteEditTextPeriodo.text = null
        longToast("Operação removida com sucesso.")

    }

}


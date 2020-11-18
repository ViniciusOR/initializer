package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_desconto_bancario.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaDescontoBancario : AppCompatActivity() {
    lateinit var telaDescontoBancarioTextViewResultado : TextView
    lateinit var telaDescontoBancarioEditTextTaxaAdministrativa : TextView
    lateinit var telaDescontoBancarioEditTextTaxa : TextView
    lateinit var telaDescontoBancarioEditTextMontante : TextView
    lateinit var telaDescontoBancarioEditTextPeriodo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_desconto_bancario)
        val database = FirebaseFirestore.getInstance()

    telaDescontoBancarioTextViewResultado = findViewById(R.id.telaDescontoBancarioTextViewResultado)
    telaDescontoBancarioEditTextMontante = findViewById(R.id.telaDescontoBancarioEditTextMontante)
    telaDescontoBancarioEditTextTaxaAdministrativa = findViewById(R.id.telaDescontoBancarioEditTextTaxaAdministrativa)
    telaDescontoBancarioEditTextTaxa = findViewById(R.id.telaDescontoBancarioEditTextTaxa)
    telaDescontoBancarioEditTextPeriodo = findViewById(R.id.telaDescontoBancarioEditTextPeriodo)


    telaDescontoBancarioButtonCalcular.setOnClickListener {
        if (telaDescontoBancarioEditTextPeriodo.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextPeriodo.text.toString().toDouble() > 0 &&
            telaDescontoBancarioEditTextTaxaAdministrativa.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextTaxaAdministrativa.text.toString().toDouble() > 0 &&
            telaDescontoBancarioEditTextTaxa.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextTaxa.text.toString().toDouble() > 0 &&
            telaDescontoBancarioEditTextMontante.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextMontante.text.toString().toDouble() > 0) {

                var calculadora =
                    Calculadora()
                calculadora.taxa = telaDescontoBancarioEditTextTaxa.text.toString().toDouble()
                calculadora.taxaAdministrativa = telaDescontoBancarioEditTextTaxaAdministrativa.text.toString().toDouble()
                calculadora.montante = telaDescontoBancarioEditTextMontante.text.toString().toDouble()
                calculadora.periodo = telaDescontoBancarioEditTextPeriodo.text.toString().toDouble()
                calculadora.desconto = calculadora.calcularDescontoBancario()

            GenericDialog.Builder(this)
                .setDialogFont(R.font.nunito_bold)
                .setDialogTheme(R.style.GenericDialogTheme)
                .setIcon(android.R.drawable.btn_radio)
                .setTitle("Desconto bancário: R$ ${(calculadora.calcularDescontoBancario().toString())}")
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

    telaDescontoBancarioButtonLimpar.setOnClickListener {
        if (telaDescontoBancarioEditTextPeriodo.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextTaxaAdministrativa.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextTaxa.text.toString().isNotEmpty() &&
            telaDescontoBancarioEditTextMontante.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaDescontoBancarioTextViewResultado.text = null
        telaDescontoBancarioEditTextMontante.text = null
        telaDescontoBancarioEditTextTaxa.text = null
        telaDescontoBancarioEditTextTaxaAdministrativa.text = null
        telaDescontoBancarioEditTextPeriodo.text = null
    }

}

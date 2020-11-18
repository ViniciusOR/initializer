package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_juro_comercial.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class TelaJuroComercial : AppCompatActivity() {

    private lateinit var telaJuroComercialEditTextTaxa : EditText
    private lateinit var telaJuroComercialTextViewResultado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_juro_comercial)
        val database = FirebaseFirestore.getInstance()

    telaJuroComercialEditTextTaxa = findViewById(R.id.telaJuroComercialEditTextTaxa)
    telaJuroComercialTextViewResultado = findViewById(R.id.telaJuroComercialTextViewResultadoTaxa)


    telaJuroComercialButtonCalcular.setOnClickListener {
        if (telaJuroComercialEditTextTaxa.text.toString().isNotEmpty() && telaJuroComercialEditTextTaxa.text.toString().toDouble() > 0) {
            var calculadora =
                Calculadora()
            calculadora.taxa = telaJuroComercialEditTextTaxa.text.toString().toDouble()
            calculadora.juros = calculadora.calcularJuroComercial()
//
          GenericDialog.Builder(this)
            .setDialogFont(R.font.nunito_bold)
            .setDialogTheme(R.style.GenericDialogTheme)
            .setIcon(android.R.drawable.btn_radio)
            .setTitle("${(calculadora.calcularJuroComercial())} %")
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

    telaJuroComercialButtonLimpar.setOnClickListener {
        if (telaJuroComercialEditTextTaxa.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaJuroComercialTextViewResultado.text = null
        telaJuroComercialEditTextTaxa.text = null
    }

}

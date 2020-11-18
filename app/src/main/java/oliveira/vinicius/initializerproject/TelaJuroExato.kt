package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_tela_juro_exato.*
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.toast

class TelaJuroExato : AppCompatActivity() {

    lateinit var telaJuroExatoTextViewResultadoTaxa : TextView
    lateinit var telaJuroExatoEditTextTaxa : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_juro_exato)
        val database = FirebaseFirestore.getInstance()

    telaJuroExatoTextViewResultadoTaxa = findViewById(R.id.telaJuroExatoTextViewResultadoTaxa)
    telaJuroExatoEditTextTaxa = findViewById(R.id.telaJuroExatoEditTextTaxa)

        telaJuroExatoButtonCalcular.setOnClickListener {
        if (telaJuroExatoEditTextTaxa.text.toString().isNotEmpty() && telaJuroExatoEditTextTaxa.text.toString().toDouble() > 0) {
            var calculadora =
                Calculadora()
            calculadora.taxa = telaJuroExatoEditTextTaxa.text.toString().toDouble()
            calculadora.juros = calculadora.calcularJuroExato()
//
          GenericDialog.Builder(this)
            .setDialogFont(R.font.nunito_bold)
            .setDialogTheme(R.style.GenericDialogTheme)
            .setIcon(android.R.drawable.btn_radio)
            .setTitle("${(calculadora.calcularJuroExato().toString())} %")
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

    telaJuroExatoButtonLimpar.setOnClickListener {
        if (telaJuroExatoEditTextTaxa.text.toString().isNotEmpty()) {
            limpar()
        }
    }

    }

    fun limpar() {
        telaJuroExatoTextViewResultadoTaxa.text = null
        telaJuroExatoEditTextTaxa.text = null
    }

}

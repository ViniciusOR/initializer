package oliveira.vinicius.initializerproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import pro.midev.expandedmenulibrary.ExpandedMenuClickListener
import pro.midev.expandedmenulibrary.ExpandedMenuItem

class MainActivity : AppCompatActivity(), ExpandedMenuClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expandableMenuViewId.setIcons(
            ExpandedMenuItem(R.drawable.ic_home_24dp, getString(R.string.home)),
            ExpandedMenuItem(R.drawable.ic_playlist_add_check_black_24dp, getString(R.string.minha_lista)),
            ExpandedMenuItem(R.drawable.ic_about_circle_black_24dp, getString(R.string.info))
        )

        expandableMenuViewId.setOnItemClickListener(this)

    buttonMontante.setOnClickListener {
        startActivity<TelaMontante>()
    }

    buttonJurosCompostos.setOnClickListener {
        startActivity<TelaJurosCompostos>()
    }

    buttonJuroExato.setOnClickListener {
        startActivity<TelaJuroExato>()
    }

    buttonJuroComercial.setOnClickListener {
        startActivity<TelaJuroComercial>()
    }

    buttonDescontoRacional.setOnClickListener {
        startActivity<TelaDescontoRacional>()
    }

    buttonDescontoComercial.setOnClickListener {
        startActivity<TelaDescontoComercial>()
    }

    buttonTaxaProporcional.setOnClickListener {
        alert(getString(R.string.recurso_indisponivel), getString(R.string.construcao)) {
            positiveButton("Ok!") {
                return@positiveButton
            }
        }.show()

    }

    buttonDescontoBancario.setOnClickListener {
        startActivity<TelaDescontoBancario>()
    }

    buttonTaxaEfetiva.setOnClickListener {
        alert(getString(R.string.recurso_indisponivel), getString(R.string.construcao)) {
            positiveButton("Ok!") {
                return@positiveButton
            }
        }.show()
    }

    }

    override fun onItemClick(position: Int) {

        when(position) {
            0 -> {
                startActivity<MainActivity>()
            }
            1 -> {
                startActivity<TelaLista>()
            }
            2 -> {
                alert("Desenvolvido por Vinícius de Oliveira e Mario Andrade." +
                        "\nOrientados por Thiago Cury." +
                        "\n19/12/19.", "Info") {
                    positiveButton("Ok!") {
                        return@positiveButton
                    }
                }.show()
            }
        }
    }

}

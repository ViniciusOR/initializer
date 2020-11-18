package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.expandableMenuViewId
import kotlinx.android.synthetic.main.activity_tela_lista.*
import oliveira.vinicius.initializerproject.adapters.CalculadoraAdapter
import oliveira.vinicius.initializerproject.modelo.Calculadora
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pro.midev.expandedmenulibrary.ExpandedMenuClickListener
import pro.midev.expandedmenulibrary.ExpandedMenuItem

class TelaLista : AppCompatActivity(), ExpandedMenuClickListener {

    val database = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null

    private lateinit var adapter: CalculadoraAdapter
    private var calculadoras = arrayListOf<Calculadora>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_lista)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth?.currentUser

        initialize()

        database.collection("contas")
            .get()
            .addOnSuccessListener { result ->

                if (result.isEmpty) {
                    toast(getString(R.string.nao_ha_operacoes))
                    return@addOnSuccessListener
                }

                for (document in result) {
                    calculadoras.add(document.toObject(Calculadora::class.java))
                }
                adapter.updateList()

            }
            .addOnFailureListener { exception -> toast(getString(R.string.erro_ao_consultar))  }

        expandableMenuViewId.setIcons(
            ExpandedMenuItem(R.drawable.ic_home_24dp, getString(R.string.home)),
            ExpandedMenuItem(R.drawable.ic_playlist_add_check_black_24dp, getString(R.string.minha_lista)),
            ExpandedMenuItem(R.drawable.ic_about_circle_black_24dp, getString(R.string.info))
        )

        expandableMenuViewId.setOnItemClickListener(this)

    }

    private fun itemClicked(calculadora: Calculadora) {

    }

    private fun initialize() {
        adapter = CalculadoraAdapter(calculadoras,this@TelaLista,
            {calculadora: Calculadora -> itemClicked(calculadora)})

        TelaListaRecyclerViewContas.adapter = adapter

        TelaListaRecyclerViewContas.layoutManager = LinearLayoutManager(this)

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

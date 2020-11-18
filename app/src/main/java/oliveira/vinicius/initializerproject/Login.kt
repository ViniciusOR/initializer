package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import oliveira.vinicius.initializerproject.modelo.Usuario
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pro.midev.expandedmenulibrary.ExpandedMenuClickListener
import pro.midev.expandedmenulibrary.ExpandedMenuItem

class Login : AppCompatActivity(), ExpandedMenuClickListener {

    private var mAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    private var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        expandableMenuViewId.setIcons(
            ExpandedMenuItem(R.drawable.ic_home_24dp, getString(R.string.home)),
            ExpandedMenuItem(R.drawable.ic_playlist_add_check_black_24dp, getString(R.string.minha_lista)),
            ExpandedMenuItem(R.drawable.ic_about_circle_black_24dp, getString(R.string.conta))
        )

        expandableMenuViewId.setOnItemClickListener(this)

        //INICIALIZAR
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth?.currentUser
        telaLoginProgress.visibility = View.INVISIBLE

        //ENTRAR
        telaLoginButtonEntrar.setOnClickListener {
            if(telaLoginEditTextLogin.text.toString().isEmpty()
                || telaLoginEditTextSenha.text.toString().isEmpty()) {
                toast("digite usuario e/ou senha")
                return@setOnClickListener
            }

            var usuario = Usuario()
            usuario.login = telaLoginEditTextLogin.text.toString()
            usuario.senha = telaLoginEditTextSenha.text.toString()
            telaLoginProgress.setVisibility(View.VISIBLE)

            mAuth?.signInWithEmailAndPassword(usuario.login.toString(),
                usuario.senha.toString())?.addOnCompleteListener(object: OnCompleteListener<AuthResult>{
                //task
                override fun onComplete(p0: Task<AuthResult>) {
                    telaLoginProgress.visibility = View.INVISIBLE

                    if(!p0.isSuccessful) {
                        toast("Usuário e senha inválidos!")
                        return
                    }

                    toast("Bem-vindo!")
                    startActivity<TelaLista>()
                    finish()
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth?.addAuthStateListener(mAuthStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthStateListener != null) {
            mAuth?.removeAuthStateListener(mAuthStateListener!!)
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
                startActivity<Login>()
            }
        }
    }
}

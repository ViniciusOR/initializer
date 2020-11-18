package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import dev.jai.genericdialog2.GenericDialog
import kotlinx.android.synthetic.main.activity_teste.*

class teste : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste)

        btn_click.setOnClickListener {

            GenericDialog.Builder(this)
                .setDialogFont(R.font.nunito_bold)
                .setDialogTheme(R.style.GenericDialogTheme)
                .setIcon(android.R.drawable.btn_radio)
                .setTitle("Alert  !").setTitleAppearance(R.color.colorPrimaryDark, 16f)
                .setMessage("Are you sure want to rate the app")
                .addNewButton(R.style.CustomButton) {
                    Toast.makeText(this@teste, "Remind me later Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
                .addNewButton(R.style.PositiveButton) {
                    Toast.makeText(
                        this@teste,
                        "OK Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addNewButton(R.style.NegativeButton) {
                    Toast.makeText(
                        this@teste,
                        "Cancel Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .setCancelable(true)
                .generate()

        }
    }
}

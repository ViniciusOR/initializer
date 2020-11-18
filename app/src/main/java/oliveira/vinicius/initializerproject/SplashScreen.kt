package oliveira.vinicius.initializerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.startActivity
import kotlin.random.Random

class SplashScreen : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    internal val mRunnable = Runnable {
        if (!isFinishing) {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        emitBubbles()

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
    }

    private fun emitBubbles() {
        Handler().postDelayed({
            val size = Random.nextInt(20, 80)
            bubbleEmitter.emitBubble(size)
            emitBubbles()
        }, Random.nextLong(100, 500))
    }

}

package william.miranda.brutusui.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val values = intArrayOf(1, 2, 3)
        val texts = arrayOf("Text 1", "Text 2", "Text 3")
        radioGroup.setOptions(values, texts)

        button.setOnClickListener {
            Log.d("BrutusUI", "${dayOfWeek.value.get()}")
        }
    }
}
package william.miranda.brutusui

import android.content.Context
import android.support.v7.widget.AppCompatSeekBar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar

class BrutusUISeekBar(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Int>(context, attrs) {

    val seekbar by lazy { findViewById<AppCompatSeekBar>(R.id.seekbar) }

    /**
     * Para este componente, apenas setamos o valor
     */
    override var value: Int? = null
        set(newValue) {
            field = newValue
            newValue?.let { seekbar.progress = it }
            changeListener(newValue)
        }

    /**
     * Como a interface tem mais de um metodo (Nao eh SAM, Single Abstract Method)
     * Precisamos criar o objeto e passar, como no Java Way
     */
    private val listener = object : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
            //nada a fazer
        }

        override fun onStartTrackingTouch(seekbar: SeekBar?) {
            //nada a fazer
        }

        override fun onStopTrackingTouch(seekbar: SeekBar?) {
            //Ao tirar o dedo, salvamos o valor
            value = seekbar?.progress
        }
    }

    /**
     * Infla o layout
     */
    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_seekbar, this)

        //obtem os elementos do XML
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            value = getInt(R.styleable.BrutusUIGeneric_value, 0)
            recycle()
        }

        //Define o change
        seekbar.setOnSeekBarChangeListener(listener)
    }
}
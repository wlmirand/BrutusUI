package william.miranda.brutusui

import android.content.Context
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet
import android.view.LayoutInflater

class BrutusUISwitch(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Boolean>(context, attrs) {

    val checkbox by lazy { findViewById<SwitchCompat>(R.id.enabled) }

    /**
     * Para este componente, apenas setamos o valor
     */
    override var value: Boolean? = null
    set(newValue) {
        field = newValue
        newValue?.let { checkbox.isChecked = it }
        changeListener(newValue)
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_switch, this)

        //obtem os elementos do XML
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            //Obtem o valor, caso passado
            value = getBoolean(R.styleable.BrutusUIGeneric_value, false)
            recycle()
        }

        //atualiza o valor ao fazer o toggle
        checkbox.setOnCheckedChangeListener {
            _, status ->
            value = status
        }
    }
}
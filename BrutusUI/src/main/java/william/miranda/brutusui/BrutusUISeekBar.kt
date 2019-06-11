package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import william.miranda.brutusui.databinding.BrutusuiSeekbarBinding

class BrutusUISeekBar(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Int>(context, attrs) {

    init {
        //Inflate the Layout
        BrutusuiSeekbarBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUISeekBar.title
            value = this@BrutusUISeekBar.value
        }

        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            value.set(getInt(R.styleable.BrutusUIGeneric_value, 0))
            recycle()
        }
    }
}
package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import william.miranda.brutusui.databinding.BrutusuiSwitchBinding

class BrutusUISwitch(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Boolean>(context, attrs) {

    /**
     * Render Function
     */
    override var renderFunction: (Boolean?) -> String? = { summary.get().toString() }

    init {
        //Inflate the Layout
        BrutusuiSwitchBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUISwitch.title
            summary = this@BrutusUISwitch.summary
            value = this@BrutusUISwitch.value
        }

        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            value.set(getBoolean(R.styleable.BrutusUIGeneric_value, false))
            recycle()
        }
    }
}
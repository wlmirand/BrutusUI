package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.brutusui_day_of_week.view.*
import william.miranda.brutusui.databinding.BrutusuiDayOfWeekBinding
import kotlin.math.pow

/**
 * Class to display a Picker for Day of Week
 * We save the Value as an Integer, which represents the sum of shifted values of a Boolean Array
 *
 * [ Sun, Mon, Tue, Wed, Thr, Fri, Sat ]
 *
 * For each day, we will have True (1) or False (0)
 *
 * But when we save the Value, we need to do a Bit shift operation that represents Power of Two
 */
class BrutusUIDayOfWeek(context: Context, attrs: AttributeSet) :
    BrutusUIGeneric<Int>(context, attrs) {

    companion object {
        //Masks for each day
        const val SUNDAY = 1
        const val MONDAY = SUNDAY.shl(1)
        const val TUESDAY = MONDAY.shl(1)
        const val WEDNESDAY = TUESDAY.shl(1)
        const val THURSDAY = WEDNESDAY.shl(1)
        const val FRIDAY = THURSDAY.shl(1)
        const val SATURDAY = FRIDAY.shl(1)

        /**
         * Get if a given button should be enabled
         */
        @JvmStatic
        fun isEnabled(position: Int, intValue: Int) = getBooleanArray(intValue)[position]

        /**
         * Get the BooleanArray from the Int Value
         */
        fun getBooleanArray(value: Int): BooleanArray {
            return BooleanArray(7).apply {
                this[0] = value and SUNDAY != 0
                this[1] = value and MONDAY != 0
                this[2] = value and TUESDAY != 0
                this[3] = value and WEDNESDAY != 0
                this[4] = value and THURSDAY != 0
                this[5] = value and FRIDAY != 0
                this[6] = value and SATURDAY != 0
            }
        }

        /**
         * Get the Int Value from the BooleanArray
         */
        fun getIntValue(array: BooleanArray): Int {

            //Start with zero
            var result = 0

            //Foreach
            for (i in array.indices) {
                //Get the Weight
                val weight = 2.0.pow(i.toDouble()).toInt()

                //Multiply to the value and add to the result
                result += array[i].toInt() * weight
            }

            return result
        }

        /**
         * Extension Function for Boolean Conversion
         */
        private fun Boolean.toInt() = if (this) 1 else 0
    }

    /**
     * Render Function
     */
    override var renderFunction: (Int) -> String? = { summary.get().toString() }

    /**
     * Click Listener for the Buttons
     */
    private val clickListener: (View) -> Unit = { view: View ->
        //Get the Button where we clicked
        val button = when (view.id) {
            R.id.btDomingo -> 0
            R.id.btSegunda -> 1
            R.id.btTerca -> 2
            R.id.btQuarta -> 3
            R.id.btQuinta -> 4
            R.id.btSexta -> 5
            R.id.btSabado -> 6
            else -> null
        }

        button?.let {
            //Get the current Array
            val array = getBooleanArray(value.get() ?: 0)

            //Toggle the Selected value
            array[it] = !array[it]

            //Update the value
            value.set(getIntValue(array))
        }
    }

    /**
     * Init block
     */
    init {
        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            //Set the Value
            value.set(getInt(R.styleable.BrutusUIGeneric_value, 0))

            recycle()
        }

        //Inflate the Layout
        BrutusuiDayOfWeekBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIDayOfWeek.title
            summary = this@BrutusUIDayOfWeek.summary
            value = this@BrutusUIDayOfWeek.value
        }
    }

    /**
     * When the View is ready
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //Define ClickLister for the View
        clickListener.let {
            btDomingo.setOnClickListener(it)
            btSegunda.setOnClickListener(it)
            btTerca.setOnClickListener(it)
            btQuarta.setOnClickListener(it)
            btQuinta.setOnClickListener(it)
            btSexta.setOnClickListener(it)
            btSabado.setOnClickListener(it)
        }
    }
}
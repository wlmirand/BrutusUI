package william.miranda.brutusui

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TimePicker
import william.miranda.brutusui.databinding.BrutusuiGenericBinding
import java.util.*

class BrutusUITimePicker(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Pair<Int, Int>>(context, attrs) {

    /**
     * Listener for TimePickerDialog
     */
    private val listener: (TimePicker, Int, Int) -> Unit = { _, hora: Int, minuto: Int ->
        value.set(Pair(hora, minuto))
    }

    /**
     * Render the time
     */
    override var renderFunction: (Pair<Int, Int>?) -> String? = {

        it?.takeIf { it.first != -1 && it.second != -1 }?.let {
            val date = Calendar.getInstance().run {
                set(Calendar.HOUR_OF_DAY, it.first)
                set(Calendar.MINUTE, it.second)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time
            }
            DateFormat.getTimeFormat(context).format(date)
        }
    }

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUITimePicker.title
            summary = this@BrutusUITimePicker.summary
        }

        //Now get the default value if passed
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUITimePicker)) {
            //if exists, loads
            val newHour: Int = getInt(R.styleable.BrutusUITimePicker_hour, -1)
            val newMinute: Int = getInt(R.styleable.BrutusUITimePicker_minute, -1)

            value.set(Pair(newHour, newMinute))
            recycle()
        }
    }

    /**
     * When View is ready
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        this.setOnClickListener { showDialog() }
    }

    /**
     * Show the Dialog
     */
    private fun showDialog() {
        val timePickerDialog = TimePickerDialog(
                context,
                listener,
                value.get()?.first ?: 0,
                value.get()?.second ?: 0,
                DateFormat.is24HourFormat(context)
        )

        timePickerDialog.setTitle(title.get())
        timePickerDialog.show()
    }
}
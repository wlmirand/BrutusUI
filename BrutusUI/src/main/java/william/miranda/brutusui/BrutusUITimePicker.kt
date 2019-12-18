package william.miranda.brutusui

import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TimePicker
import william.miranda.brutusui.databinding.BrutusuiGenericBinding
import java.util.*

/**
 * Data class to hold the Composite value
 */
data class Time(
    val hour: Int,
    val minute: Int
)

/**
 * Component Class
 */
class BrutusUITimePicker(context: Context, attrs: AttributeSet) :
    BrutusUIGeneric<Time>(context, attrs) {

    /**
     * Listener for TimePickerDialog
     */
    private val listener: (TimePicker, Int, Int) -> Unit = { _, hora: Int, minuto: Int ->
        value.set(Time(hora, minuto))
    }

    /**
     * Formatter
     */
    var timeFormatter = android.text.format.DateFormat.getTimeFormat(context)
        set(value) {
            field = value
            renderSummary()
        }

    /**
     * Render the time
     */
    override var renderFunction: (Time) -> String? = {
        val date = Calendar.getInstance().run {
            set(Calendar.HOUR_OF_DAY, it.hour)
            set(Calendar.MINUTE, it.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            time
        }
        timeFormatter.format(date)
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
            val hour = getInt(R.styleable.BrutusUITimePicker_hour, -1)
            val minute = getInt(R.styleable.BrutusUITimePicker_minute, -1)
            recycle()

            value.set(Time(hour, minute))
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
            value.get()?.hour ?: 0,
            value.get()?.minute ?: 0,
            android.text.format.DateFormat.is24HourFormat(context)
        )

        timePickerDialog.setTitle(title.get())
        timePickerDialog.show()
    }
}
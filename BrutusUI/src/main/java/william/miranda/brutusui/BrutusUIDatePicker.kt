package william.miranda.brutusui

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import william.miranda.brutusui.databinding.BrutusuiGenericBinding
import java.util.*

/**
 * Data class to hold the Composite value
 */
data class Date(
    val year: Int,
    val month: Int,
    val day: Int
)

/**
 * Component class
 */
class BrutusUIDatePicker(context: Context, attrs: AttributeSet) :
    BrutusUIGeneric<Date>(context, attrs) {

    /**
     * Listener for DatePickerDialog
     */
    private val listener: (DatePicker, Int, Int, Int) -> Unit =
        { _, year: Int, month: Int, day: Int ->
            value.set(Date(year, month, day))
        }

    /**
     * Formatter
     */
    var dateFormatter = android.text.format.DateFormat.getDateFormat(context)
        set(value) {
            field = value
            renderSummary()
        }

    /**
     * Render the Value
     */
    override var renderFunction: (Date) -> String? = {
        val date = Calendar.getInstance().run {
            set(Calendar.YEAR, it.year)
            set(Calendar.MONTH, it.month)
            set(Calendar.DAY_OF_MONTH, it.day)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            time
        }
        dateFormatter.format(date)
    }

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIDatePicker.title
            summary = this@BrutusUIDatePicker.summary
        }

        //Now get the default value if passed. If not set, let be the Now date
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIDatePicker)) {
            //if exists, loads
            val day = getInt(R.styleable.BrutusUIDatePicker_day, -1)
            val month = getInt(R.styleable.BrutusUIDatePicker_month, -1)
            val year = getInt(R.styleable.BrutusUIDatePicker_year, -1)
            recycle()

            //If values are not set, nothing to do
            if (day == -1 || month == -1 || year == -1) return@with

            //If set, we update the value
            value.set(Date(year, month, day))
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
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            listener,
            value.get()?.year ?: calendar.get(Calendar.YEAR),
            value.get()?.month ?: calendar.get(Calendar.MONTH),
            value.get()?.day ?: calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.setTitle(title.get())
        datePickerDialog.show()
    }
}
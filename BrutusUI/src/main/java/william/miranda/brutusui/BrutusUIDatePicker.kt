package william.miranda.brutusui

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import william.miranda.brutusui.databinding.BrutusuiGenericBinding
import java.text.SimpleDateFormat
import java.util.*

class BrutusUIDatePicker(context: Context, attrs: AttributeSet) :
    BrutusUIGeneric<Triple<Int, Int, Int>>(context, attrs) {

    /**
     * Listener for DatePickerDialog
     */
    private val listener: (DatePicker, Int, Int, Int) -> Unit = { _, year: Int, month: Int, day: Int ->
        value.set(Triple(year, month, day))
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
     * Render the time
     */
    override var renderFunction: (Triple<Int, Int, Int>?) -> String? = {

        it?.takeIf { it.first != -1 && it.second != -1 && it.third != -1 }?.let {
            val date = Calendar.getInstance().run {
                set(Calendar.YEAR, it.first)
                set(Calendar.MONTH, it.second)
                set(Calendar.DAY_OF_MONTH, it.third)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time
            }
            dateFormatter.format(date)
        }
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
            val format = getString(R.styleable.BrutusUIDatePicker_dateFormat)
            recycle()

            if (format != null) {
                dateFormatter = SimpleDateFormat(format)
            }

            //If values are not set, nothing to do
            if (day == -1 || month == -1 || year == -1) return@with

            //If set, we update the value
            value.set(Triple(year, month, day))
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
            value.get()?.first ?: calendar.get(Calendar.YEAR),
            value.get()?.second ?: calendar.get(Calendar.MONTH),
            value.get()?.third ?: calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.setTitle(title.get())
        datePickerDialog.show()
    }
}
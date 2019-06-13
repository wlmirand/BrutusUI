package william.miranda.brutusui

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import william.miranda.brutusui.databinding.BrutusuiGenericBinding
import java.text.DecimalFormat

/**
 * Class to store Integer values
 */
class BrutusUIEditInt(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Int>(context, attrs) {

    /**
     * Render Function
     */
    override var renderFunction: (Int) -> String? = {
        numberFormatter.format(it)
    }

    /**
     * Number Formatter
     */
    var numberFormatter = DecimalFormat.getNumberInstance()
        set(newFormatter) {
            field = newFormatter
            renderSummary()
        }

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIEditInt.title
            summary = this@BrutusUIEditInt.summary
        }

        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            if (hasValue(R.styleable.BrutusUIGeneric_value)) {
                value.set(getInt(R.styleable.BrutusUIGeneric_value, 0))
            }

            recycle()
        }
    }

    /**
     * When the View is ready
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //Define ClickLister for the View
        this.setOnClickListener { showDialog() }
    }

    /**
     * Mostra a dialog
     */
    private fun showDialog() {
        val dialog = with(AlertDialog.Builder(context, androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert)) {
            setTitle(title.get())
            setView(R.layout.brutusui_dialog_edittext)

            setPositiveButton(android.R.string.ok) { di, _ ->
                //first get the String value
                val strValue = (di as AlertDialog).findViewById<EditText>(R.id.dialog_edittext)?.text?.toString()

                //Save the inputted value
                value.set( strValue?.takeIf { strValue.isNotEmpty() }?.toInt() )
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }

        //Define the InputType
        dialog.findViewById<EditText>(R.id.dialog_edittext)?.inputType = InputType.TYPE_CLASS_NUMBER

        //If we have some value, put it in the Dialog
        value.get()?.let { dialog.findViewById<EditText>(R.id.dialog_edittext)?.setText(it.toString()) }
    }
}
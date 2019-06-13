package william.miranda.brutusui

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import william.miranda.brutusui.databinding.BrutusuiGenericBinding

/**
 * Class to store Integer values
 */
class BrutusUIEditFloat(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Float>(context, attrs) {

    /**
     * Render Function
     */
    override var renderFunction: (Float?) -> String? = { it?.toString() }

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIEditFloat.title
            summary = this@BrutusUIEditFloat.summary
        }

        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            if (hasValue(R.styleable.BrutusUIGeneric_value)) {
                value.set(getFloat(R.styleable.BrutusUIGeneric_value, 0f))
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
        val dialog =
            with(AlertDialog.Builder(context, androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert)) {
                setTitle(title.get())
                setView(R.layout.brutusui_dialog_edittext)

                setPositiveButton(android.R.string.ok) { di, _ ->
                    //first get the String value
                    val strValue = (di as AlertDialog).findViewById<EditText>(R.id.dialog_edittext)?.text?.toString()

                    //Save the inputted value
                    value.set(strValue?.takeIf { strValue.isNotEmpty() }?.toFloat())
                }
                setNegativeButton(android.R.string.cancel, null)
                show()
            }

        //Define the InputType
        dialog.findViewById<EditText>(R.id.dialog_edittext)?.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED

        //If we have some value, put it in the Dialog
        value.get()?.let { dialog.findViewById<EditText>(R.id.dialog_edittext)?.setText(it.toString()) }
    }
}
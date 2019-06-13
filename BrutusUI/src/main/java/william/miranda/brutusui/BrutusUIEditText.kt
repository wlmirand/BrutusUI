package william.miranda.brutusui

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import william.miranda.brutusui.databinding.BrutusuiGenericBinding

/**
 * Classe para Guardar texto
 */
class BrutusUIEditText(context: Context, attrs: AttributeSet) : BrutusUIGeneric<String>(context, attrs) {

    /**
     * Method to convert the value to String
     */
    override var renderFunction: (String) -> String? = { it?.takeIf { it.isNotEmpty() } }

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIEditText.title
            summary = this@BrutusUIEditText.summary
        }

        //Get the value from XML and set to the Field
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            value.set(getText(R.styleable.BrutusUIGeneric_value)?.toString())
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
     * Show the Dialog
     */
    private fun showDialog() {
        val dialog = with(AlertDialog.Builder(context, androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert)) {
            setTitle(title.get())
            setView(R.layout.brutusui_dialog_edittext)

            setPositiveButton(android.R.string.ok) { di, _ ->
                //Save the inputted value
                value.set( (di as AlertDialog).findViewById<EditText>(R.id.dialog_edittext)?.text.toString() )
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }

        //Define the InputType
        dialog.findViewById<EditText>(R.id.dialog_edittext)?.inputType = InputType.TYPE_CLASS_TEXT

        //If we have some value, put it in the Dialog
        value.get()?.let { dialog.findViewById<EditText>(R.id.dialog_edittext)?.setText(it) }
    }
}
package william.miranda.brutusui

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText

/**
 * Classe para Guardar texto
 */
class BrutusUIEditInt(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Int>(context, attrs) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_generic, this)

        //Obtem o valor, caso passado
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            value = getText(R.styleable.BrutusUIGeneric_value)?.toString()?.toInt()
            recycle()
        }
    }

    /**
     * Adiciona o onClick ao inflar as Views
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //seta o click na view inteira
        this.setOnClickListener { showDialog() }
    }

    /**
     * Mostra a dialog
     */
    private fun showDialog() {
        val dialog = with(AlertDialog.Builder(context)) {
            setTitle(title)
            setView(R.layout.brutusui_dialog_edittext)

            setPositiveButton(android.R.string.ok) { di, _ ->
                //salva o valor... a regra do setter se encarrega de atualizar o summary
                value = (di as AlertDialog).findViewById<EditText>(R.id.dialog_edittext)?.text?.toString()?.toInt()
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }

        //Define o InputType apos o show
        dialog.findViewById<EditText>(R.id.dialog_edittext)?.inputType = InputType.TYPE_CLASS_NUMBER

        //Se ja temos valor, colocamos como default no dialog
        value?.let {
            dialog.findViewById<EditText>(R.id.dialog_edittext)?.setText(it.toString())
        }
    }
}
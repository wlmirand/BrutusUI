package william.miranda.brutusui

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TimePicker
import java.util.*

class BrutusUITimePicker(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Long>(context, attrs) {

    /**
     * Listener para o TimePicker
     */
    val listener: (TimePicker, Int, Int) -> Unit = { _, hora: Int, minuto: Int ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hora)
        calendar.set(Calendar.MINUTE, minuto)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        value = calendar.timeInMillis
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_generic, this)
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
        val hour = getHora()
        val minute = getMinuto()
        val use24HourClock = DateFormat.is24HourFormat(context)

        val timePickerDialog = TimePickerDialog(context, listener, hour, minute, use24HourClock)
        timePickerDialog.setTitle(title)
        timePickerDialog.show()
    }

    /**
     * Obtem a hora a partir do valor
     */
    private fun getHora(): Int {
        return value?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.get(Calendar.HOUR_OF_DAY)
        } ?: 0
    }

    /**
     * Obtem o minuto a partir do valor
     */
    private fun getMinuto(): Int {
        return value?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.get(Calendar.MINUTE)
        } ?: 0
    }

    /**
     * Metodo para mostrar HORA:MINUTO
     */
    override var renderFunction: (Long) -> String = {
        //Inicia o calendario com o timestamp atual
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = it

        //obtem os campos
        val hora = calendar.get(Calendar.HOUR_OF_DAY)
        val minuto = calendar.get(Calendar.MINUTE)

        //retorna a String no formato HH:MM
        "%02d:%02d".format(hora, minuto)
    }
}
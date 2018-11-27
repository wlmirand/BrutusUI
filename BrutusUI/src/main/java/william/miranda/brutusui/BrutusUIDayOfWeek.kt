package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.ToggleButton
import java.util.*

class BrutusUIDayOfWeek(context: Context, attrs: AttributeSet) : BrutusUIGeneric<BooleanArray>(context, attrs) {

    /**
     * Faz override para inicializar o array
     */
    override var value: BooleanArray? = BooleanArray(7) //inicialmente cria vazio
    set(newValue) {
        //se eh valido, atribui
        if (newValue != null) {
            field = newValue
        } else {//senao vazio
            value = BooleanArray(7)
        }

        //Atualiza a UI para representar o valor
        updateButtons()

        //Invoca o Listener
        changeListener(newValue)
    }

    /**
     * Views do Layout
     */
    private val btDomingo by lazy { findViewById<ToggleButton>(R.id.btDomingo) }
    private val btSegunda by lazy { findViewById<ToggleButton>(R.id.btSegunda) }
    private val btTerca by lazy { findViewById<ToggleButton>(R.id.btTerca) }
    private val btQuarta by lazy { findViewById<ToggleButton>(R.id.btQuarta) }
    private val btQuinta by lazy { findViewById<ToggleButton>(R.id.btQuinta) }
    private val btSexta by lazy { findViewById<ToggleButton>(R.id.btSexta) }
    private val btSabado by lazy { findViewById<ToggleButton>(R.id.btSabado) }

    /**
     * Listener compartilhado por todos botoes
     */
    val listener: (cb: CompoundButton, status: Boolean) -> Unit = {cb: CompoundButton, status: Boolean ->
        val index = getIndexFromButton(cb.id)
        index?.let { value?.set(it, status) }
    }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_day_of_week, this)

        btDomingo.setOnCheckedChangeListener(listener)
        btSegunda.setOnCheckedChangeListener(listener)
        btTerca.setOnCheckedChangeListener(listener)
        btQuarta.setOnCheckedChangeListener(listener)
        btQuinta.setOnCheckedChangeListener(listener)
        btSexta.setOnCheckedChangeListener(listener)
        btSabado.setOnCheckedChangeListener(listener)
    }

    /**
     * Atualiza os botoes da UI
     */
    private fun updateButtons() {
        value?.let {
            var i = 0
            for(bool in it) {
                getButtonFromIndex(i++)?.isChecked = bool
            }
        }
    }

    /**
     * Dado o indice, obtem o botao
     */
    private fun getButtonFromIndex(index: Int): CompoundButton? {
        return when(index+1) {
            Calendar.SUNDAY -> btDomingo
            Calendar.MONDAY -> btSegunda
            Calendar.TUESDAY -> btTerca
            Calendar.WEDNESDAY -> btQuarta
            Calendar.THURSDAY -> btQuinta
            Calendar.FRIDAY -> btSexta
            Calendar.SATURDAY -> btSabado
            else -> null
        }
    }

    /**
     * Dado o botao, obtem o indice
     */
    private fun getIndexFromButton(resId: Int): Int? {
        val ret = when (resId) {
            R.id.btDomingo -> Calendar.SUNDAY
            R.id.btSegunda -> Calendar.MONDAY
            R.id.btTerca -> Calendar.TUESDAY
            R.id.btQuarta -> Calendar.WEDNESDAY
            R.id.btQuinta -> Calendar.THURSDAY
            R.id.btSexta -> Calendar.FRIDAY
            R.id.btSabado -> Calendar.SATURDAY
            else -> null
        }

        return ret?.minus(1)
    }
}
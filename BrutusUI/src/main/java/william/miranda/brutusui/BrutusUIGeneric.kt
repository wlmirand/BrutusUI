package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

abstract class BrutusUIGeneric<T>(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    /**
     * Valores do titulo e summary
     */
    var title: String? = null
    var summary: String? = null

    /**
     * Valor do campo... por default a gente atualiza o summary
     */
    open var value: T? = null
        set(newValue) {
            field = newValue
            updateSummary()
            changeListener(newValue)
        }

    /**
     * Change Listener, para podermos notificar quando o valor muda
     */
    var changeListener: (T?) -> Unit = {}

    /**
     * Metodo para "renderizar" o objeto
     */
    open var renderFunction: (T) -> String = { it.toString() }

    /**
     * Apos rodar o construtor, roda o bloco init
     */
    init {
        //obtem os elementos do XML e seta o titulo e summary
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            title = getText(R.styleable.BrutusUIGeneric_title) as String?
            summary = getText(R.styleable.BrutusUIGeneric_summary) as String?
            recycle()
        }
    }

    /**
     * Ao terminar de inflar, seta o corpo
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //seta o titulo e o sumario caso existam
        findViewById<TextView>(R.id.title)?.text = title
        findViewById<TextView>(R.id.summary)?.text = summary
    }

    /**
     * Metodo para atualizar o Summary de acordo com o valor
     */
    fun updateSummary() {
        findViewById<TextView>(R.id.summary)?.text =
                value?.takeIf { it.toString().isNotEmpty() }?.let { renderFunction(it) } ?: summary
    }
}
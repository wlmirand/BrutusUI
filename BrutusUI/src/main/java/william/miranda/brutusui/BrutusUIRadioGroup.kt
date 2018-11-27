package william.miranda.brutusui

import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView

class BrutusUIRadioGroup(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Int>(context, attrs) {

    /**
     * Aki, atualiza para o Label e nao para o valor
     */
    override var value: Int? = null
        set(newValue) {
            //assigna o valor
            field = newValue

            //na inicializacao, chamaremos o set direto, entao precisa atualizar o selected
            selected = getIndexFromValue(newValue)

            //mostra o texto do indice selecionado
            options?.let { findViewById<TextView>(R.id.summary).text = it[selected] }

            changeListener(newValue)
        }

    /**
     * Arrays de chaves -> valores para popular
     */
    private var options: Array<CharSequence>? = null
    private var values: IntArray? = null

    /**
     * Indice do elemento selecionado
     */
    private var selected: Int = 0

    /**
     * Bloco init, para inicializar os dados a serem mostrados
     */
    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_generic, this)

        // Obtem os elementos do XML...
        // Para string array, tem metodo pronto
        // Para Int, precisamos obter o ResID e entao obter o array
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIRadioGroup)) {
            options = getTextArray(R.styleable.BrutusUIRadioGroup_optTexts)
            val valuesResId = getResourceId(R.styleable.BrutusUIRadioGroup_optValues, 0)
            values = resources.getIntArray(valuesResId)
            recycle()
        }

        //Agora carrega o valor default caso tenha sido passado
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            //se existe, carregamos
            if (hasValue(R.styleable.BrutusUIGeneric_value)) {
                //carrega o valor
                value = getInt(R.styleable.BrutusUIGeneric_value, -1)
            }

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

        //seta o valor
        values?.let {
            if (selected >= 0) {
                value = it[selected]
            }
        }
    }

    /**
     * Mostra a dialog
     */
    private fun showDialog() {
        with(AlertDialog.Builder(context)) {
            setTitle(title)
            setSingleChoiceItems(options, selected) { _, which -> selected = which }
            setPositiveButton(android.R.string.ok) { _, _ ->
                values?.let { value = it[selected] }
            }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    /**
     * Retorna o indice a partir do valor
     */
    private fun getIndexFromValue(newValue: Int?): Int {
        if (newValue == null) return 0

        var index = 0
        values?.let {
            for (v in it) {
                if (v == newValue) return index
                index++
            }
        }

        return 0
    }
}
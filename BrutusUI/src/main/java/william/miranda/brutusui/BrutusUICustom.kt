package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater

/**
 * Classe para mostrar o layout e deixar a cargo do usuario definir o onClick
 * Utilizada para logicas mais complexas como por exemplo o ringtone picker
 * que depende de contexto e de ActivityForResult
 */
class BrutusUICustom<T>(context: Context, attrs: AttributeSet) : BrutusUIGeneric<T>(context, attrs) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.brutusui_generic, this)
    }
}
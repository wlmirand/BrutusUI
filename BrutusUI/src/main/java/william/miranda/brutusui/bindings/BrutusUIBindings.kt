package william.miranda.brutusui.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import william.miranda.brutusui.BrutusUIGeneric

object BrutusUIBindings {

    @BindingAdapter("value")
    @JvmStatic
    fun <T> set(view: BrutusUIGeneric<T>, newValue: T?) {
        val oldValue = view.value.get()
        if (newValue != oldValue) {
            view.value.set(newValue)
        }
    }

    @InverseBindingAdapter(attribute = "value")
    @JvmStatic
    fun <T> get(view: BrutusUIGeneric<T>) : T? {
        return view.value.get()
    }

    @BindingAdapter("valueAttrChanged")
    @JvmStatic fun <T> setListener(view: BrutusUIGeneric<T>, listener: InverseBindingListener?) {
        view.changeListener = { listener?.onChange() }
    }
}


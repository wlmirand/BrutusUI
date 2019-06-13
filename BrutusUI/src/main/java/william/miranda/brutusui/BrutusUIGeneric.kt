package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.databinding.Observable
import androidx.databinding.ObservableField

/**
 * Generic View Class that holds value of type T
 */
abstract class BrutusUIGeneric<T>(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    /**
     * Values for title and summary
     */
    protected val title = ObservableField<String>()
    protected val summary = ObservableField<String>()

    /**
     * Store the default Summary Value
     */
    private val defaultSummary: String

    /**
     * Field value
     */
    val value: ObservableField<T> = ObservableField<T>().apply {
        this.addOnPropertyChangedCallback(
                object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        //Render the Summary
                        renderSummary()

                        //Call the Listener
                        changeListener?.invoke( (sender as ObservableField<T>).get() )
                    }
                }
        )
    }

    /**
     * Change Listener, to notify someone that is interested to know if the value was changed
     */
    open var changeListener: ((newValue: T?) -> Unit)? = null

    /**
     * Method to convert the value to String
     */
    open var renderFunction: (T) -> String? = { it.toString() }

    /**
     * Init block
     */
    init {
        //Get values from XML and fill the properties with the values
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            title.set(getText(R.styleable.BrutusUIGeneric_title) as String)

            defaultSummary = getText(R.styleable.BrutusUIGeneric_summary) as String
            summary.set(defaultSummary)

            recycle()
        }
    }

    /**
     * Method to update the Summary
     * If we have a valid value, put the value
     * If not, put the Summary.
     */
    internal open fun renderSummary() {
        summary.set( value.get()?.let { renderFunction(it) } ?: defaultSummary)
    }
}
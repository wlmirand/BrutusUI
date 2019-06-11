package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import william.miranda.brutusui.databinding.BrutusuiGenericBinding

/**
 * Class to display a List of items to be chosen
 */
class BrutusUIRadioGroup(context: Context, attrs: AttributeSet) : BrutusUIGeneric<Pair<Int, String>>(context, attrs) {

    /**
     * Map that contains the Values -> Strings
     */
    private val map = mutableMapOf<Int, String>()

    /**
     * Render Function to put the String in the Summary
     */
    override var renderFunction: (Pair<Int, String>?) -> String? = {
        value.get()?.second
    }

    /**
     * Holds the Selected Pair to be saved if the user press OK
     */
    private var selectedPair: Pair<Int, String>? = null

    init {
        //Inflate the Layout
        BrutusuiGenericBinding.inflate(LayoutInflater.from(context), this, true).run {
            title = this@BrutusUIRadioGroup.title
            summary = this@BrutusUIRadioGroup.summary
        }

        // Get stuff from the XML
        // For StringArray we can read it directly
        // For Ins, we need to get the ResourceID and then Get the Array itself
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIRadioGroup)) {
            //Get the String Array
            val textArray = getTextArray(R.styleable.BrutusUIRadioGroup_optTexts)

            //Get the IntArray Res ID and then the Array itself
            val valuesResId = getResourceId(R.styleable.BrutusUIRadioGroup_optValues, 0)
            val valuesArray = resources.getIntArray(valuesResId)

            //Now fill the map
            for (i in 0 until textArray.size) {
                map[valuesArray[i]] = textArray[i].toString()
            }

            recycle()
        }

        //Now get the default value if passed
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            //if exists, Get the passed value
            val newValue = getInt(R.styleable.BrutusUIGeneric_value, -1)

            //Set it
            value.set(getPairFromValue(newValue))

            recycle()
        }
    }

    /**
     * When Views are ready
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //Set ClickListener for the View
        this.setOnClickListener { showDialog() }
    }

    /**
     * Show the Dialog
     */
    private fun showDialog() {
        val selectedIndex = getSelectedIndex()

        with(AlertDialog.Builder(context)) {
            setTitle(title.get())
            setSingleChoiceItems(map.values.toTypedArray(), selectedIndex) { _, newIndex ->
                //Get the Selected Pair from Index
                selectedPair = map.toList()[newIndex]
            }

            setPositiveButton(android.R.string.ok) { _, _ ->
                //Update the Selected Pair
                value.set(selectedPair)
            }

            //setPositiveButton(android.R.string.ok) { _, _ -> /*values?.let { value.set(it[selected]) }*/ }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    /**
     * Get the Selected Index from the Selected Pair
     */
    private fun getSelectedIndex() = map.toList().indexOf(value.get())

    /**
     * Return the Pair from the selected Value
     */
    private fun getPairFromValue(newValue: Int) : Pair<Int, String>? {
        //If valid, assign the Pair to the value
        return newValue.takeIf { it != -1 }?.let { validValue ->
            map[validValue]?.let {
                Pair(validValue, it)
            }
        }
    }
}
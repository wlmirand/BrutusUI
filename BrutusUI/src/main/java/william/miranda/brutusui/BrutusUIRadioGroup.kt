package william.miranda.brutusui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import william.miranda.brutusui.databinding.BrutusuiGenericBinding

/**
 * Class to display a List of items to be chosen
 */
class BrutusUIRadioGroup(context: Context, attrs: AttributeSet) :
    BrutusUIGeneric<Int>(context, attrs) {

    /**
     * Map that contains the Values -> Strings
     */
    private val map = mutableMapOf<Int, String>()

    /**
     * Render Function to put the String in the Summary
     */
    override var renderFunction: (Int) -> String? = {
        map[it]
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

            //Nothing to do here
            if (textArray == null || valuesResId == 0) return@with

            //Update the Values
            setOptions(resources.getIntArray(valuesResId), textArray)

            //Dispose the StyledAttrs
            recycle()
        }

        //Now get the default value if passed
        with(context.obtainStyledAttributes(attrs, R.styleable.BrutusUIGeneric)) {
            //if exists, Get the passed value
            val newValue = getInt(R.styleable.BrutusUIGeneric_value, -1)

            //Set it
            value.set(newValue)

            //Dispose the StyledAttrs
            recycle()
        }
    }

    /**
     * Update the Internal Map
     */
    fun setOptions(values: IntArray, texts: Array<CharSequence>) {
        //Clear the Map
        map.clear()

        //Put the new Values
        for (i in texts.indices) {
            map[values[i]] = texts[i].toString()
        }
    }

    /**
     * Update the Internal Map
     * Override for String Array
     */
    fun setOptions(values: IntArray, texts: Array<String>) {
        //Clear the Map
        map.clear()

        //Put the new Values
        for (i in texts.indices) {
            map[values[i]] = texts[i]
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
                value.set(selectedPair?.first)
            }

            //setPositiveButton(android.R.string.ok) { _, _ -> /*values?.let { value.set(it[selected]) }*/ }
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    /**
     * Get the Selected Index from the Selected Pair
     */
    private fun getSelectedIndex() = map.toList().indexOf(selectedPair)
}
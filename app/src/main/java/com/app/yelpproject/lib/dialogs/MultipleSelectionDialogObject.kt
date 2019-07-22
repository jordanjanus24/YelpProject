package com.app.yelpproject.lib.dialogs

import android.app.Activity
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.app.yelpproject.lib.dialogs.button.DialogButton

class MultipleSelectionDialogObject {
    companion object {
        fun create(
            activity: Activity,
            title: String? = null,
            message: String,
            options: Array<String?>,
            selectedItems: List<String>? = null,
            positive: MultiSelectDialogButton? = null,
            negative: DialogButton? = null
        ) = AlertDialog.Builder(activity).apply {
            setTitle(title)
            val arr = ArrayList<String>()
            setMessage(message)
            options.forEach {
                Log.d("OptionsIn",it)
            }
            setMultiChoiceItems(
                options, getCheckedItems(options, selectedItems)
            ) { _, which, isChecked ->
                if (isChecked)
                    arr.add(options[which]!!)
                else
                    if (arr.contains(options[which]!!)) arr.remove(options[which]!!)
            }
            setCancelable(false)
            if (positive != null) {
                setPositiveButton(positive.title) { dialog, _ ->
                    positive.callBack?.invoke(dialog, arr)
                }
            }
            if (negative != null) {
                setNegativeButton(negative.title, negative.onClickListener)
            }
        }.create()

        private fun getCheckedItems(options: Array<String?>, selectedItems: List<String>? = null): BooleanArray? {
            val array = ArrayList<Boolean>()
            if(selectedItems==null) return null
            options.forEach {
                if (selectedItems.contains(it)) array.add(true)
                else array.add(false)
            }
            return array.toBooleanArray()
        }
    }


}

class MultiSelectDialogButton(
    val title: String,
    val callBack: ((DialogInterface, ArrayList<String>) -> Unit)? = null
)
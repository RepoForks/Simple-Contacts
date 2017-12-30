package com.simplemobiletools.contacts.dialogs

import android.support.v7.app.AlertDialog
import com.simplemobiletools.commons.extensions.setupDialogStuff
import com.simplemobiletools.contacts.R
import com.simplemobiletools.contacts.activities.SimpleActivity
import com.simplemobiletools.contacts.adapters.AddFavoritesAdapter
import com.simplemobiletools.contacts.extensions.config
import com.simplemobiletools.contacts.helpers.ContactsHelper
import com.simplemobiletools.contacts.models.Contact
import kotlinx.android.synthetic.main.dialog_add_favorites.view.*

class AddFavoritesDialog(val activity: SimpleActivity, val callback: () -> Unit) {
    var dialog: AlertDialog? = null
    private var view = activity.layoutInflater.inflate(R.layout.dialog_add_favorites, null)

    init {
        ContactsHelper(activity).getContacts {
            Contact.sorting = activity.config.sorting
            it.sort()
            view.add_favorites_list.adapter = AddFavoritesAdapter(activity, it)

            activity.runOnUiThread {
                dialog = AlertDialog.Builder(activity)
                        .setPositiveButton(R.string.ok, { dialog, which -> dialogConfirmed() })
                        .setNegativeButton(R.string.cancel, null)
                        .create().apply {
                    activity.setupDialogStuff(view, this)
                }
            }
        }
    }

    private fun dialogConfirmed() {
        val selectedItems = (view.add_favorites_list.adapter as AddFavoritesAdapter).getSelectedItemsSet()
        dialog?.dismiss()
    }
}

package jp.co.yumemi.android.code_check.common

import android.content.Context
import androidx.appcompat.app.AlertDialog
import jp.co.yumemi.android.code_check.R

fun Context.showDialog() {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.error_dialog_title))
        .setMessage(getString(R.string.error_dialog_text))
        .setPositiveButton(getString(R.string.error_dialog_button)) { _, _ ->
        }
        .show()
}
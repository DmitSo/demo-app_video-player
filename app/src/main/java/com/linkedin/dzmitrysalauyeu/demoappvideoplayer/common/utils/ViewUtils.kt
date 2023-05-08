package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.R

fun makeAlert(
    context: Context,
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok) { _,_ -> onDismiss() }
        .setOnDismissListener { onDismiss() }
        .create()
        .show()
}

fun makeAlert(
    context: Context,
    titleId: Int,
    messageId: Int,
    onDismiss: () -> Unit
) {
    makeAlert(
        context,
        context.getString(titleId),
        context.getString(messageId),
        onDismiss
    )
}

fun makeErrorAlert(
    context: Context,
    throwable: Throwable,
    onDismiss: () -> Unit
) {
    val errorMessage = throwable.localizedMessage ?: throwable.message ?: context.getString(R.string.common_error_no_throwable)
    val msg = context.getString(R.string.common_error_ocurred_message, errorMessage)

    makeAlert(
        context,
        title = context.getString(R.string.common_error_title),
        message = msg,
        onDismiss = onDismiss
    )
}


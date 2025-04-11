package com.examples.digisocial.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

const val TAG = "AppTag"

fun printError(e: Exception?) = e?.apply {
    Log.e(TAG, "$message")
}

fun showToastMessage(
    context: Context,
    resourceId: Int
) = makeText(context, context.resources.getString(resourceId), LENGTH_LONG).show()
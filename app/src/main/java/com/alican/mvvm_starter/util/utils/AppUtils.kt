
package com.alican.mvvm_starter.util.utils

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import com.alican.mvvm_starter.R
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.murgupluoglu.request.RESPONSE
import com.tapadoo.alerter.Alerter
import com.alican.mvvm_starter.util.utils.SPPARAM.TOKEN
import com.alican.mvvm_starter.base.BaseResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun <T> addDataModelSharedPref(key: String, data: T?) {
    val gson = Gson()
    if (data == null)
        throw RuntimeException("Data can not be null")

    val json = gson.toJson(data)

    if (json != null) {
        SPUtils.getInstance().apply {
            put(key, json)
        }
    } else
        throw RuntimeException("Data can not be null")
}

@Nullable
fun <T> retrieveDataModelSharedPref(key: String, clazz: Class<T>): T? {
    val gson = Gson()
    if (SPUtils.getInstance().contains(key)) {

        val json = SPUtils.getInstance().getString(key)

        return gson.fromJson(json, clazz)

    } else {
        return null
    }
}

fun showDialogTop(message: String?, title: String?, activity: Activity) {

    val alerter: Alerter = Alerter.create(activity)
        .setTitle(title)
        .setText(message)
        .setDuration(2000)
        .setBackgroundColorRes(R.color.black)
    alerter.show()
}

fun Context.showToastMessage(errorMessage: String?, type: ToastType) {

    var textColor = -1
    var background = -1
    var startIcon = -1
    when (type) {
        ToastType.SUCCESS -> {
            textColor = R.color.black
            background = R.mipmap.ic_launcher
            startIcon = R.mipmap.ic_launcher

        }

        ToastType.WARNING -> {
            textColor = R.color.black
            background = R.mipmap.ic_launcher
            startIcon = R.mipmap.ic_launcher
        }

        ToastType.ERROR -> {
            textColor = R.color.black
            background = R.mipmap.ic_launcher
            startIcon = R.mipmap.ic_launcher
        }
    }
    val toast = Toast(this)
    val inflate = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val v = inflate.inflate(R.layout.layout_custom_toast, null)
    val toastText = v.findViewById(R.id.toastTextView) as TextView
    val container = v.findViewById(R.id.toastContainer) as LinearLayout
    val toastImage = v.findViewById(R.id.toastImageView) as ImageView
    container.background = ContextCompat.getDrawable(this, background)
    toastText.text = errorMessage
    toastText.txtColor(textColor)
    toastImage.setImageResource(startIcon)
    toast.setGravity(Gravity.BOTTOM, 0, 220)
    toast.view = v
    toast.show()
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun <T> RESPONSE<T>.handleError(
    activity: FragmentActivity,
    doFunction: () -> Unit = {},
) {

    if (this.errorCode != -1) {
        LogUtils.e(this.errorCode, this.errorMessage)
        this.errorBody
    }

    when (this.errorCode) {
        401 -> {

            SPUtils.getInstance().remove(TOKEN)
      //      activity.startActivity(Intent(activity, AuthenticationActivity::class.java))
            activity.finishAffinity()

            return
        }
        404, 500, 503 -> {
            var error = ""
            try {
                this.errorBody?.let {
                    error = "activity.getString(R.string.general_error_text)"
                }
            } catch (e: Exception) {
                LogUtils.e(e)
            }
            activity.showToastMessage(error, ToastType.ERROR)
        }
        else -> {
            this.responseObject?.let {
                when ((it as BaseResponse<*>).error.code) {
                    400 -> {
                        showDialogTop(
                            "hata",
                            it.error.message,
                            activity
                        )
                    }
                    500 -> {
                        activity.showToastMessage(
                            it.error.message,
                            ToastType.ERROR
                        )
                    }
                    0 -> {
                        doFunction.invoke()
                    }
                }
            }

        }

    }
}

fun isEmailValid(email: CharSequence?): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String?): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.!])(?=\\S+$).{8,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(password)
    return matcher.matches()
}

fun hidePassword(layout: TextInputLayout, editText: EditText) {
    layout.setEndIconDrawable(R.drawable.ic_launcher_foreground)
    editText.transformationMethod =
        PasswordTransformationMethod.getInstance()

}

fun showPassword(layout: TextInputLayout, editText: EditText) {
    layout.setEndIconDrawable(R.drawable.ic_launcher_background)
    editText.transformationMethod =
        HideReturnsTransformationMethod.getInstance()
}

fun enableButton(button: Button) {
    button.isEnabled = true
    button.background.alpha = 255
}

fun disableButton(button: Button) {
    button.isEnabled = false
    button.background.alpha = 102
}


fun handleEditTextError(
    layout: TextInputLayout,
    error: String,
    editText: EditText,
    length: Int,
    context: Context,
) {
    editText.addTextChangedListener {
        if (it.toString().trim().length < length) {
            layout.isErrorEnabled = true
            layout.boxStrokeErrorColor =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
            layout.error = error
        } else {
            layout.isErrorEnabled = false
            layout.boxStrokeColor =
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
        }
    }

    if (editText.text.length < length && !editText.text.isNullOrEmpty()) {
        layout.isErrorEnabled = true
        layout.boxStrokeErrorColor =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )
        layout.error = error
    }
}

fun handleEditTextErrorAfterButtonClick(
    layout: TextInputLayout,
    error: String,
    editText: EditText,
    context: Context,
) {
    if (editText.text.isNullOrEmpty()) {
        layout.isErrorEnabled = true
        layout.boxStrokeErrorColor =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )
        layout.error = error
    } else {
        layout.isErrorEnabled = false
        layout.boxStrokeColor =
            ContextCompat.getColor(
                context,
                R.color.white
            )
    }
}

fun showPasswordError(layout: TextInputLayout, error: String, context: Context) {
    layout.setErrorIconTintList(
        ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.red)
        )
    )
    layout.setErrorTextColor(
        ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.white)
        )
    )
    layout.errorIconDrawable = null
    layout.error =
        Html.fromHtml(error)
    layout.boxStrokeErrorColor =
        ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.red
            )
        )
}

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.hideKeyboard(view: View) {
    val inputMethodManager: InputMethodManager? =
        ContextCompat.getSystemService(this, InputMethodManager::class.java)
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hidePasswordError(layout: TextInputLayout, context: Context, error: String) {
    layout.setErrorIconTintList(
        ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.white)
        )
    )
    layout.setErrorTextColor(
        ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.white)
        )
    )
    layout.errorIconDrawable = null
    layout.error =
        Html.fromHtml(error)
    layout.boxStrokeErrorColor =
        ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
}

fun prepareFilePart(key: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        key,
        file.name,
        file.asRequestBody("file/*".toMediaTypeOrNull())
    )
}

fun prepareStringPart(key: String, value: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(key, value)
}


fun copyFileToInternalStorage(
    uri: Uri,
    newDirName: String,
    activity: Activity
): String? {
    val returnCursor: Cursor = activity.contentResolver.query(
        uri, arrayOf(
            OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        ), null, null, null
    )!!


    /*
 * Get the column indexes of the data in the Cursor,
 *     * move to the first row in the Cursor, get the data,
 *     * and display it.
 * */
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    val size = returnCursor.getLong(sizeIndex).toString()
    val output: File = if (newDirName != "") {
        val dir = File(activity.filesDir.toString() + "/" + newDirName)
        if (!dir.exists()) {
            dir.mkdir()
        }
        File(activity.filesDir.toString() + "/" + newDirName + "/" + name)
    } else {
        File(activity.filesDir.toString() + "/" + name)
    }
    try {
        val inputStream: InputStream = activity.contentResolver.openInputStream(uri)!!
        val outputStream = FileOutputStream(output)
        var read = 0
        val bufferSize = 1024
        val buffers = ByteArray(bufferSize)
        while (inputStream.read(buffers).also { read = it } != -1) {
            outputStream.write(buffers, 0, read)
        }
        inputStream.close()
        outputStream.close()
    } catch (e: java.lang.Exception) {
        Log.e("Exception", e.message!!)
    }
    return output.path
}

fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}


@Throws(Exception::class)
fun getFileFromUri(context: Context, uri: Uri): File? {
    var path: String? = null

    // DocumentProvider
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        if (DocumentsContract.isDocumentUri(context, uri)) { // TODO: 2015. 11. 17. KITKAT

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    path = Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                path = getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) { // MediaProvider
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                path = getDataColumn(context, contentUri, selection, selectionArgs)
            } else if (isGoogleDrive(uri)) { // Google Drive
                val TAG = "isGoogleDrive"
                path = TAG
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(";").toTypedArray()
                val acc = split[0]
                val doc = split[1]

                /*
                 * @details google drive document data. - acc , docId.
                 * */return saveFileIntoExternalStorageByUri(context, uri)
            } // MediaStore (and general)
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            path = getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            path = uri.path
        }
        File(path)
    } else {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        File(cursor!!.getString(cursor.getColumnIndex("_data")))
    }
}

fun isGoogleDrive(uri: Uri): Boolean {
    return uri.authority.equals("com.google.android.apps.docs.storage", ignoreCase = true)
}

fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = MediaStore.Images.Media.DATA
    val projection = arrayOf(
        column
    )
    try {
        cursor = context.contentResolver.query(
            uri!!, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(column_index)
        }
    } finally {
        if (cursor != null) cursor.close()
    }
    return null
}

fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}


fun makeEmptyFileIntoExternalStorageWithTitle(title: String?): File {
    val root = Environment.getExternalStorageDirectory().absolutePath
    return File(root, title)
}


fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor!!.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}


@Throws(Exception::class)
fun saveBitmapFileIntoExternalStorageWithTitle(bitmap: Bitmap, title: String) {
    val fileOutputStream = FileOutputStream(
        makeEmptyFileIntoExternalStorageWithTitle(
            "$title.png"
        )
    )
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.close()
}


@Throws(Exception::class)
fun saveFileIntoExternalStorageByUri(context: Context, uri: Uri): File? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val originalSize = inputStream!!.available()
    var bis: BufferedInputStream? = null
    var bos: BufferedOutputStream? = null
    val fileName = getFileName(context, uri)
    val file = makeEmptyFileIntoExternalStorageWithTitle(fileName)
    bis = BufferedInputStream(inputStream)
    bos = BufferedOutputStream(
        FileOutputStream(
            file, false
        )
    )
    val buf = ByteArray(originalSize)
    bis.read(buf)
    do {
        bos.write(buf)
    } while (bis.read(buf) !== -1)
    bos.flush()
    bos.close()
    bis.close()
    return file
}

fun isUserLoggedIn(): Boolean {
    return !SPUtils.getInstance().getString(SPPARAM.TOKEN, "").isNullOrEmpty()
}

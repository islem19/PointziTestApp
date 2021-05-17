package com.pointzi.library

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.pointzi.library.data.local.SharedPrefs
import com.pointzi.library.model.PointziModel
import com.pointzi.library.utils.TimeUtils
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.main_layout.view.*


class CustomPointzi(@NonNull private val activity: Activity) {
    // get the root view of the current activity
    private val rootView by lazy { activity.findViewById<ViewGroup>(android.R.id.content) }
    // our custom dialog instance
    private val dialog by lazy { CustomDialog(activity) }
    // our pointzi model
    private val model by lazy { PointziModel(defaultIcon, installedOn) }
    // our default icon url link
    private val defaultIcon by lazy { activity.getString(R.string.default_icon_link) }
    // installation date , fetched from local shared prefs
    private val installedOn by lazy {
        // if the date is null -> then get the current date and save it
        // in the shared prefs instance
        sharedPrefs.getInstalledOn() ?:
        TimeUtils.getDate().also {
            sharedPrefs.setInstalledOn(it)
        }
    }
    // init shared prefs instance
    private val sharedPrefs by lazy { SharedPrefs.also { SharedPrefs.init(activity) } }
    // instance of the current view inflated
    private lateinit var view : View

    fun setIcon(iconUrl : String) = apply { model.iconUrl = iconUrl }

    fun build() = apply {
        // inflate our main layout
        view = activity.layoutInflater.inflate(R.layout.main_layout, rootView,false)
        // set click listene of the floating action button
        setupFabClickListener()
        // add the current inflated view to the root view at position 0
        rootView.addView(view,0)
    }

    private fun setupFabClickListener() {
        view.fab.setOnClickListener {
            // bind the data model to ui
            bindModelToDialogUi(dialog,model)
            // show our dialog
            dialog.show()
        }
    }

    private fun bindModelToDialogUi(customDialog: CustomDialog, model: PointziModel) {
        // load the icon using Glide on the image view
        Glide.with(customDialog.context)
            .load(model.iconUrl)
            .into(customDialog.iv_icon)
        // bind the installation date to the text view
        customDialog.tv_installed_on.text = String.format(
                this.activity.getString(R.string.installed_on),
                model.installedOn
        )
    }

    /**
     * our custom dialog that holds our model
     */
    private inner class CustomDialog(context : Context) : Dialog(context){
        init {
            // set the view to our dialog layout
            setContentView(R.layout.dialog_layout)
            // set the current window dim amount to 50%
            window!!.setDimAmount(0.5f)
            // set the background of the dialog root view to transparent
            // because dialog layout is put within root view which hides the corners in custom layout
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // set the enter and exit dialog animation
            window!!.attributes.windowAnimations = R.style.DialogAnimation
        }
    }

}
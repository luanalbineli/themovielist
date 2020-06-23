package com.themovielist.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.themovielist.R
import com.themovielist.extension.getScreenSize
import kotlinx.android.synthetic.main.fullscreen_list_dialog.*
import java.security.InvalidParameterException

abstract class FullscreenListDialog<TModel : Parcelable> : BottomSheetDialogFragment() {
    protected lateinit var mList: ArrayList<TModel>

    override fun getTheme(): Int = R.style.AppTheme_RoundBottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireActivity(), theme) {
            override fun onBackPressed() {
                dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialogArguments = arguments
        if (dialogArguments == null || !dialogArguments.containsKey(LIST_BUNDLE_KEY)) {
            throw InvalidParameterException(LIST_BUNDLE_KEY)
        }

        mList = dialogArguments.getParcelableArrayList<TModel>(LIST_BUNDLE_KEY) as ArrayList<TModel>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fullscreen_list_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFullscreenDialog()
    }

    private fun setupFullscreenDialog() {
        dialog?.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams

            val windowHeight: Int = requireContext().getScreenSize().heightPixels
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = windowHeight
        }
    }

    protected fun setTitle(@StringRes titleResId: Int) {
        text_fullscreen_list_dialog_title.setText(titleResId)
    }

    companion object {
        private const val LIST_BUNDLE_KEY = "list"

        fun <TFragmentDialog : FullscreenListDialog<TModel>, TModel : Parcelable> createNewInstance(
            clazz: Class<TFragmentDialog>,
            items: List<TModel>
        ): TFragmentDialog {
            val baseFullscreenDialogWithList = clazz.newInstance()
            baseFullscreenDialogWithList.arguments = Bundle().also { bundle ->
                bundle.putParcelableArrayList(LIST_BUNDLE_KEY, ArrayList(items))
            }

            return baseFullscreenDialogWithList
        }
    }
}

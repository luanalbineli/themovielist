package com.themovielist.widget

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.themovielist.R
import androidx.fragment.app.DialogFragment

abstract class BaseFullscreenListDialog<TModel: Parcelable>: DialogFragment() {
    protected lateinit var mList: ArrayList<TModel>

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mList = it.getParcelableArrayList<TModel>(LIST_BUNDLE_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fullscreen_list_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* context?.let {
            configureToolbarBackButton(it, toolbarMovieReviewDialog) {
                dismiss()
            }
        }*/
    }

    companion object {
        private const val LIST_BUNDLE_KEY = "list"

        fun <TFragmentDialog : BaseFullscreenListDialog<TModel>, TModel : Parcelable> createNewInstance(clazz: Class<TFragmentDialog>, items: List<TModel>): TFragmentDialog {
            val baseFullscreenDialogWithList = clazz.newInstance()
            val bundle = Bundle()
            bundle.putParcelableArrayList(LIST_BUNDLE_KEY, ArrayList(items))
            baseFullscreenDialogWithList.arguments = bundle
            baseFullscreenDialogWithList.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_FullscreenDialog)

            return baseFullscreenDialogWithList
        }
    }
}
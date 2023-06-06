
package com.alican.mvvm_starter.util.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.alican.mvvm_starter.BR
import com.alican.mvvm_starter.R
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.alican.mvvm_starter.data.model.EditProfileItem
import com.alican.mvvm_starter.databinding.ItemBottomSheetLayoutBinding


class BottomSheetDialog : BottomSheetDialogFragment() {

    var adapter: LastAdapter? = null
    var sheetItem = arrayListOf<Any>()

    private lateinit var recyclerView: RecyclerView
    private var titleText: String = ""
    private lateinit var cancelButton : Button

    var listener: BottomSheetListener? = null

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.layout_bottom_sheet, container, false)
    }

    companion object {
        val instance: BottomSheetDialog
            get() = BottomSheetDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_company_select)
        cancelButton = view.findViewById(R.id.btn_cancel)
        initAdapter()

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        adapter = LastAdapter(sheetItem,BR.data)
            .map<EditProfileItem>(
                Type<ItemBottomSheetLayoutBinding>(R.layout.item_bottom_sheet_layout).onBind { holder ->
                    val data = holder.binding.data
                    data?.let {
                        if (data.name == "Kaldır" || data.name == "Projeyi Sil") {
                            holder.binding.itemNameTextView.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.delete_list_red
                                )
                            )
                        }
                        holder.binding.itemNameTextView.text = data.name
                    }
                    holder.binding.container.setOnClickListener {
                        listener?.itemClick(
                            holder.binding.data!!,
                            holder.adapterPosition
                        )
                        dismiss()
                    }
                }
            ).into(recyclerView)
    }

    /** First method init title and bottom sheet type **/
    fun setupSheet(title: String) {
        titleText = title
    }

    /** BottomSheet RecList item listener **/
    interface BottomSheetListener {
        fun itemClick(item: Any, layoutPosition: Int)
    }

    fun updateList(newList: ArrayList<Any>) {
        sheetItem.clear()
        newList.forEach {
            sheetItem.add(it)
        }
        adapter?.notifyDataSetChanged()

    }
}
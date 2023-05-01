package com.trdz.infinilist.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trdz.infinilist.databinding.ElementLineBinding

class WindowMainListRecycle(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var list: List<String> = emptyList()

	fun listControl(newList: List<String>, first: Int, count: Int) {
		this.list = this.list + newList
		notifyItemRangeChanged(first, count)
	}

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<String>) {
		this.list = newList
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = ElementLineBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return Element(view.root)
		}

	override fun onBindViewHolder(
		holder: RecyclerView.ViewHolder,
		position: Int,
		payloads: MutableList<Any>,
	) {
		if (payloads.isEmpty()) {
			super.onBindViewHolder(holder, position, payloads)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as ListElement).myBind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	abstract inner class ListElement(view: View): RecyclerView.ViewHolder(view) {
		abstract fun myBind(data: String)
	}

	inner class Element(view: View): ListElement(view) {

		override fun myBind(data: String) {
			(ElementLineBinding.bind(itemView)).apply { lName.text = data }
		}
	}

}
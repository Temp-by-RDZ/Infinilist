package com.trdz.infinilist.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class WindowMainScroll(
	private val executor: OnScrollExecutor,
	private val layoutManager: RecyclerView.LayoutManager
	) : RecyclerView.OnScrollListener() {

	private var threshold = 5
	private var isLoading: Boolean = false
	private var itemsLastVisible: Int = 0
	private var itemsTotal:Int = 0
	//private var mLayoutManager: RecyclerView.LayoutManager

	fun setup(limits:Int=threshold, status: Boolean=isLoading) {
		threshold = limits
		isLoading = status
		when (layoutManager) {
			is LinearLayoutManager -> {}
			is GridLayoutManager -> { threshold *= layoutManager.spanCount}
			is StaggeredGridLayoutManager -> { threshold *= layoutManager.spanCount }
		}
	}

	fun setLoaded() {
		isLoading = false
	}

	fun getLoaded(): Boolean {
		return isLoading
	}

	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)

		if (dy <= 0) return

		itemsTotal = layoutManager.itemCount

		when (layoutManager) {
			is StaggeredGridLayoutManager -> {
				val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
				itemsLastVisible = getLastVisibleItem(lastVisibleItemPositions)
			}
			is GridLayoutManager -> {
				itemsLastVisible = layoutManager.findLastVisibleItemPosition()
			}
			is LinearLayoutManager -> {
				itemsLastVisible = layoutManager.findLastVisibleItemPosition()
			}
		}

		if (!isLoading && itemsTotal <= itemsLastVisible + threshold) {
			executor.loadMore()
			isLoading = true
		}

	}

	private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
		var maxSize = 0
		for (i in lastVisibleItemPositions.indices) {
			if (i == 0) {
				maxSize = lastVisibleItemPositions[i]
			} else if (lastVisibleItemPositions[i] > maxSize) {
				maxSize = lastVisibleItemPositions[i]
			}
		}
		return maxSize
	}
}
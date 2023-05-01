package com.trdz.infinilist.view

import android.os.Bundle
import android.text.style.*
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.trdz.infinilist.R
import com.trdz.infinilist.databinding.FragmentWindowMainBinding
import com.trdz.infinilist.utility.KEY_FINSTANCE
import com.trdz.infinilist.view_model.MainViewModel
import com.trdz.infinilist.view_model.StatusProcess
import com.trdz.infinilist.view_model.ViewModelFactories
import org.koin.android.ext.android.inject


class WindowMain: Fragment(), OnScrollExecutor {

	//region Injected
	private val navigation: Navigation by inject()
	private val factory: ViewModelFactories by inject()

	private val viewModel: MainViewModel by viewModels {
		factory
	}

	//endregion

	//region Elements
	private val adapter = WindowMainListRecycle()
	private lateinit var scroler: WindowMainScroll
	private var _binding: FragmentWindowMainBinding? = null
	private val binding get() = _binding!!
	var isFirst = false

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			isFirst = it.getBoolean(KEY_FINSTANCE)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowMainBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		prepInitialize()
		initialize()
	}

	private fun prepInitialize() {
		setViewModel()
	}

	private fun setViewModel() {
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
	}

	//endregion

	//region Main functional

	private fun initialize() {
		binds()
		viewModel.initialize()
		}

	private fun binds() = with(binding) {
		recyclerView.adapter = adapter
		scroler = WindowMainScroll(this@WindowMain, recyclerView.layoutManager!!)
		recyclerView.addOnScrollListener(scroler.apply { setup(100) })
		}

	override fun loadMore() {
		viewModel.more()
	}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {}
			is StatusProcess.Error -> {}
			is StatusProcess.Success -> {
				val list = material.data.data
				binding.naming.text = (getString(R.string.list_title)+"("+(list.size+material.data.firstId).toString()+ ")")
				adapter.listControl(list,material.data.firstId,list.size)
				scroler.setLoaded()
			}
		}
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance(first_instance: Boolean) =
			WindowMain().apply {
				arguments = Bundle().apply {
					putBoolean(KEY_FINSTANCE, first_instance)
				}
			}
	}

}
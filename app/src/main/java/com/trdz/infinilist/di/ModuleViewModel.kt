package com.trdz.infinilist.di

import com.trdz.infinilist.model.Repository
import com.trdz.infinilist.model.RepositoryExecutor
import com.trdz.infinilist.view_model.SingleLiveData
import com.trdz.infinilist.view_model.StatusProcess
import com.trdz.infinilist.view_model.ViewModelFactories
import org.koin.dsl.module

val moduleViewModel = module {
	single<Repository>() {
		RepositoryExecutor()
	}
	factory<SingleLiveData<StatusProcess>>() { SingleLiveData() }
	single<ViewModelFactories>() {
		ViewModelFactories(
			dataLive = get(),
			repository = get(),
		)
	}
}



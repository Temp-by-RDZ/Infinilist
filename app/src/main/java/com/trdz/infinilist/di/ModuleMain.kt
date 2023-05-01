package com.trdz.infinilist.di

import com.trdz.infinilist.R
import com.trdz.infinilist.view.Navigation
import org.koin.dsl.module

val moduleMain = module {
	single<Navigation>() { Navigation(R.id.container_fragment_base) }
}



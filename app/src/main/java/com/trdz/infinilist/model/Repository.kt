package com.trdz.infinilist.model

import io.reactivex.rxjava3.core.Single

/** Интерфейс для основного репозитория */
interface Repository {
	fun connection(requariedId: Int, range: Int) : Single<RequestResult>
}
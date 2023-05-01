package com.trdz.infinilist.model

/** Интерфейс для Источников Данных */
interface ExternalSource {
	fun load(requariedId: Int, range: Int): RequestResult
}
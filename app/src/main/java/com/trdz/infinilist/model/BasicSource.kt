package com.trdz.infinilist.model

/** Получение данных*/
class BasicSource: ExternalSource {

	override fun load(requariedId: Int, range: Int): RequestResult {
		val result: MutableList<String> = mutableListOf()
		((requariedId+1)..(requariedId+range)).forEach { index -> result.add("Реузльтат $index") }
		return RequestResult(200, requariedId, result)
	}

}

package com.trdz.infinilist.view_model

import com.trdz.infinilist.model.RequestResult

/** Ожидаемые действия VM на обращения */
interface ServerResponse {
	fun success(data: RequestResult)
	fun fail(code: Int, throwable: Throwable?)
}
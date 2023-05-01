package com.trdz.infinilist.view_model

import com.trdz.infinilist.model.RequestResult

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Success(val data: RequestResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}
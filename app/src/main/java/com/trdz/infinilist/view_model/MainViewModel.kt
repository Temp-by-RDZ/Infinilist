package com.trdz.infinilist.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.infinilist.model.Repository
import com.trdz.infinilist.model.RepositoryExecutor
import com.trdz.infinilist.model.RequestResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/** Главная VM для сегмента Picture */
class MainViewModel(
	private val dataLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val repository: Repository = RepositoryExecutor(),
): ViewModel(), ServerResponse {

	private var lastIndex = 0

	fun getData(): LiveData<StatusProcess> = dataLive

	/** Подготовка запроса*/
	fun initialize() {
		request(100)
	}

	fun more() {
		request(50)
	}

	/** Выполнение запроса */
	private fun request(range: Int) {
		repository.connection(lastIndex, range)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{
					if (it.code in 200..299) {
						success(it)
					}
					else {
						fail(it.code,null)
					}
				},
				{ exception -> fail(-4, exception) })
	}

	/** Реакция MV на успех запроса */
	override fun success(data: RequestResult) {
		Log.d("@@@", "Mod - get success answer")
		lastIndex += data.data.size
		dataLive.postValue(StatusProcess.Success(data))
	}

	/** Реакция MV на ошибку запроса */
	override fun fail(code: Int, throwable: Throwable?) {
		Log.d("@@@", "Mod - request failed $code")
		val message = throwable ?: Throwable("Unspecified Error")
		dataLive.postValue(StatusProcess.Error(code, message))
	}
}
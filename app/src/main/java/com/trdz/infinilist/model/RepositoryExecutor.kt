package com.trdz.infinilist.model

import android.util.Log
import io.reactivex.rxjava3.core.Single

class RepositoryExecutor: Repository {
	/** Отправка запроса NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
	override fun connection(requariedId: Int, range: Int): Single<RequestResult> {
		Log.d("@@@", "Rep - start connection")
		val internalSource: ExternalSource = BasicSource()
		return Single.create{
			val data = internalSource.load(requariedId, range)
			it.onSuccess(data)
		}
	}

}

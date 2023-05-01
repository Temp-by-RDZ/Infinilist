package com.trdz.infinilist.model

/** Ответ от Запроса в NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
data class RequestResult(
	val code: Int, 						//Код ответа на запрос
	val firstId: Int,
	val data: MutableList<String>, 		//Содержимое ответа
)

package com.alican.mvvm_starter.data.model

import com.squareup.moshi.Json

data class MovieCreditResponse(

	@Json(name="cast")
	val cast: List<CastItem?>? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="crew")
	val crew: List<CrewItem?>? = null
)

data class CastItem(

	@Json(name="cast_id")
	val cast_id: Int? = null,

	@Json(name="character")
	val character: String? = null,

	@Json(name="gender")
	val gender: Int? = null,

	@Json(name="credit_id")
	val credit_id: String? = null,

	@Json(name="known_for_department")
	val known_for_department: String? = null,

	@Json(name="original_name")
	val original_name: String? = null,

	@Json(name="popularity")
	val popularity: Double? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="profile_path")
	val profile_path: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="adult")
	val adult: Boolean? = null,

	@Json(name="order")
	val order: Int? = null
)

data class CrewItem(

	@Json(name="gender")
	val gender: Int? = null,

	@Json(name="credit_id")
	val credit_id: String? = null,

	@Json(name="known_for_department")
	val known_for_department: String? = null,

	@Json(name="original_name")
	val original_name: String? = null,

	@Json(name="popularity")
	val popularity: Any? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="profile_path")
	val profile_path: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="adult")
	val adult: Boolean? = null,

	@Json(name="department")
	val department: String? = null,

	@Json(name="job")
	val job: String? = null
)

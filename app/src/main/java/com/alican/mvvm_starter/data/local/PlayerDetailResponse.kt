package com.alican.mvvm_starter.data.local

import com.squareup.moshi.Json

data class PlayerDetailResponse(

	@Json(name="data")
	val data: List<DataItem?>? = null,

	@Json(name="meta")
	val meta: Meta? = null
)

data class Team(

	@Json(name="division")
	val division: String? = null,

	@Json(name="conference")
	val conference: String? = null,

	@Json(name="full_name")
	val fullName: String? = null,

	@Json(name="city")
	val city: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="abbreviation")
	val abbreviation: String? = null
)

data class DataItem(

	@Json(name="weight_pounds")
	val weightPounds: Int? = null,

	@Json(name="height_feet")
	val heightFeet: Int? = null,

	@Json(name="height_inches")
	val heightInches: Int? = null,

	@Json(name="last_name")
	val lastName: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="position")
	val position: String? = null,

	@Json(name="team")
	val team: Team? = null,

	@Json(name="first_name")
	val firstName: String? = null
)

data class Meta(

	@Json(name="next_page")
	val nextPage: Any? = null,

	@Json(name="per_page")
	val perPage: Int? = null,

	@Json(name="total_count")
	val totalCount: Int? = null,

	@Json(name="total_pages")
	val totalPages: Int? = null,

	@Json(name="current_page")
	val currentPage: Int? = null
)

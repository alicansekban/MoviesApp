package com.alican.mvvm_starter.testutil

import com.alican.mvvm_starter.data.model.CastItem
import com.alican.mvvm_starter.data.model.CrewItem
import com.alican.mvvm_starter.data.model.MovieCreditResponse

object MockData {

    val mockMovieCreditResponse = MovieCreditResponse(
        cast = listOf(
            CastItem(
                cast_id = 1,
                character = "Character 1",
                gender = 0,
                credit_id = "credit_id_1",
                known_for_department = "Department 1",
                original_name = "Original Name 1",
                popularity = 1.23,
                name = "Name 1",
                profile_path = "profile_path_1",
                id = 1001,
                adult = false,
                order = 0
            ),
            CastItem(
                cast_id = 2,
                character = "Character 2",
                gender = 1,
                credit_id = "credit_id_2",
                known_for_department = "Department 2",
                original_name = "Original Name 2",
                popularity = 2.34,
                name = "Name 2",
                profile_path = "profile_path_2",
                id = 1002,
                adult = true,
                order = 1
            )
        ),
        id = 12345,
        crew = listOf(
            CrewItem(
                gender = 0,
                credit_id = "credit_id_3",
                known_for_department = "Department 3",
                original_name = "Original Name 3",
                popularity = null,
                name = "Name 3",
                profile_path = null,
                id = 2001,
                adult = false,
                department = "Department 3",
                job = "Job 1"
            ),
            CrewItem(
                gender = 1,
                credit_id = "credit_id_4",
                known_for_department = "Department 4",
                original_name = "Original Name 4",
                popularity = 3.45,
                name = "Name 4",
                profile_path = "profile_path_4",
                id = 2002,
                adult = true,
                department = "Department 4",
                job = "Job 2"
            )
        )
    )

}
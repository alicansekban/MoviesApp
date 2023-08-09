package com.alican.mvvm_starter.domain

import com.alican.mvvm_starter.data.repository.FavoriteMoviesRepository
import com.alican.mvvm_starter.domain.usecase.FavoriteMoviesUseCase
import com.alican.mvvm_starter.util.ResultWrapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import java.io.IOException

class FavoritesUseCaseTest {

    private val repository  = mockk<FavoriteMoviesRepository>()
    private val useCase = FavoriteMoviesUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun test_getFavMoviesUseCase_Fail() = runBlockingTest {
        val errorResponse = IOException("Failed to connect to network!")

        coEvery { repository.getFavoriteMovies("") } returns ResultWrapper.GenericError(null,null)

        val favorites = useCase.invoke()

    }

    @Test
    fun test_getFavMoviesUseCase_Success() = runBlockingTest {
        val errorResponse = IOException("Failed to connect to network!")

        coEvery { repository.getFavoriteMovies("") } returns ResultWrapper.GenericError(null,null)

        val favorites = useCase.invoke()
    }
}
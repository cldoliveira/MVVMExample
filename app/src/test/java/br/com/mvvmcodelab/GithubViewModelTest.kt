package br.com.mvvmcodelab

import android.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mvvmcodelab.di.GithubApi
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.repository.GithubRepository
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class GithubViewModelTest {

    @get:Rule
    var jumpSchedulerRule = JumpSchedulerRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: GithubApi

    private lateinit var viewModel: GithubViewModel
    private lateinit var repository: GithubRepository
    private lateinit var users: List<User>

    @Before
    fun setUp() {
        repository = GithubRepository(api)
        viewModel = GithubViewModel(repository)

        val user1 = User(login = "abc", id = 100, url = "teste.com.br", avatar_url = "teste.com.br")
        val user2 = User(login = "cde", id = 101, url = "teste.com.br", avatar_url = "teste.com.br")
        val user3 = User(login = "def", id = 102, url = "teste.com.br", avatar_url = "teste.com.br")

        users = listOf(user1, user2, user3)
    }

    @Test
    fun shouldShowCorrectListOfUsers() {
        `when`(api.getUsers()).thenReturn(Single.just(users))

        val testObserver = repository.getUsers().test()

        viewModel.fetchUsers()
        viewModel.getUsers().observeForever { response ->
            assertNotNull(response)
            assertEquals(response!!.size, users.size)
        }
        testObserver.assertComplete().assertValue(users)
    }

    @Test
    fun shouldReturnCorrectListWhenFilter() {
        `when`(api.getUsers()).thenReturn(Single.just(users))

        val testObserver = repository.getUsers().test()

        viewModel.fetchUsers()
        viewModel.filterUsers("abc")
        viewModel.getUsersFiltered().observeForever {response ->
            assertNotNull(response)
            assertEquals(response!!.size, 1)
            assertEquals(response[0].id, 100)
        }
        testObserver.assertComplete().assertValue(users)
    }

    @Test
    fun shouldSendThrowableWhenApiReturnsError() {
        val throwable = Throwable("an unexpected error has happened")
        `when`(api.getUsers()).thenReturn(Single.error(throwable))

        val testObserver = repository.getUsers().test()

        viewModel.fetchUsers()
        viewModel.getError().observeForever {response ->
            assertNotNull(response)
        }
        testObserver.assertError(throwable)
    }
}
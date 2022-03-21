package com.vip.cleantemplate.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vip.cleantemplate.common.Constants.STARTING_PAGE_INDEX
import com.vip.cleantemplate.data.remote.ApiService
import com.vip.cleantemplate.domain.model.Player
import retrofit2.HttpException
import java.io.IOException

class PlayersPagingSource(private val apiService: ApiService) : PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        try {
            val pageIndex = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getPlayers(params.loadSize, pageIndex)
            val data = response.data ?: emptyList()

            val prevKey = if (pageIndex == 1) null else pageIndex - 1

            return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = pageIndex.plus(1)
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
       // return state.anchorPosition
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }
}
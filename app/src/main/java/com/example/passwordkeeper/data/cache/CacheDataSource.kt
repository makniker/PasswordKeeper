package com.example.passwordkeeper.data.cache

class CacheDataSource(private val dao: PageDao) {
    suspend fun add(title: String, url: String) {
        dao.addPage(PageCache(System.nanoTime(), title, url, ""))
    }

    suspend fun saveImageUrl(item: PageCache) {
        dao.saveImageUrl(item)
    }

    suspend fun getAll(): List<PageCache> = dao.getAll()

    suspend fun checkIfExist(title: String, url: String): Boolean = dao.checkIfExist(title, url) < 1

    suspend fun getUrlAndNameById(pageId: Long): Pair<String, String> {
        val p = dao.getPageById(pageId)
        return p.title to p.url
    }

    suspend fun getTitleById(pageId: Long): String {
        val p = dao.getPageById(pageId)
        return p.title
    }
}
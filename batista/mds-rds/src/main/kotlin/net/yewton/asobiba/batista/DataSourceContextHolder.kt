package net.yewton.asobiba.batista

object DataSourceContextHolder {
    private val context = ThreadLocal<MyDataSource>()

    fun set(ds: MyDataSource) {
        context.set(ds)
    }

    fun get(): MyDataSource? = context.get()

    fun clear() {
        context.remove()
    }
}

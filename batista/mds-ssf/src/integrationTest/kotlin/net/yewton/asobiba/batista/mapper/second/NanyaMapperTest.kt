package net.yewton.asobiba.batista.mapper.second

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.shouldNot
import net.yewton.asobiba.batista.DataSourcesConfig
import net.yewton.asobiba.batista.MyBatisConfig
import net.yewton.asobiba.batista.SecondTransactional
import net.yewton.asobiba.batista.model.Nanya
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import

@SecondTransactional
@MybatisTest
@Import(DataSourcesConfig::class, MyBatisConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NanyaMapperTest(val nanyaMapper: NanyaMapper) : DescribeSpec({
    describe("#findByName") {
        it("名前で探せる") {
            nanyaMapper.truncate()
            nanyaMapper.insert(Nanya.newInstance("なんや"))
            val actual = nanyaMapper.findByName("なんや")
            actual shouldNot beEmpty()
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}

package net.yewton.asobiba.batista.mapper.first

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.shouldNot
import net.yewton.asobiba.batista.DataSourcesConfig
import net.yewton.asobiba.batista.FirstTransactional
import net.yewton.asobiba.batista.MyBatisConfig
import net.yewton.asobiba.batista.model.Kanya
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@FirstTransactional // @MybatisTest が @Transactional を合成しているので宣言順が大事
@MybatisTest
@Import(DataSourcesConfig::class, MyBatisConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("ct")
class KanyaMapperTest(val kanyaMapper: KanyaMapper) : DescribeSpec({
    describe("#findByName") {
        it("名前で探せる") {
            kanyaMapper.truncate()
            kanyaMapper.insert(Kanya.newInstance("かんや"))
            val actual = kanyaMapper.findByName("かんや")
            actual shouldNot beEmpty()
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}

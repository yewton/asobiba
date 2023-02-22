package net.yewton.asobiba.batista.mapper

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.should
import io.kotest.matchers.string.startWith
import net.yewton.asobiba.batista.NKService
import net.yewton.asobiba.batista.mapper.first.KanyaMapper
import net.yewton.asobiba.batista.mapper.second.NanyaMapper
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Suppress("MagicNumber")
class NKServiceTest(val nkService: NKService, val nanyaMapper: NanyaMapper, val kanyaMapper: KanyaMapper) : StringSpec({

    beforeEach {
        nanyaMapper.truncate()
        kanyaMapper.truncate()
    }

    "正しいトランザクション指定すれば普通に終わる" {
        nkService.successfulNanya()
        nanyaMapper.all().shouldNotBeEmpty()

        nkService.successfulKanya()
        kanyaMapper.all().shouldNotBeEmpty()
    }

    "正しいトランザクションで例外出たらロールバックされる" {
        shouldThrowAny { nkService.throwingNanya() }
        nanyaMapper.all().shouldBeEmpty()

        shouldThrowAny { nkService.throwingKanya() }
        kanyaMapper.all().shouldBeEmpty()
    }

    "間違ったトランザクション指定で例外出たら当然ロールバックされない" {
        shouldThrowAny { nkService.wrongTxNanya() }
        nanyaMapper.all().shouldNotBeEmpty()

        shouldThrowAny { nkService.wrongTxKanya() }
        kanyaMapper.all().shouldNotBeEmpty()
    }

    "存在しないトランザクション指定でエラー" {
        val e1 = shouldThrowExactly<NoUniqueBeanDefinitionException> {
            nkService.ghostTxNanya()
        }
        val e2 = shouldThrowExactly<NoUniqueBeanDefinitionException> {
            nkService.ghostTxKanya()
        }
        "No qualifying bean of type 'org.springframework.transaction.TransactionManager' available:".let {
            e1.message should startWith(it)
            e2.message should startWith(it)
        }
    }
})

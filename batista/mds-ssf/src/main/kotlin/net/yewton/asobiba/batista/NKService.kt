package net.yewton.asobiba.batista

import net.yewton.asobiba.batista.mapper.first.KanyaMapper
import net.yewton.asobiba.batista.mapper.second.NanyaMapper
import net.yewton.asobiba.batista.model.Kanya
import net.yewton.asobiba.batista.model.Nanya
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Suppress("MagicNumber", "TooGenericExceptionThrown")
class NKService(val nanyaMapper: NanyaMapper, val kanyaMapper: KanyaMapper) {

    companion object {
        fun newNanya() = Nanya.newInstance(UUID.randomUUID().toString())
        fun newKanya() = Kanya.newInstance(UUID.randomUUID().toString())
    }

    @SecondTransactional
    fun successfulNanya() {
        nanyaMapper.insert(newNanya())
    }

    @FirstTransactional
    fun successfulKanya() {
        kanyaMapper.insert(newKanya())
    }

    @SecondTransactional
    fun throwingNanya() {
        nanyaMapper.insert(newNanya())
        throw RuntimeException("なんてこったい")
    }

    @FirstTransactional
    fun throwingKanya() {
        kanyaMapper.insert(newKanya())
        throw RuntimeException("どんなもんだい")
    }

    @FirstTransactional
    fun wrongTxNanya() {
        nanyaMapper.insert(newNanya())
        throw RuntimeException("これはけったい")
    }

    @SecondTransactional
    fun wrongTxKanya() {
        kanyaMapper.insert(newKanya())
        throw RuntimeException("やっちまったい")
    }

    @Transactional
    fun ghostTxNanya() {
        nanyaMapper.insert(newNanya())
    }

    @Transactional
    fun ghostTxKanya() {
        kanyaMapper.insert(newKanya())
    }
}

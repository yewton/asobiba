package net.yewton.asobiba.batista

import net.yewton.asobiba.batista.mapper.first.KanyaMapper
import net.yewton.asobiba.batista.mapper.second.NanyaMapper
import net.yewton.asobiba.batista.model.Kanya
import net.yewton.asobiba.batista.model.Nanya
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on

@RestController
class NanyaKanyaController(val nanyaMapper: NanyaMapper, val kanyaMapper: KanyaMapper) {

    @PostMapping("nanya")
    fun insertNanya(@RequestParam name: String): HttpEntity<Unit> {
        nanyaMapper.insert(Nanya.newInstance(name))
        val uri = MvcUriComponentsBuilder.fromMethodCall(on(this::class.java).findNanya(name))
            .build().encode().toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("nanya")
    fun findNanya(@RequestParam name: String) = nanyaMapper.findByName(name)

    @PostMapping("kanya")
    fun insertKanya(@RequestParam name: String): HttpEntity<Unit> {
        kanyaMapper.insert(Kanya.newInstance(name))
        val uri = MvcUriComponentsBuilder.fromMethodCall(on(this::class.java).findKanya(name))
            .build().encode().toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("kanya")
    fun findKanya(@RequestParam name: String) = kanyaMapper.findByName(name)
}

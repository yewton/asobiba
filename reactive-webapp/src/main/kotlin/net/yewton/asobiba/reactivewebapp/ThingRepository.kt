package net.yewton.asobiba.reactivewebapp

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface ThingRepository : CoroutineCrudRepository<Thing, UUID>

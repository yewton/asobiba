package net.yewton.asobiba.reactivewebapp

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table
data class Thing(@Id val id: UUID?, val name: String)

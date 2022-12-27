package net.yewton.asobiba.reactivewebapp

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

/**
 * @property id
 * @property name
 */
@Table
data class Thing(@Id val id: UUID?, val name: String)

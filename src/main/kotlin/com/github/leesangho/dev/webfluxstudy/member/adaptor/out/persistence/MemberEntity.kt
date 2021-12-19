package com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("member")
class MemberEntity {
    val id: UUID = UUID.randomUUID()
    val name: String = ""
}
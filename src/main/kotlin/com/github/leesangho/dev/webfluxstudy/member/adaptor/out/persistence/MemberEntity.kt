package com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence

import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("member")
data class MemberEntity(private val name: String) {
    @Id
    private var id: Long? = null

    fun fromThis(): Member {
        return Member(id, name)
    }
}
package com.github.leesangho.dev.webfluxstudy.member.application.service

import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberEntity
import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberR2dbcRepository
import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MemberService(val memberR2dbcRepository: MemberR2dbcRepository) {
    fun getMembers(pageable: Pageable, sort: Sort): Flux<Member> {
        return memberR2dbcRepository.findAll(pageable, sort)
            .map(MemberEntity::fromThis)
    }

    fun getMember(id: Long): Mono<Member> {
        return memberR2dbcRepository.findById(id)
            .map(MemberEntity::fromThis)
    }

    fun addMember(memberCreateCommand: MemberCreateCommand): Mono<Member> {
        return memberR2dbcRepository.save<MemberEntity?>(MemberEntity(memberCreateCommand.name))
            .map(MemberEntity::fromThis)
    }

    fun deleteMemberById(id: Long): Mono<Void> {
        return memberR2dbcRepository.deleteById(id)
    }
}
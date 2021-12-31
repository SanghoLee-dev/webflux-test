package com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux

interface MemberR2dbcRepository : ReactiveSortingRepository<MemberEntity, Long> {
    fun findAll(pageable: Pageable, sort: Sort): Flux<MemberEntity>
}
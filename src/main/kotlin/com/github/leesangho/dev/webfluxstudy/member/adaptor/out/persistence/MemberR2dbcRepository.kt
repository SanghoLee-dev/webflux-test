package com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface MemberR2dbcRepository : ReactiveCrudRepository<MemberEntity, Long>
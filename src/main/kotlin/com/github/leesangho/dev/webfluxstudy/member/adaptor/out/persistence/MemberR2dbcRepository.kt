package com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface MemberR2dbcRepository : ReactiveCrudRepository<MemberEntity, UUID> {
}
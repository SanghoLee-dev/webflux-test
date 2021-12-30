package com.github.leesangho.dev.webfluxstudy.member.adaptor.`in`.web

import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import com.github.leesangho.dev.webfluxstudy.member.application.service.MemberService
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.util.*

@Component
@RequestMapping("/members")
class MemberHandler(val memberService: MemberService) {

    @GetMapping("/{id}")
    fun getMember(@PathVariable id: Long): Mono<ResponseEntity<Member>> {
        return memberService.getMember(id)
            .switchIfEmpty(Mono.empty())
            .map { member ->
                if (member != null) {
                    return@map ResponseEntity.ok(member)
                }
                return@map ResponseEntity.notFound().build<Member>()
            }

    }

    @PostMapping
    fun addMember(@RequestBody memberCreateCommand: MemberCreateCommand): Mono<ResponseEntity<Member>> {
        return memberService.addMember(memberCreateCommand)
            .switchIfEmpty(Mono.empty())
            .map { member ->
                if (member != null) {
                    return@map ResponseEntity.status(CREATED).body(member)
                }
                return@map ResponseEntity.notFound().build<Member>()
            }
    }

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long): Mono<ServerResponse> {
        return memberService.getMember(id)
            .filter(Objects::nonNull)
            .flatMap { member -> ok().build(memberService.deleteMemberById(member.id!!)) }
            .switchIfEmpty(notFound().build())
    }

}
package com.github.leesangho.dev.webfluxstudy.member.adaptor.`in`.web

import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import com.github.leesangho.dev.webfluxstudy.member.application.service.MemberService
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

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
                    return@map ResponseEntity.ok(member)
                }
                return@map ResponseEntity.notFound().build<Member>()
            }
    }

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long): Mono<ResponseEntity<Void>> {
        return memberService.deleteMember(id)
            .map { return@map ResponseEntity.noContent().build() }
    }

}
package com.github.leesangho.dev.webfluxstudy.member.adaptor.`in`.web

import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import com.github.leesangho.dev.webfluxstudy.member.application.service.MemberService
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import lombok.Getter
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/members")
class MemberRestController(val memberService: MemberService) {

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
    @ResponseStatus(NO_CONTENT)
    fun deleteMember(@PathVariable id: Long): Mono<ResponseEntity<Void>> {
        return memberService.getMember(id)
            .switchIfEmpty(Mono.empty())
            .flatMap{ member -> memberService.deleteMemberById(member.id!!)}
            .map { return@map ResponseEntity.noContent().build() }
    }

}
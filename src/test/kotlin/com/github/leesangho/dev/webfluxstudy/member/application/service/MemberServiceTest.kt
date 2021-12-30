package com.github.leesangho.dev.webfluxstudy.member.application.service

import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberEntity
import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberR2dbcRepository
import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class MemberServiceTest {

    private lateinit var memberService: MemberService

    @Mock
    private lateinit var memberR2dbcRepository: MemberR2dbcRepository

    @BeforeEach
    internal fun setUp() {
        memberService = MemberService(memberR2dbcRepository)
    }

    @Test
    @DisplayName("사용자 조회")
    fun getMember() {
        // given
        val id = 1L
        val name = "test"
        `when`(memberR2dbcRepository.findById(id))
            .thenReturn(Mono.just(MemberEntity(id, name)))

        // when
        val member = memberService.getMember(id)
            .blockOptional().get()

        // then
        assertThat(member.id).isEqualTo(id)
        assertThat(member.name).isEqualTo(name)
    }

    @Test
    @DisplayName("사용자 추가")
    fun addMember() {
        // given
        val name = "test"
        `when`(memberR2dbcRepository.save(any()))
            .thenReturn(Mono.just(MemberEntity(1, name)))

        // when
        val member = memberService.addMember(MemberCreateCommand(name))
            .blockOptional().get()

        // then
        assertThat(member.id).isNotNull()
        assertThat(member.name).isEqualTo(name)
    }

    @Test
    @DisplayName("사용자 제거")
    fun deleteMember() {
        // given
        val id = 1L
        `when`(memberR2dbcRepository.deleteById(id))
            .thenReturn(Mono.empty<Void?>().then())

        // when
        val deleteMember = memberService.deleteMemberById(id)

        // then
        StepVerifier.create(deleteMember)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun getMemberR2dbcRepository() {
    }
}
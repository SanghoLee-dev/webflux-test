package com.github.leesangho.dev.webfluxstudy.member.adaptor.`in`.web

import com.github.leesangho.dev.webfluxstudy.member.application.port.`in`.MemberCreateCommand
import com.github.leesangho.dev.webfluxstudy.member.application.service.MemberService
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class MemberRestControllerTest {
    private lateinit var client: WebTestClient

    @Mock
    private lateinit var memberService: MemberService

    @BeforeEach
    internal fun setUp() {
        client = WebTestClient.bindToController(MemberRestController(memberService))
            .build()
    }

    @Test
    @DisplayName("사용자 조회 API 성공 테스트")
    fun getMember() {
        // given
        val id = 1L
        val name = "test"
        given(memberService.getMember(id))
            .willReturn(Mono.just(Member(id, name)))

        // when
        client.get().uri("/members/{id}", id)
            .exchange()
        // then
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
            .jsonPath("$.name").isEqualTo(name)

    }

    @Test
    @DisplayName("사용자 등록 API 성공 테스트")
    fun addMember() {
        // given
        val id = 1L
        val name = "test"
        val memberCreateCommand = MemberCreateCommand(name)
        given(memberService.addMember(memberCreateCommand))
            .willReturn(Mono.just(Member(id, name)))

        // when
        client.post().uri("/members")
            .body(Mono.just(memberCreateCommand), memberCreateCommand.javaClass)
            .exchange()
        // then
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
            .jsonPath("$.name").isEqualTo(name)
    }

    @Test
    @DisplayName("사용자 삭제 API 성공 테스트")
    fun deleteMember() {
        // given
        val id = 1L
        val name = "test"
        given(memberService.getMember(id))
            .willReturn(Mono.just(Member(id, name)))
//
        given(memberService.deleteMemberById(id))
            .willReturn(Mono.empty<Void?>().then())

        // when
        client.delete().uri("/members/{id}", id)
            .exchange()
        // then
            .expectStatus().isNoContent
            .expectBody().isEmpty
    }

    @Test
    fun getMemberService() {
    }
}
package com.github.leesangho.dev.webfluxstudy.member.adaptor.`in`.web

import com.github.leesangho.dev.webfluxstudy.member.application.service.MemberService
import com.github.leesangho.dev.webfluxstudy.member.domain.Member
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class MemberHandlerTest {
    private lateinit var client: WebTestClient

    @Mock
    private lateinit var memberService: MemberService

    @BeforeEach
    internal fun setUp() {
        client = WebTestClient.bindToController(MemberHandler(memberService))
            .build()
    }

    @Test
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
    fun addMember() {
    }

    @Test
    fun deleteMember() {
    }

    @Test
    fun getMemberService() {
    }
}
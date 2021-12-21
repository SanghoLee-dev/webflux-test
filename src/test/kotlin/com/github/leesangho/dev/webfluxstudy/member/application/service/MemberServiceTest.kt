package com.github.leesangho.dev.webfluxstudy.member.application.service

import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberEntity
import com.github.leesangho.dev.webfluxstudy.member.adaptor.out.persistence.MemberR2dbcRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class MemberServiceTest {

    private lateinit var memberService: MemberService;

    @Mock
    private lateinit var memberR2dbcRepository: MemberR2dbcRepository

    @BeforeEach
    internal fun setUp() {
        memberService = MemberService(memberR2dbcRepository)
    }

    @Test
    @DisplayName("사용자 조회")
    fun getMember() {
        val id = 1L
        val name = "test"
        `when`(memberR2dbcRepository.findById(id))
            .thenReturn(Mono.just(MemberEntity(id, name)))

        val member = memberService.getMember(id)
            .blockOptional().get()

        assertThat(member.id).isEqualTo(id)
        assertThat(member.name).isEqualTo(name)
    }

    @Test
    fun addMember() {
    }

    @Test
    fun getMemberR2dbcRepository() {
    }
}
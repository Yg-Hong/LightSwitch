package com.lightswitch.core.domain.member.dto.res

import jakarta.validation.constraints.NotBlank

data class MemberResDto(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    @field:NotBlank
    val telNumber: String,
)
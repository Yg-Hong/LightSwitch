package com.lightswitch.core.domain.flag.repository.entity

import com.lightswitch.core.domain.flag.common.enum.FlagType
import jakarta.persistence.*
import lombok.Getter

@Entity(name = "variation")
class Variation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val variationId: Long? = null,

    val portion: Int,
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flag_id")
    val flagId: Flag,

    val variationType: FlagType,
    val value: String
)
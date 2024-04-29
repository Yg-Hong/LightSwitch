package com.lightswitch.core.domain.flag.repository.entity

import com.lightswitch.core.common.entity.BaseEntity
import jakarta.persistence.*

@Entity(name = "variation")
class Variation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val variationId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flag_id")
    val flag: Flag,

//    @Enumerated(EnumType.STRING)
//    var variationType: FlagType,
    var value: String,
    var portion: Int,
    var description: String,

    val defaultFlag: Boolean = false,
) : BaseEntity()
package com.lightswitch.core.domain.flag.service

import com.lightswitch.core.domain.flag.common.enum.FlagType
import com.lightswitch.core.domain.flag.dto.req.FlagRequestDto
import com.lightswitch.core.domain.flag.dto.req.FlagInitRequestDto
import com.lightswitch.core.domain.flag.dto.req.TagRequestDto
import com.lightswitch.core.domain.member.dto.req.SdkKeyReqDto
import com.lightswitch.core.domain.member.entity.Member
import com.lightswitch.core.domain.member.repository.MemberRepository
import com.lightswitch.core.domain.member.repository.SdkKeyRepository
import com.lightswitch.core.domain.member.service.SdkKeyService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class FlagSdkInitTest(
    @Autowired
    private val flagService: FlagService,

    @Autowired
    private val memberRepository: MemberRepository,

    @Autowired
    private val sdkKeyService: SdkKeyService,

    @Autowired
    private val sdkKeyRepository: SdkKeyRepository,
) {

    fun signUpAndSdkKeyForTest(): String {
        val member = Member(
            firstName = "동훈",
            lastName = "김",
            telNumber = "01012345678",
            email = "test@gmail.com",
            password = "1234"
        )
        memberRepository.save(member)

        val sdkKeyReqDto = SdkKeyReqDto(member.email)
        return sdkKeyService.createSdkKey(sdkKeyReqDto).key
    }

    @Test
    fun `SDK init()시 SDK_KEY에 해당하는 유저의 모든 Flag 정보 조회`() {
        // given
        val sdkKey = signUpAndSdkKeyForTest()
        val memberId = sdkKeyRepository.findByKey(sdkKey)!!.member.memberId

        val tag1 = TagRequestDto(
            colorHex = "#FFFFFF",
            content = "test"
        )

        val tag2 = TagRequestDto(
            colorHex = "#000000",
            content = "test2"
        )

        val flagRequestDto = FlagRequestDto(
            title = "test",
            tags = listOf(tag1, tag2),
            description = "test",
            type = FlagType.BOOLEAN,
            defaultValue = "TRUE",
            defaultValuePortion = 100,
            defaultValueDescription = "test",
            variation = "FALSE",
            variationPortion = 0,
            variationDescription = "test",
            userId = memberId!!
        )
        flagService.createFlag(flagRequestDto)

        val flagRequestDto2 = FlagRequestDto(
            title = "test2",
            tags = listOf(tag2),
            description = "test2",
            type = FlagType.INTEGER,
            defaultValue = "1",
            defaultValuePortion = 80,
            defaultValueDescription = "1 test",
            variation = "2",
            variationPortion = 20,
            variationDescription = "2 test",
            userId = memberId
        )
        flagService.createFlag(flagRequestDto2)

        val flagRequestDto3 = FlagRequestDto(
            title = "test3",
            tags = listOf(tag1),
            description = "test3",
            type = FlagType.INTEGER,
            defaultValue = "A",
            defaultValuePortion = 10,
            defaultValueDescription = "A test",
            variation = "B",
            variationPortion = 90,
            variationDescription = "B test",
            userId = memberId
        )
        flagService.createFlag(flagRequestDto3)

        // when
        val flagInitRequestDto = FlagInitRequestDto(
            sdkKey = sdkKey
        )
        val flagList = flagService.getAllFlagForInit(flagInitRequestDto)

        // then
        println(flagList.toString())
        Assertions.assertThat(flagList).hasSize(3)
    }
}
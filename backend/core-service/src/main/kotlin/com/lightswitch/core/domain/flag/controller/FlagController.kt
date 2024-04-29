package com.lightswitch.core.domain.flag.controller

import com.lightswitch.core.common.dto.BaseResponse
import com.lightswitch.core.common.dto.ResponseCode
import com.lightswitch.core.common.dto.success
import com.lightswitch.core.common.exception.BaseException
import com.lightswitch.core.domain.flag.dto.req.FlagRequestDto
import com.lightswitch.core.domain.flag.dto.res.FlagResponseDto
import com.lightswitch.core.domain.flag.dto.res.FlagSummaryDto
import com.lightswitch.core.domain.flag.dto.res.MainPageOverviewDto
import com.lightswitch.core.domain.flag.service.FlagService
import com.lightswitch.core.domain.member.service.SdkKeyService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/flag")
class FlagController(
    @Autowired
    private var flagService: FlagService,

    @Autowired
    private var sdkKeyService: SdkKeyService
) {

    @PostMapping("")
    fun createFlag(@RequestBody flagRequestDto: FlagRequestDto): BaseResponse<FlagResponseDto> {
        return success(flagService.createFlag(flagRequestDto))
    }

    @GetMapping("")
    fun getAllFlagsSummary(): BaseResponse<List<FlagSummaryDto>> {
        return success(flagService.getAllFlag())
    }

    @GetMapping("/{flagId}")
    fun getFlagDetail(@PathVariable flagId: Long): BaseResponse<FlagResponseDto> {
        return success(flagService.getFlag(flagId))
    }

    @GetMapping("/filter")
    fun filteredFlags(@RequestParam("tags") tags: List<String>): BaseResponse<List<FlagResponseDto>> {
        return success(flagService.filteredFlags(tags))
    }

    @DeleteMapping("/{flagId}")
    fun deleteFlag(@PathVariable flagId: Long): BaseResponse<Long> {
        return success(flagService.deleteFlag(flagId))
    }

    @PatchMapping("/{flagId}")
    fun switchFlag(@PathVariable flagId: Long): BaseResponse<Long> {
        return success(flagService.switchFlag(flagId))
    }

    @PutMapping("/{flagId}")
    fun updateFlag(
        @PathVariable flagId: Long,
        @RequestBody flagRequestDto: FlagRequestDto
    ): BaseResponse<FlagResponseDto> {
        return success(flagService.updateFlag(flagId, flagRequestDto))
    }

    @GetMapping("/overview")
    fun getFlagOverview(@PathParam(value = "memberId") memberId: Long): MainPageOverviewDto {
        val flagCountForOverview = flagService.getFlagCountForOverview()
        val sdkKeyForOverview = sdkKeyService.getSdkKeyForOverview(memberId)

        val totalFlags = flagCountForOverview["totalFlags"] ?: throw BaseException(ResponseCode.FLAG_NOT_FOUND)
        val activeFlags = flagCountForOverview["activeFlags"] ?: throw BaseException(ResponseCode.FLAG_NOT_FOUND)
        val sdkKey = sdkKeyForOverview["sdkKey"] ?: throw BaseException(ResponseCode.SDK_KEY_NOT_FOUND)

        return MainPageOverviewDto(
            totalFlags = totalFlags,
            activeFlags = activeFlags,
            sdkKey = sdkKey,
        )
    }

}
package com.demoboletto.dto.response;

import com.demoboletto.domain.SysTicket;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "TicketInfoDto", description = "ticket information")
@Builder
public record TicketInfoDto(
        @JsonProperty("ticket_full") @Schema(description = "ticket front full img")
        String ticketFull,
        @JsonProperty("ticket_small") @Schema(description = "ticket front small img")
        String ticketSmall
) {
    public static TicketInfoDto of(SysTicket sysTicket) {
        return TicketInfoDto.builder()
                .ticketFull(sysTicket.getTicketFull())
                .ticketSmall(sysTicket.getTicketSmall())
                .build();
    }
}

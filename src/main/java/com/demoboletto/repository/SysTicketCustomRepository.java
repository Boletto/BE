package com.demoboletto.repository;

import com.demoboletto.domain.SysTicket;

import java.util.Optional;

public interface SysTicketCustomRepository {
    Optional<SysTicket> randomEventTicket();

    Optional<SysTicket> randomTicket();
}

package com.demoboletto.repository;

import com.demoboletto.domain.SysTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysTicketRepository extends JpaRepository<SysTicket, Long>, SysTicketCustomRepository {
}

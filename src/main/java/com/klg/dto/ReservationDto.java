package com.klg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.sql.Date;

@AllArgsConstructor
@Getter
@Builder
public class ReservationDto {

    private final Long tenantId;

    private final Long rentObjectId;

    private final Date startDate;

    private final Date endDate;
}

package com.sejin.clubmanager.member.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sejin.clubmanager.crud.Identifiable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponse implements Identifiable<Long> {
    private Long id;
    private String name;
    private String email;
    private Long clubId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

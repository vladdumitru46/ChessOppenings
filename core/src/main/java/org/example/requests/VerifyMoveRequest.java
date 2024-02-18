package org.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@AllArgsConstructor
public class VerifyMoveRequest {
    private final String playerUsernameOrEmail;
    private final String courseName;
    private final Integer boardId;
    private final String start;
    private final String end;
    private final String pieceColour;
}

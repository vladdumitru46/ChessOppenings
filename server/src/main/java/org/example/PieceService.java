package org.example;

import com.example.models.board.Board;
import com.example.models.board.CellOnTheBord;
import com.example.models.pieces.*;
import org.example.repositoryes.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("pieceService")
public class PieceService {
    Logger logger = LoggerFactory.getLogger(PieceService.class);
    private final PawnRepository pawnRepository;
    private final RookRepository rookRepository;
    private final KnightRepository knightRepository;
    private final BishopRepository bishopRepository;
    private final QueenRepository queenRepository;
    private final KingRepository kingRepository;

    public PieceService(PawnRepository pawnRepository, RookRepository rookRepository, KnightRepository knightRepository, BishopRepository bishopRepository, QueenRepository queenRepository, KingRepository kingRepository) {
        this.pawnRepository = pawnRepository;
        this.rookRepository = rookRepository;
        this.knightRepository = knightRepository;
        this.bishopRepository = bishopRepository;
        this.queenRepository = queenRepository;
        this.kingRepository = kingRepository;
    }

    public boolean canThePawnMove(Board board, CellOnTheBord start, CellOnTheBord end, Pawn pawn) {
        logger.info("verify if the pawn can move");
        return pawnRepository.canMove(board, start, end, pawn);
    }

    public boolean canTheRookMove(Board board, CellOnTheBord start, CellOnTheBord end, Rook rook) {
        logger.info("verify if the rook can move");
        return rookRepository.canMove(board, start, end, rook);
    }

    public boolean canTheKnightMove(Board board, CellOnTheBord start, CellOnTheBord end, Knight knight) {
        logger.info("verify if the knight can move");
        return knightRepository.canMove(board, start, end, knight);
    }

    public boolean canTheBishopMove(Board board, CellOnTheBord start, CellOnTheBord end, Bishop bishop) {
        logger.info("verify if the bishop can move");
        return bishopRepository.canMove(board, start, end, bishop);
    }

    public boolean canTheQueenMove(Board board, CellOnTheBord start, CellOnTheBord end, Queen queen) {
        logger.info("verify if the queen can move");
        return queenRepository.canMove(board, start, end, queen);
    }

    public boolean canTheKingMove(Board board, CellOnTheBord start, CellOnTheBord end, King king) {
        logger.info("verify if the king can move");
        return kingRepository.canMove(board, start, end, king);
    }

    public boolean checkIsTheKingIsInCech(Board board, CellOnTheBord start, CellOnTheBord end, Pieces king) {
        logger.info("verify is the king is checked");
        return kingRepository.checkIfPieceIsNotAttacked(board, start, end, king);
    }
}

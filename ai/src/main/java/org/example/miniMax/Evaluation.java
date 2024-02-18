package org.example.miniMax;

import com.example.models.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.PieceService;

@Getter
@Setter
@AllArgsConstructor
public class Evaluation {

//    Mobilitatea Pieselor:
//
//    Acordă puncte pentru mobilitatea și flexibilitatea pieselor. Piese care au mai multe opțiuni și flexibilitate în mișcare pot fi evaluate mai bine.
//    Activitatea Pieselor:
//
//    Cu cât piesele sunt mai active și au mai multe posibilități de mișcare, cu atât pot fi evaluate mai pozitiv.
//    Coordonarea Pieselor:
//
//    Evaluează coordonarea între piese. Piese care lucrează împreună pentru a ataca sau apăra pot primi puncte suplimentare.
//    Controlul Liniilor și Coloanelor:
//
//    Acordă puncte pentru controlul liniilor și coloanelor tablei. Controlul acestora poate oferi avantaje tactice.
//    Stadiul Jocului:
//
//    Evaluează dacă jocul este în faza de deschidere, mijloc sau final. Poți adapta evaluarea în funcție de stadiul jocului.

    public int evaluationScore(Board board, PieceService pieceService, boolean isWhite) {
        if (isWhite) {
            return score(board, pieceService, true) + pieceService.canTheKingBeCheckedInNextMove(board, true)
                    - score(board, pieceService, false);
        } else {
            return score(board, pieceService, true) +
                    score(board, pieceService, false) + pieceService.canTheKingBeCheckedInNextMove(board, false);
        }
    }

    private static int score(Board board, PieceService pieceService, boolean isWhite) {
        return (board.getTotalPoints(isWhite) + pieceService.getAllPossibleMoves(board, isWhite).size()
                + pieceService.numberOfCenterSquaresAttacked(board, isWhite)
                + pieceService.canAnEnemyPieceBeCaptured(board, isWhite))
                + pieceService.verifyPawnStructure(board, isWhite)
                - (pieceService.verifyIfThereAreDoublePawns(board, isWhite) * 10);
    }

}

package chess.domain.pieces;

import chess.domain.Team;
import chess.domain.math.Direction;
import java.util.Collections;
import java.util.List;

public final class EmptyPiece extends Piece {

    static final String INVALID_MOVE = "EmptyPiece는 이동할 수 없습니다.";

    public EmptyPiece() {
        super(Team.NEUTRALITY, Collections.emptyList());
    }

    @Override
    protected void validateTeam(final Team team) {
    }

    @Override
    public void validateMove(final Direction correctDirection, final List<Piece> onRoutePieces) {
        throw new IllegalArgumentException(INVALID_MOVE);
    }
}

package chess.domain.pieces;

import static chess.domain.pieces.EmptyPiece.INVALID_MOVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import chess.domain.Team;
import chess.domain.math.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

class EmptyPieceTest {

    private EmptyPiece emptyPiece;

    @BeforeEach
    void setUp() {
        emptyPiece = new EmptyPiece();
    }

    @Test
    @DisplayName("EmptyPiece는 중립을 가진다.")
    void EmptyPiece는_중립을_가진다() {
        assertThat(emptyPiece.getTeam()).isEqualTo(Team.NEUTRALITY);
    }

    @Test
    @DisplayName("EmptyPiece는 방향을 가질 수 없고 움직일 수도 없다.")
    void EmptyPiece는_방향을_가질_수_없고_움직일_수도_없다() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> emptyPiece.validateMove(Direction.UP, List.of(new EmptyPiece()))
        ).withMessage(INVALID_MOVE);
    }
}

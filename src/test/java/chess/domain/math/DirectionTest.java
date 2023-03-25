package chess.domain.math;

import static chess.domain.math.Direction.DOWN;
import static chess.domain.math.Direction.DOWN_LEFT;
import static chess.domain.math.Direction.DOWN_RIGHT;
import static chess.domain.math.Direction.KNIGHT;
import static chess.domain.math.Direction.LEFT;
import static chess.domain.math.Direction.RIGHT;
import static chess.domain.math.Direction.UP;
import static chess.domain.math.Direction.UP_LEFT;
import static chess.domain.math.Direction.UP_RIGHT;
import static chess.domain.math.Direction.computeDirection;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.Position;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void findDirection_up() {
        var current = Position.of(1, 0);
        var target = Position.of(0, 0);

        assertThat(computeDirection(current, target)).isEqualTo(UP);
    }

    @Test
    void findDirection_down() {
        var current = Position.of(0, 0);
        var target = Position.of(1, 0);

        assertThat(computeDirection(current, target)).isEqualTo(DOWN);
    }

    @Test
    void findDirection_left() {
        var current = Position.of(1, 1);
        var target = Position.of(1, 0);

        assertThat(computeDirection(current, target)).isEqualTo(LEFT);
    }

    @Test
    void findDirection_right() {
        var current = Position.of(1, 0);
        var target = Position.of(1, 1);

        assertThat(computeDirection(current, target)).isEqualTo(RIGHT);
    }

    @Test
    void findDirection_upLeft() {
        var current = Position.of(1, 1);
        var target = Position.of(0, 0);

        assertThat(computeDirection(current, target)).isEqualTo(UP_LEFT);
    }

    @Test
    void findDirection_upRight() {
        var current = Position.of(1, 0);
        var target = Position.of(0, 1);

        assertThat(computeDirection(current, target)).isEqualTo(UP_RIGHT);
    }

    @Test
    void findDirection_downLeft() {
        var current = Position.of(0, 1);
        var target = Position.of(1, 0);

        assertThat(computeDirection(current, target)).isEqualTo(DOWN_LEFT);
    }

    @Test
    void findDirection_downRight() {
        var current = Position.of(0, 0);
        var target = Position.of(1, 1);

        assertThat(computeDirection(current, target)).isEqualTo(DOWN_RIGHT);
    }

    @Test
    void findDirection_knight_case1() {
        var current = Position.of(1, 2);
        var target = Position.of(0, 0);

        assertThat(computeDirection(current, target)).isEqualTo(KNIGHT);
    }

    @Test
    void findDirection_knight_case2() {
        var current = Position.of(2, 1);
        var target = Position.of(0, 0);

        assertThat(computeDirection(current, target)).isEqualTo(KNIGHT);
    }
}

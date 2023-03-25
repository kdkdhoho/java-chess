package chess.domain.math;

import static chess.domain.math.UnitVector.DOWN;
import static chess.domain.math.UnitVector.DOWN_LEFT;
import static chess.domain.math.UnitVector.DOWN_RIGHT;
import static chess.domain.math.UnitVector.LEFT;
import static chess.domain.math.UnitVector.RIGHT;
import static chess.domain.math.UnitVector.UP;
import static chess.domain.math.UnitVector.UP_LEFT;
import static chess.domain.math.UnitVector.UP_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnitVectorTest {

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_up() {
        var current = Position.of(5, 1);
        var target = Position.of(4, 1);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(UP);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_down() {
        var current = Position.of(0, 0);
        var target = Position.of(1, 0);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(DOWN);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_left() {
        var current = Position.of(0, 1);
        var target = Position.of(0, 0);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(LEFT);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_right() {
        var current = Position.of(0, 0);
        var target = Position.of(0, 1);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(RIGHT);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_upRight() {
        var current = Position.of(1, 0);
        var target = Position.of(0, 1);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(UP_RIGHT);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_downRight() {
        var current = Position.of(0, 0);
        var target = Position.of(1, 1);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(DOWN_RIGHT);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_downLeft() {
        var current = Position.of(0, 1);
        var target = Position.of(1, 0);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(DOWN_LEFT);
    }

    @Test
    @DisplayName("현재 위치와 목표 위치를 받아 단위 벡터를 계산해준다.")
    void ofTest_upLeft() {
        var current = Position.of(1, 1);
        var target = Position.of(0, 0);

        assertThat(UnitVector.computeUnitVector(current, target)).isEqualTo(UP_LEFT);
    }
}

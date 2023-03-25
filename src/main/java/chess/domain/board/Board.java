package chess.domain.board;

import chess.domain.Team;
import chess.domain.position.Position;
import chess.domain.math.Direction;
import chess.domain.math.UnitVector;
import chess.domain.pieces.EmptyPiece;
import chess.domain.pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Board {

    static final String INVALID_TARGET_POSITION = "위치가 중복되었습니다.";
    private static final Team DEFAULT_START_TEAM = Team.WHITE;
    private static final int ALL_KING_ALIVE_COUNT = 2;

    private final Map<Position, Piece> board;
    private Turn turn;

    public Board(final BoardMaker boardMaker) {
        this.board = boardMaker.createBoard();
        this.turn = new Turn(DEFAULT_START_TEAM);
    }

    public void movePiece(final Position currentPosition, final Position targetPosition) {
        validateNotEquals(currentPosition, targetPosition);

        Piece currentPositionPiece = findPieceAt(currentPosition);
        checkAndChangeTurn(currentPositionPiece, turn);

        validateMove(currentPosition, targetPosition, currentPositionPiece);

        move(currentPosition, targetPosition);
    }

    public boolean isKingAlive() {
        long kingCount = board.values().stream()
                .filter(Piece::isKing)
                .count();

        return kingCount == ALL_KING_ALIVE_COUNT;
    }

    private void validateNotEquals(final Position currentPosition, final Position targetPosition) {
        if (currentPosition.equals(targetPosition)) {
            throw new IllegalArgumentException(INVALID_TARGET_POSITION);
        }
    }

    private Piece findPieceAt(final Position position) {
        return board.get(position);
    }

    private void checkAndChangeTurn(final Piece currentPositionPiece, final Turn turn) {
        turn.validateTurn(currentPositionPiece);
        this.turn = turn.change();
    }

    private void validateMove(final Position currentPosition, final Position targetPosition, final Piece currentPositionPiece) {
        Direction correctDirection = Direction.computeDirection(currentPosition, targetPosition);
        UnitVector unitVector = UnitVector.computeUnitVector(currentPosition, targetPosition);
        List<Piece> onRoutePieces = getOnRoutePieces(currentPosition, targetPosition, unitVector);

        currentPositionPiece.validateMove(correctDirection, onRoutePieces);
    }

    private List<Piece> getOnRoutePieces(final Position currentPosition, final Position targetPosition, final UnitVector unitVector) {
        List<Piece> foundPieces = new ArrayList<>();

        Position pieceFinder = Position.from(currentPosition).move(unitVector);
        while (!pieceFinder.equals(targetPosition)) {
            foundPieces.add(findPieceAt(pieceFinder));
            pieceFinder = pieceFinder.move(unitVector);
        }
        foundPieces.add(findPieceAt(targetPosition));

        return foundPieces;
    }

    private void move(final Position currentPosition, final Position targetPosition) {
        Piece currentPositionPiece = findPieceAt(currentPosition);
        board.replace(targetPosition, currentPositionPiece);
        board.replace(currentPosition, new EmptyPiece());
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}

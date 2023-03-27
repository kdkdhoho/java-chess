package chess.domain.board;

import static java.util.stream.Collectors.toList;

import chess.domain.Team;
import chess.domain.math.Direction;
import chess.domain.math.UnitVector;
import chess.domain.pieces.EmptyPiece;
import chess.domain.pieces.Piece;
import chess.domain.pieces.PieceType;
import chess.domain.pieces.Score;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class Board {

    static final String INVALID_TARGET_POSITION = "위치가 중복되었습니다.";
    private static final Team DEFAULT_START_TEAM = Team.WHITE;
    private static final int ALL_KING_ALIVE_COUNT = 2;

    private final Map<Position, Piece> board;
    private Turn turn;

    private Board(final BoardMaker boardMaker) {
        this.board = boardMaker.createBoard();
        this.turn = new Turn(DEFAULT_START_TEAM);
    }

    public Board(final Map<Position, Piece> board, final Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public static Board create() {
        return new Board(new BoardMaker());
    }

    public void movePiece(final Position currentPosition, final Position targetPosition) {
        validateNotEquals(currentPosition, targetPosition);

        Piece currentPositionPiece = findPieceAt(currentPosition);
        validateMove(currentPosition, targetPosition, currentPositionPiece);

        checkAndChangeTurn(currentPositionPiece, turn);
        move(currentPosition, targetPosition);
    }

    private void validateNotEquals(final Position currentPosition, final Position targetPosition) {
        if (currentPosition.equals(targetPosition)) {
            throw new IllegalArgumentException(INVALID_TARGET_POSITION);
        }
    }

    private Piece findPieceAt(final Position position) {
        return board.get(position);
    }

    private void validateMove(final Position currentPosition, final Position targetPosition, final Piece currentPositionPiece) {
        Direction correctDirection = Direction.computeDirection(currentPosition, targetPosition);
        if (correctDirection == Direction.KNIGHT) {
            currentPositionPiece.validateMove(correctDirection, List.of(findPieceAt(targetPosition)));
            return;
        }

        UnitVector unitVector = UnitVector.computeUnitVector(currentPosition, targetPosition);
        List<Piece> onRoutePieces = getOnRoutePieces(currentPosition, targetPosition, unitVector);

        currentPositionPiece.validateMove(correctDirection, onRoutePieces);
    }

    private void checkAndChangeTurn(final Piece currentPositionPiece, final Turn turn) {
        turn.validateTurn(currentPositionPiece);
        this.turn = turn.changeTurn();
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

    public boolean isKingAlive() {
        long kingCount = board.values().stream()
                .filter(Piece::isKing)
                .count();

        return kingCount == ALL_KING_ALIVE_COUNT;
    }

    public Map<Team, Score> scores() {
        return new HashMap<>() {{
            put(Team.WHITE, scoreOf(Team.WHITE));
            put(Team.BLACK, scoreOf(Team.BLACK));
        }};
    }

    private Score scoreOf(final Team team) {
        return IntStream.range(0, Position.getMaxIndex())
                .mapToObj(column -> findOneColumnAllyPieces(column, team))
                .map(PieceType::calculateScore)
                .reduce(Score::add)
                .orElse(Score.ZERO);
    }

    private List<Piece> findOneColumnAllyPieces(final int column, final Team team) {
        return IntStream.range(0, Position.getMaxIndex())
                .mapToObj(row -> Position.of(row, column))
                .map(board::get)
                .filter(piece -> piece.isAlly(team))
                .collect(toList());
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public Turn getTurn() {
        return turn;
    }
}

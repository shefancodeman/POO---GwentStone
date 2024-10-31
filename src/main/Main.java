package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.CardInput;
import fileio.GameInput;
import fileio.Input;
import game.components.Board;
import game.components.Deck;
import game.components.Games;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        // creem board-ul
        Board board = new Board();

        // creem jocurile
        ArrayList<GameInput> gamesList = inputData.getGames();

        // initializam deck-urile playerilor
        ArrayList<ArrayList<CardInput>> temp =  inputData.getPlayerOneDecks().getDecks();
        ArrayList<Deck> deckListOne = new ArrayList<>();
        int i = 0;
        for (ArrayList<CardInput> deck: temp) {
            deckListOne.add(new Deck(deck, i, 1));
            i++;
        }
        i = 0;
        ArrayList<ArrayList<CardInput>> temp2 =  inputData.getPlayerTwoDecks().getDecks();
        ArrayList<Deck> deckListTwo = new ArrayList<>();
        for (ArrayList<CardInput> deck: temp2) {
            deckListTwo.add(new Deck(deck, i, 2));
            i++;
        }

        // tinem track la scor si la jocuri
        Games games = new Games(deckListOne, deckListTwo, gamesList);

        // incepem sa jucam!
        ArrayNode output = games.startMatches();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}

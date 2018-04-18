package com.groupryan.shared;

public class utils {
    public static String GAME_ID_TAG = "GAME_ID_TAG";
    public static String GAME_NAME_IN_USE = "Game name in use";
    public static String GAME_CREATED = "Game created";
    public static String VALID = "valid";
    public static String INVALID_PW = "Invalid Password";
    public static String INVALID_UN = "Invalid Username";
    public static String INVALID_GAMEID = "Invalid Game ID";
    public static String FULL_GAME = "Game is full";
    public static String STARTED_GAME = "Started game";

    public static final String CREATE_GAME = "createGame";
    public static final String JOIN_GAME = "joinGame";
    public static final String START_GAME = "startGame";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String GET_COMMANDS = "getCommands";
    public static final String GET_GAME_LIST = "getGameList";
    public static final String DISCARD_DESTCARD = "discardDestCard";
    public static final String DISCARD_TRAINCARD = "discardTrainCard";
    public static final String DRAW_THREE_CARDS = "drawDestinationCards";
    public static final String DRAW_COLOR_CARD="drawColorCards";

    public static final String BLACK = "BLACK";
    public static final String YELLOW = "YELLOW";
    public static final String GREEN = "GREEN";
    public static final String RED = "RED";
    public static final String BLUE = "BLUE";
    public static final String PINK = "PINK";
    public static final String WHITE = "WHITE";
    public static final String ORANGE = "ORANGE";
    public static final String LOCOMOTIVE = "LOCOMOTIVE";
    public static final String CURRENT_PLAYER = "CURRENT_PLAYER";

    public static final String DESTINATION = "DESTINATION";
    public static final String TRAIN = "TRAIN";
    public static final String BLANK = "BLANK";
    public static final String END_GAME = "END GAME";

    public static final String PLUGIN_REGISTRY_PATH = "server/src/main/java/com/groupryan/server/plugin_helps/PluginRegistry.json";

    public static final String EXEC_COMMAND = "/executeCommand";  // url for command API
//    public static final String SERVER_HOST = "localhost";
    public static final int PORT_NUMBER = 8080;
    public static String URL_PREFIX = "http://" + "10.24.71.142" + ":" + PORT_NUMBER;


    public static final String HTTP_POST = "POST";
    public static final String COMMAND_NAME = "CommandName";   // HTTP request header to determine

    public static final String GAME_SETUP = "GAME_SETUP";
    public static final String CHAT = "CHAT";
    public static final String HISTORY = "HISTORY";
    public static final String STAT = "STAT";
    public static final String BANK = "BANK";
    public static final String INVALID = "INVALID";
    public static final String FAILED_CONNECTION = "FAILED CONNECTION";
    public static final String FAILED_CONNECTION_MESSAGE = "FAILED TO CONNECT";
    public static final String HAND = "HAND";
    public static final String REDRAW_ROUTES = "REDRAW_ROUTES";
    public static final String CARD_DRAWN = "CARD_DRAWN";

    public static final String ROUTE_LENGTH = "LENGTH";
    public static final String ROUTE_COLOR = "ROUTE COLOR";
    public static final String ROUTE_ID = "ROUTE ID";

    public static final String GAME_OVER = "GAME_OVER";
    public static final String GAME_RESTORED = "GAME RESTORED";
    public static final String NEW_TURN = "NEW_TURN";
    public static final String CLAIM_ROUTE = "CLAIM ROUTE";
    public static final String COMMAND_LIST = "commandList";
    public static final String REJOIN_LOBBY = "REJOIN LOBBY";

    public static int convertStringToRColor(String color) {
        switch (color) {
            case BLACK:
                break;
            case YELLOW:
                break;
            case GREEN:
                break;
            case RED:
                break;
            case BLUE:
                break;
            case PINK:
                break;
            case WHITE:
                break;
            case ORANGE:
                break;
            case LOCOMOTIVE:
                break;
        }
        return 0;
    }

    public static void setUrlPrefix(String host){
        URL_PREFIX = "http://" + host + ":" + PORT_NUMBER;
    }
}

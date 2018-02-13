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


    public static final String EXEC_COMMAND = "/executeCommand";  // url for command API
    public static final String SERVER_HOST = "localhost";
    public static final int PORT_NUMBER = 8080;
    public static final String URL_PREFIX = "http://" + "172.20.20.20" + ":" + PORT_NUMBER;
    public static final String HTTP_POST = "POST";
    public static final String COMMAND_NAME = "CommandName";   // HTTP request header to determine

}

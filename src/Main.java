public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.creatDir("D://Games", "src");
        game.creatDir("D://Games", "res");
        game.creatDir("D://Games", "savegames");
        game.creatDir("D://Games", "temp");
        game.creatDir("D://Games/src", "main");
        game.creatDir("D://Games/src", "test");
        game.creatFile("D://Games/src/main", "Main.java");
        game.creatFile("D://Games/src/main", "Utils.java");
        game.creatDir("D://Games/res", "drawables");
        game.creatDir("D://Games/res", "vectors");
        game.creatDir("D://Games/res", "icons");
        game.creatFile("D://Games/temp", "temp.txt");
        game.creatLogFile("D://Games/temp/temp.txt");
        GameProgress player1 = new GameProgress(92, 4, 3, 234.1);
        GameProgress player2 = new GameProgress(67, 6, 7, 567.5);
        GameProgress player3 = new GameProgress(97, 2, 1, 43.2);
        game.saveGame("D://Games/savegames/", player1);
        game.saveGame("D://Games/savegames/", player2);
        game.saveGame("D://Games/savegames/", player3);
        game.zipFiles("D://Games/savegames/zip_saves.zip", "D://Games/savegames/");
        game.openZip("D://Games/savegames/zip_saves.zip", "D://Games/savegames/");
        game.openProgress("D://Games/savegames/new_save1.dat");
    }
}
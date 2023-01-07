import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Game {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    Date date = new Date(System.currentTimeMillis());
    StringBuilder log = new StringBuilder();

    public boolean creatDir(String dir, String name) {
        File file = new File(dir, name);
        if (file.mkdir()) {
            String text = String.format("%s создан каталог %s по адресу %s\n", formatter.format(date), file.getName(), file.getParent());
            log.append(text);
        } else {
            String text = String.format("%s каталог %s по адресу %s не создан\n", formatter.format(date), file.getName(), file.getParent());
            log.append(text);
        }
        return file.mkdir();
    }

    public File creatFile(String dir, String name) {
        File file = new File(dir, name);
        try {
            if (file.createNewFile()) {
                String text = String.format("%s создан фаил %s по адресу %s\n", formatter.format(date), file.getName(), file.getAbsolutePath());
                log.append(text);
            } else {
                String text = String.format("%s фаил %s не создан\n", formatter.format(date), file.getName(), file.getAbsolutePath());
                log.append(text);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    public void creatLogFile(String fileDir) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileDir, true))) {
            bw.append(log);
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveGame(String pathFile, GameProgress gp) {
        File dirSave = new File(pathFile);
        int numberSave;
        try {
            numberSave = dirSave.listFiles().length + 1;
        } catch (NullPointerException e) {
            numberSave = 1;
        }
        String name = String.format("save%d.dat", numberSave);
        try (FileOutputStream fos = new FileOutputStream("D://Games/savegames/" + name);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openProgress(String savegame) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(savegame);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(gameProgress);
    }

    public void openZip(String archivePath, String newDirPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(archivePath))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                try (FileOutputStream fout = new FileOutputStream(newDirPath + "new_" + entry.getName())) {
                    fout.write(zin.readAllBytes());
                    fout.flush();
                    zin.closeEntry();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void zipFiles(String archivePath, String saveFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archivePath))) {
            for (File file : new File(saveFile).listFiles()) {
                if (file.getName().equals(new File(archivePath).getName())) continue;
                FileInputStream fis = new FileInputStream(saveFile + file.getName());
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                file.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

import java.io.*;

public class GitTester {
    public static void main(String[] args) {
        testBlob();
        resetTestBlob();
    }

    public static void testBlob() {
        resetTestBlob();

        String file1Path = "file.txt";
        String file2Path = "duck.txt";
        String file3Path = "hamlet.txt";
        File file1 = new File(file1Path);
        File file2 = new File(file2Path);
        File file3 = new File(file3Path);

        safelyCreateFile(file1);
        safelyCreateFile(file2);
        safelyCreateFile(file3);

        String content1 = "Just another file";
        String content2 = "🦆🦆🦆";
        String content3 = "To be\nOr not to be\nThat is the question";

        safelyPopulateFile(file1, content1);
        safelyPopulateFile(file2, content2);
        safelyPopulateFile(file3, content3);

        safelyBlobFile(file1Path);
        safelyBlobFile(file2Path);
        safelyBlobFile(file3Path);

        // Predetermined what hashes should be
        String file1Hashed = "4e787877860da028e7e3d89022669561ad11b8de";
        String file2Hashed = "1c11ed170dcecb1cb2ffe5252553f36f8f03f4da";
        String file3Hashed = "b56e2400518cfc65f56d76b35eaddab2d70a4490";

        File blob1 = new File("git/objects/" + file1Hashed);
        File blob2 = new File("git/objects/" + file2Hashed);
        File blob3 = new File("git/objects/" + file3Hashed);

        assert blob1.exists();
        System.out.println("File 1 blob successfully created");
        assert content1.equals(extractContents(blob1));
        System.out.println("File 1 blob has correct content");

        assert blob2.exists();
        System.out.println("File 2 blob successfully created");
        assert content2.equals(extractContents(blob2));
        System.out.println("File 2 blob has correct content");

        assert blob3.exists();
        System.out.println("File 3 blob successfully created");
        assert content3.equals(extractContents(blob3));
        System.out.println("File 3 blob has correct content");
    }

    public static String extractContents(File f) {
        StringBuilder fullContents = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                fullContents.append(line);
                line = br.readLine();
                if (line != null) {
                    fullContents.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return fullContents.toString();
    }

    public static void safelyCreateFile(File f) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void safelyPopulateFile(File f, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void safelyBlobFile(String filePath) {
        try {
            Git.blobFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void resetTestBlob() {
        File file1 = new File("file.txt");
        File file2 = new File("duck.txt");
        File file3 = new File("hamlet.txt");
        // File paths to blob predetermiend based on content of files
        File blob1 = new File("git/objects/4e787877860da028e7e3d89022669561ad11b8de");
        File blob2 = new File("git/objects/1c11ed170dcecb1cb2ffe5252553f36f8f03f4da");
        File blob3 = new File("git/objects/b56e2400518cfc65f56d76b35eaddab2d70a4490");

        if (file1.exists()) {
            file1.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
        if (file3.exists()) {
            file3.delete();
        }
        if (blob1.exists()) {
            blob1.delete();
        }
        if (blob2.exists()) {
            blob2.delete();
        }
        if (blob3.exists()) {
            blob3.delete();
        }
    }
}

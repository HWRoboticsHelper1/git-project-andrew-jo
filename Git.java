import java.io.*;

class Git {
    public static void main(String[] args) {
        initializeRepo();
    }

    public static void initializeRepo() {
        boolean alreadyInitialized = true;
        File gitRepo = new File("git");
        if (!gitRepo.exists()) {
            gitRepo.mkdir();
            alreadyInitialized = false;
        }
        File objectRepo = new File("git/objects");
        if (!objectRepo.exists()) {
            objectRepo.mkdir();
            alreadyInitialized = false;
        }
        File indexFile = new File("git/index");
        if (!indexFile.exists()) {
            try {
                indexFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            alreadyInitialized = false;
        }
        File headFile = new File("git/HEAD");
        if (!headFile.exists()) {
            try {
                headFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            alreadyInitialized = false;
        }
        if (!alreadyInitialized) {
            System.out.println("Git Repository Created");
        } else {
            System.out.println("Git Repository Already Exists");
        }
    }
}
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String hashFile(String filePath) {
        try {
            String content = extractContent(filePath);
            return hashContent(content);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractContent(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException("File doesn't exist");
        }
        StringBuilder content = new StringBuilder();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            line = br.readLine();
            while (line != null) {
                content.append(line);
                line = br.readLine();
                if (line != null) {
                    content.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String hashContent(String content) {
        byte[] hashVal = new byte[0];
        try {
            hashVal = getSHA(content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hexaHash = toHexString(hashVal);

        return hexaHash;
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 40) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static void blobFile(String filePath) throws FileNotFoundException {
        String hashedFile = hashFile(filePath);
        if (hashedFile.equals("")) {
            throw new FileNotFoundException();
        }
        String blobFilePath = "git/objects/" + hashedFile;
        File blobFile = new File(blobFilePath);
        if (!blobFile.exists()) {
            try {
                blobFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(blobFile))) {
                String line = br.readLine();
                while (line != null) {
                    bw.write(line);
                    line = br.readLine();
                    if (line != null) {
                        bw.write("\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static String input_zip_path;
    public static final String output_folder_path = "temp";

    public static long begin_time = Long.MAX_VALUE;

    public static final int threads = 10;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (args.length != 1){
            System.out.print("Type the zip file path: ");
            input_zip_path = scan.nextLine();
        }else{
            input_zip_path = args[0];
        }
        BruteForce attacker = new BruteForce(4, false, true, false, false);
        System.out.println("Loading file [" + input_zip_path + "]");
        File f = new File(input_zip_path);
        if (!f.exists() || !f.isFile()){
            System.out.println("Target file is not found.");
            System.exit(1);
        }
        System.out.println("Attacking...");
        begin_time = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            new Thread(new Attacker(attacker)).start();
        }
    }

    public static void exit(){
        long time = System.currentTimeMillis() - begin_time;
        System.out.println("Done!");
        System.out.println("所要時間 : " + (time / 1000f) + "秒");
        System.exit(0);
    }

    static class Attacker implements Runnable {
        BruteForce attacker;

        public Attacker(BruteForce attacker){
            this.attacker = attacker;
        }

        public void run() {
            String pass;
            ZipFile file;
            while ((pass = attacker.next()) != null) {
                try {
                    file = new ZipFile(input_zip_path, pass.toCharArray());
                    file.extractAll(output_folder_path);
                    System.out.println("The password is found.");
                    System.out.println("The password is " + "\"" + pass + "\".");
                    exit();
                } catch (ZipException ignored) {
                }
            }
            System.out.println("Cannot find the password.");
            System.out.println("Please retry to another pattern.");
            exit();
        }
    }
}

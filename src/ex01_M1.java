import java.io.InputStream;
import java.net.*;

public class ex01_M1 {

    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();

        ///
        if (args.length == 0) return;
        URL url = new URL(args[0]);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        System.out.println(is.read());

        ///

        long endTime = System.nanoTime();
        long time = (endTime - startTime);
        System.out.println(time);
    }
}

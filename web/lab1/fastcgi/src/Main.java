import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static Logger logger = Logger.getLogger("MyLog");

    static {
        logger.setUseParentHandlers(false);
        try {
            FileHandler fh = new FileHandler("log.txt", true);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            logger.info("Logger initialized");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("org.example.Main");
        FCGIInterface fcgiInterface = new FCGIInterface();
        HandlerInterface handler = new ProxyHandler(new Handler(fcgiInterface), fcgiInterface);
        while (true) {
            try {
                if(fcgiInterface.FCGIaccept() >= 0) {
                    handler.handleRequest();
                }
            } catch (Exception e) {
                logger.info("pizdec" + e.getMessage());
            }
        }


    }
}

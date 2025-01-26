import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.util.Properties;

public class ProxyHandler implements HandlerInterface {
    private final HandlerInterface originalHandler;
    private final FCGIInterface fcgiInterface;

    public ProxyHandler(HandlerInterface handler, FCGIInterface fcgiInterface) {
        this.originalHandler = handler;
        this.fcgiInterface = fcgiInterface;
    }

    @Override
    public void handleRequest() throws IOException {
        Main.logger.info("startinterface");
        String requestUri = fcgiInterface.request.params.getProperty("REQUEST_URI");
        if (!requestUri.matches("^/fcgi-bin/fastcgi\\.jar(\\?x=[^&]+&y=[^&]+&r=[^&]+)?$")) {
            sendError("wrong request");
            return;
        }

        String requestMethod = getRequestMethod();
        if (!"GET".equalsIgnoreCase(requestMethod)) {
            Main.logger.info("Rejected request with method: " + requestMethod);
            sendError("only get method allowed");
            return;
        }

        originalHandler.handleRequest();
    }

    private String getRequestMethod() throws IOException {
        Properties params = fcgiInterface.request.params;
        String method = params.getProperty("REQUEST_METHOD");
        if (method == null || method.isEmpty()) {
            Main.logger.info("Rejected request with method: " + method);
            sendError("REQUEST_METHOD not found");
        }
        return method;
    }

    public void sendError(String message) {
        String httpResponse = """
                HTTP/1.1 400 Bad org.example.fastgci.Request
                Content-Type: text
                Content-Length: %d
                Access-Control-Allow-Origin: *
                
                %s
                """.formatted(message.getBytes().length, message);
        System.out.println(httpResponse);
    }


}


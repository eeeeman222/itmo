import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static com.fastcgi.FCGIInterface.request;

public class Handler implements HandlerInterface {
    private final float[] rValues = {1f, 2f, 3f, 4f, 5f};
    private final float[] xValues = {-2f, -1.5f, -1f, -0.5f, 0f, 0.5f, 1f, 1.5f, 2f};
    private final FCGIInterface fcgi;

    public Handler(FCGIInterface fcgi) {
        this.fcgi = fcgi;
    }

    @Override
    public void handleRequest() throws IOException {
        Request request;
        long startTime = System.currentTimeMillis();
        try {
            request = readRequestParams();
            Main.logger.info("org.example.fastgci.Request: " + request + "\n");
        } catch (Exception e) {
            sendError("плохие данные");
            return;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        if (validateRequest(request)) {
            Main.logger.info("yeah");
            sendResponse(request.x(),
                    request.y(),
                    request.r(),
                    checkHit(request),
                    time,
                    System.currentTimeMillis() - startTime);
        } else {
            sendError("повалидируйте");
        }
    }

    public boolean validateRequest(Request request) {
        if (request == null) {
            return false;
        }
        if (request.y() < -3f || request.y() > 3f) {
            return false;
        }
        for (float val : rValues) {
            if (val == request.r()) {
                for (float x : xValues) {
                    if (x == request.x()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkHit(Request request) {
        double x = request.x();
        double y = request.y();
        double r = request.r();
        return  y >= (double) 1 / 2 * x - r / 2
                || x <= (r / 2) && y >= (-r) && y <= 0 && x >= 0
                || (x * x + y * y) <= Math.pow((r / 2), 2) && x <= 0;
        }

    public Request readRequestParams() throws Exception {
        try {
            String query = request.params.getProperty("QUERY_STRING");
            float[] values = new float[]{0, 0, 0};
            AtomicBoolean x = new AtomicBoolean(false);
            AtomicBoolean y = new AtomicBoolean(false);
            AtomicBoolean r = new AtomicBoolean(false);
            Pattern.compile("&")
                    .splitAsStream(query)
                    .map(s -> s.split("="))
                    .forEach(pair -> {
                        String key = pair[0];
                        String valueStr = pair[1];

                        if (valueStr.length() > 8) {
                            throw new IllegalArgumentException("Значение слишком велико: " + key);
                        }

                        float value = Float.parseFloat(valueStr);
                        switch (key) {
                            case "x":
                                values[0] = value;
                                x.set(true);
                                break;
                            case "y":
                                values[1] = value;
                                y.set(true);
                                break;
                            case "r":
                                values[2] = value;
                                r.set(true);
                                break;
                        }
                    });
            if(x.get() && y.get() && r.get()) {
                return new Request(values[0], values[1], values[2]);
            }
            else{
                sendError("Вы не указали данные, обязательные к заполнению!");
            }
        } catch (Exception e) {
            sendError("слишком длинно");
        }
        return null;
    }


    public void sendResponse(float x, float y, float r, boolean result, String requestTime, long processingTime) {
        if (requestTime == null) {
            return;
        }
        String body = String.valueOf((new Result(x, y, r, result, requestTime, processingTime)));
        String httpResponse = """
                HTTP/1.1 200 OK
                Content-Type: text/html
                Content-Length: %d
                Access-Control-Allow-Origin: *
                
                %s
                """.formatted(body.getBytes().length, body);
        Main.logger.info("send" + httpResponse);
        System.out.println(httpResponse);
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
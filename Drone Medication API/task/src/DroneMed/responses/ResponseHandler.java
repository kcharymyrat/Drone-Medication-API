package DroneMed.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseHandler {

    public static <T> ResponseEntity<Map<String, Object>> responseBuilder(
            String message, HttpStatus httpStatus, T responseObject) {

        Map<String, Object> response = Map.of(
                "message", message,
                "httpStatus", httpStatus,
                "data", responseObject
        );

        return new ResponseEntity<>(response, httpStatus);
    }

    public static <T> ResponseEntity<Map<String, Object>> responseBuilder(
            String message, HttpStatus httpStatus) {

        Map<String, Object> response = Map.of(
                "message", message,
                "httpStatus", httpStatus
        );

        return new ResponseEntity<>(response, httpStatus);
    }
}

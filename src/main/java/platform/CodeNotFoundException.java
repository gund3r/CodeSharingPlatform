package platform;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "platform.Code not found")
public class CodeNotFoundException extends RuntimeException {
}


package exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

package kboparai.beans;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    private Long userId;
    @NonNull
    private String email;
    @NonNull
    private String encryptedPassword;
    @NonNull
    private boolean enabled;
}

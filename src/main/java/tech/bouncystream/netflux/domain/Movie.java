package tech.bouncystream.netflux.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {

    private String id = UUID.randomUUID().toString();

    @NonNull
    private String title;

}

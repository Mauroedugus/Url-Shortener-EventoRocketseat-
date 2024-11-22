package com.rocketseat.createUrlShortner;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
public class UrlData {
    private String originalUrl;
    private long expirationTime;
}

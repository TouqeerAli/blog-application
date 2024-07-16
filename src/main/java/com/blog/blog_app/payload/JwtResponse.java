package com.blog.blog_app.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private  String jwtToken;
    private String username;
}

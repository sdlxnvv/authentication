package org.example.entity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "password")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User2 {
    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private Boolean active;
}
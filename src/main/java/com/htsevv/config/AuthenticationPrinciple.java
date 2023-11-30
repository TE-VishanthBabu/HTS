package com.htsevv.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationPrinciple {

    private String id;
    private String email;
    private String authority;

    public UUID getId(){
        if(this.id != null){
            return UUID.fromString(this.id);
        }
        return null;
    }

}

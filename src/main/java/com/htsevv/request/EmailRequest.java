package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private List<String> recipients;
    private String subject;
    private String message;
    private String from;
    private List<String> bccRecipients;
}

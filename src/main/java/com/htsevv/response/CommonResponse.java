package com.htsevv.response;

import com.htsevv.constants.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private String status = Constant.STATUS_SUCCESS;
    private String code = "200";
    private Object data;
    private String message;
}

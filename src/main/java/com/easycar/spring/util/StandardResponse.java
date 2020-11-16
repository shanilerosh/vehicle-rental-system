package com.easycar.spring.util;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse {
    private int code;
    private String msg;
    private Object data;
}

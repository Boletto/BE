package com.demoboletto.dto.push.common;

import java.util.Map;

public interface NotiDto {
    String getMessage();

    String getTitle();

    Map<String, String> toMap();
}

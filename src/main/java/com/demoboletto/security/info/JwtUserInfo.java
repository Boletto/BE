package com.demoboletto.security.info;

import com.demoboletto.type.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}

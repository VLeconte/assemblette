package com.assemblette.assemblette_backend.mapper;

import com.assemblette.assemblette_backend.dto.AuthorityJson;
import com.assemblette.assemblette_backend.entity.Authority;

public class AuthorityMapper {
    public static Authority mapToAuthority(AuthorityJson authorityJson) {
        return Authority.builder()
                .id(authorityJson.getId())
                .label(authorityJson.getLabel())
                .labelAbbreviated(authorityJson.getLabelAbbreviated())
                .authorityType(authorityJson.getAuthorityType())
                .associatedColor(authorityJson.getAssociatedColor())
                .build();
    }
}

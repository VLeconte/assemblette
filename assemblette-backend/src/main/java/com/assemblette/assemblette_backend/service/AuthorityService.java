package com.assemblette.assemblette_backend.service;

import java.util.List;

import com.assemblette.assemblette_backend.entity.Authority;

public interface AuthorityService {
    Authority createAuthority(Authority authority);

    Authority getAuthorityById(String authorityId);

    List<Authority> getAllAuthoritys();

    Authority updateAuthority(String authorityId, Authority authority);

    void deleteAuthorityById(String authorityId);

    void addAuthoritysFromResourcesFolder(String folderName);
}

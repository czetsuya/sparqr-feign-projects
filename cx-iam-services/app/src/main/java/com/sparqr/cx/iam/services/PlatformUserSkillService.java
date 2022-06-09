package com.sparqr.cx.iam.services;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PlatformUserSkillService {

  CompletableFuture<Set<String>> createOrUpdate(String userAlias, Set<String> skills);

  CompletableFuture<Set<String>> getSkills(String userAlias);
}

/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2020-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.ansiblegalaxy;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.plugins.ansiblegalaxy.AnsibleGalaxyFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.ReplicationAttributes;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiProxyRepository;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.sonatype.nexus.plugins.ansiblegalaxy.AnsibleGalaxyAttributes;

/**
 * @since 3.41
 */
public class AnsibleGalaxyProxyApiRepository
    extends SimpleApiProxyRepository
{

  @NotNull
  private final AnsibleGalaxyAttributes ansiblegalaxy;

  public AnsibleGalaxyProxyApiRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRuleName") final String routingRuleName,
      @JsonProperty("replication") @JsonInclude(value= Include.NON_EMPTY, content=Include.NON_NULL)
      final ReplicationAttributes replication,
      @JsonProperty("ansiblegalaxy") final AnsibleGalaxyAttributes ansiblegalaxy)
  {
    super(name, AnsibleGalaxyFormat.NAME, url, online, storage, cleanup, proxy, negativeCache, httpClient, routingRuleName, replication);
    this.ansiblegalaxy = ansiblegalaxy;
  }

  public AnsibleGalaxyAttributes getAnsibleGalaxy() {
    return ansiblegalaxy;
  }
}
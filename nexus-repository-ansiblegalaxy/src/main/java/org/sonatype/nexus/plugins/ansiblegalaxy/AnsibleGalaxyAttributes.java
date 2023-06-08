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

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class AnsibleGalaxyAttributes
{
  @ApiModelProperty(value = "Content Disposition",
      allowableValues = "INLINE,ATTACHMENT", example = "ATTACHMENT")
  @NotEmpty
  private final String contentDisposition;

  @JsonCreator
  public AnsibleGalaxyAttributes(
    @JsonProperty("contentDisposition") final String contentDisposition) {
    this.contentDisposition = contentDisposition;
  }

  public String getContentDisposition() {
    return contentDisposition;
  }
}

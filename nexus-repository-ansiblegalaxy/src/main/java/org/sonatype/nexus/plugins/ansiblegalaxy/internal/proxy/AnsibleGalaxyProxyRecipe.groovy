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
package org.sonatype.nexus.plugins.ansiblegalaxy.internal.proxy

import org.sonatype.nexus.plugins.ansiblegalaxy.AnsibleGalaxyFormat
import org.sonatype.nexus.plugins.ansiblegalaxy.internal.AnsibleGalaxyRecipeSupport
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.proxy.ProxyHandler
import org.sonatype.nexus.repository.types.ProxyType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Route
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

/**
 * AnsibleGalaxy proxy repository recipe.
 * @since 0.0.1
 */
@Named(AnsibleGalaxyProxyRecipe.NAME)
@Singleton
class AnsibleGalaxyProxyRecipe
        extends AnsibleGalaxyRecipeSupport {
    public static final String NAME = 'ansiblegalaxy-proxy'

    @Inject
    Provider<AnsibleGalaxyProxyFacetImpl> proxyFacet

    @Inject
    ProxyHandler proxyHandler

    @Inject
    AnsibleGalaxyProxyRecipe(@Named(ProxyType.NAME) final Type type,
                             @Named(AnsibleGalaxyFormat.NAME) final Format format) {
        super(type, format)
    }

    @Override
    void apply(@Nonnull final Repository repository) throws Exception {
        repository.attach(securityFacet.get())
        repository.attach(configure(viewFacet.get()))
        repository.attach(httpClientFacet.get())
        repository.attach(negativeCacheFacet.get())
        repository.attach(componentMaintenanceFacet.get())
        repository.attach(proxyFacet.get())
        repository.attach(storageFacet.get())
        repository.attach(searchFacet.get())
        repository.attach(purgeUnusedFacet.get())
        repository.attach(attributesFacet.get())
    }

    /**
     * Configure {@link ViewFacet}.
     */
    private ViewFacet configure(final ConfigurableViewFacet facet) {
        Router.Builder builder = new Router.Builder()

        [
                apiInternalsMatcher(),
                collectionDetailMatcher(),
                collectionVersionListMatcher(),
                collectionVersionListMatcherPages(),
                collectionVersionDetailMatcher(),
                collectionArtifactMatcher(),
                collectionArtifactV2Matcher(),
                collectionArtifactIhmMatcher(),
                roleSearchMatcher(),
                roleDetailMatcher(),
                roleVersionListMatcher(),
                roleArtifactMatcher()
        ].each { matcher ->
            builder.route(new Route.Builder().matcher(matcher)
                    .handler(timingHandler)
                    .handler(securityHandler)
                    .handler(routingRuleHandler)
                    .handler(exceptionHandler)
                    .handler(handlerContributor)
                    .handler(negativeCacheHandler)
                    .handler(partialFetchHandler)
                    .handler(contentHeadersHandler)
                    .handler(conditionalRequestHandler)
                    .handler(unitOfWorkHandler)
                    .handler(lastDownloadedHandler)
                    .handler(proxyHandler)
                    .create())
        }

        builder.defaultHandlers(HttpHandlers.notFound())

        facet.configure(builder.create())

        return facet
    }
}

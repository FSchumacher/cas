package org.apereo.cas.oidc.ticket;

import org.apereo.cas.oidc.AbstractOidcTests;
import org.apereo.cas.oidc.OidcConstants;
import org.apereo.cas.services.RegisteredServiceTestUtils;
import org.apereo.cas.support.oauth.web.response.callback.OAuth20AuthorizationModelAndViewBuilder;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.pac4j.core.context.JEEContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link OidcPushedAuthorizationModelAndViewBuilderTests}.
 *
 * @author Misagh Moayyed
 * @since 6.5.0
 */
@Tag("OIDC")
public class OidcPushedAuthorizationModelAndViewBuilderTests extends AbstractOidcTests {
    @Autowired
    @Qualifier("oidcPushedAuthorizationModelAndViewBuilder")
    private OAuth20AuthorizationModelAndViewBuilder oidcPushedAuthorizationModelAndViewBuilder;

    @Test
    public void verifyOperation() {
        val request = new MockHttpServletRequest();
        val response = new MockHttpServletResponse();
        val webContext = new JEEContext(request, response);

        val parameters = new LinkedHashMap<String, String>();
        parameters.put(OidcConstants.EXPIRES_IN, "100");
        parameters.put(OidcConstants.REQUEST_URI, UUID.randomUUID().toString());
        val mv = oidcPushedAuthorizationModelAndViewBuilder.build(webContext, getOidcRegisteredService(),
            RegisteredServiceTestUtils.CONST_TEST_URL2, parameters);
        assertTrue(mv.getModel().containsKey(OidcConstants.EXPIRES_IN));
        assertTrue(mv.getModel().containsKey(OidcConstants.REQUEST_URI));
    }
}

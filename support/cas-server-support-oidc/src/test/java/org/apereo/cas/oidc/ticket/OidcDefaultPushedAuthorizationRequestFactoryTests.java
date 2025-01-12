package org.apereo.cas.oidc.ticket;

import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.oidc.AbstractOidcTests;
import org.apereo.cas.services.RegisteredServiceTestUtils;
import org.apereo.cas.support.oauth.OAuth20GrantTypes;
import org.apereo.cas.support.oauth.OAuth20ResponseTypes;
import org.apereo.cas.support.oauth.web.response.accesstoken.ext.AccessTokenRequestDataHolder;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link OidcDefaultPushedAuthorizationRequestFactoryTests}.
 *
 * @author Misagh Moayyed
 * @since 6.5.0
 */
@Tag("OIDC")
public class OidcDefaultPushedAuthorizationRequestFactoryTests extends AbstractOidcTests {
    @Test
    public void verifyOperation() throws Exception {
        val registeredService = getOidcRegisteredService();
        val holder = AccessTokenRequestDataHolder.builder()
            .clientId(registeredService.getClientId())
            .service(RegisteredServiceTestUtils.getService())
            .authentication(RegisteredServiceTestUtils.getAuthentication())
            .registeredService(registeredService)
            .grantType(OAuth20GrantTypes.AUTHORIZATION_CODE)
            .responseType(OAuth20ResponseTypes.CODE)
            .build();
        val factory = (OidcPushedAuthorizationRequestFactory) defaultTicketFactory.get(OidcPushedAuthorizationRequest.class);
        val ticket = factory.create(holder);
        assertNotNull(ticket);
        assertTrue(ticket.getId().startsWith(OidcPushedAuthorizationRequest.PREFIX));
        assertEquals(OidcPushedAuthorizationRequest.class, factory.getTicketType());

        val expiration = Beans.newDuration(casProperties.getAuthn().getOidc().getPar().getMaxTimeToLiveInSeconds()).getSeconds();
        assertEquals(expiration, ticket.getExpirationPolicy().getTimeToLive());
    }
}

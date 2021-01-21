package am.learning.library.config;


import am.learning.library.model.Authority;
import am.learning.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
public class SecurityContextProvider {


    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;


    @SuppressWarnings("unchecked")
    public User getByAuthentication(OAuth2Authentication oAuth2Authentication) {

        Map<String, Object> additional = resourceServerTokenServices.readAccessToken(
                ((OAuth2AuthenticationDetails) oAuth2Authentication.getDetails()).getTokenValue())
                .getAdditionalInformation();

        User userEntity = new User();
        userEntity.setId((Long) additional.get("userId"));
        userEntity.setName(String.valueOf(additional.get("name")));
        userEntity.setSurname(String.valueOf(additional.get("surname")));
        userEntity.setUsername(String.valueOf(additional.get("username")));

        HashMap<Integer, String> roles = (HashMap<Integer, String>) additional.get("roles");
        Set<Authority> authorities = new HashSet<>();
        roles.forEach((key, value) -> authorities.add(new Authority(key, value)));
        userEntity.setAuthorities(authorities);

        return userEntity;
    }

}

// package rt.strategy.utils;

// import java.nio.charset.StandardCharsets;
// import java.util.Base64;

// import org.springframework.http.HttpHeaders;
// import org.springframework.web.util.UriComponentsBuilder;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// public class ConnectionUtils {

//      public static String buildURI(String baseURL, String path) {
//         UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL).path(path);
//         String uri = builder.build().toUriString();
//         log.debug("BUILT URI: {}", uri);
//         return uri;
//     }

//      public static HttpHeaders createAuthHeaders(String userEmail, String token) {
//         String auth = userEmail + ":" + token;
//         String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
//         HttpHeaders headers = new HttpHeaders();
//         headers.set("Authorization", "Basic " + encodedAuth);
//         return headers;
//     }

// }

package rt.strategy.utils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionUtils {

    public static String buildURI(String baseURL, String path) {
        // Use pathSegment if you want to avoid double slashes, otherwise keep .path()
        // UriComponentsBuilder builder =
        // UriComponentsBuilder.fromHttpUrl(baseURL).path(path);
        // String uri = builder.build().toUriString();
        // log.debug("BUILT URI: {}", uri);
        // return uri;
        URI baseUri = URI.create(baseURL);
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .uri(baseUri)
                .path(path);
        String uri = builder.build().toUriString();
        log.debug("BUILT URI: {}", uri);
        return uri;
    }

    public static HttpHeaders createAuthHeaders(String userEmail, String token) {
        String auth = userEmail + ":" + token;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}
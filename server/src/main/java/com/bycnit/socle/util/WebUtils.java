package com.bycnit.socle.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebUtils {

    /**
     * Returns the connected user
     *
     * @param request
     *            objet request
     * @return username
     */
    public static String getConnectedUser(final HttpServletRequest request) {

        if (isLocalClient(request)) {
            return getLocalUserName();
        }

        final Principal principal = request.getUserPrincipal();

        if (principal != null && StringUtils.isNotEmpty(principal.getName())) {
            final String username = principal.getName();
            return username.substring(0, username.indexOf('@')).toLowerCase();
        }

        return null;
    }

    /**
     * Returns 'true' if the user is connected locally
     *
     * @param request
     *            http request
     * @return true if localhost
     */
    public static boolean isLocalClient(final HttpServletRequest request) {

        try {
            final InetAddress remote = InetAddress.getByName(request.getRemoteAddr());

            return remote.isLoopbackAddress();

        } catch (final UnknownHostException e) {
            log.error("Erreur lors de la vérification : isLoopbackAddress", e);
        }

        return false;
    }

    /**
     * Workaround to get the user
     *
     * @return L'utilisateur local
     */
    public static String getLocalUserName() {

        try {
            final StringBuilder builder = new StringBuilder();
            final Process process = Runtime.getRuntime().exec("qwinsta");
            final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            boolean deb = false;
            String line = null;

            while ((line = in.readLine()) != null) {
                if (line.indexOf("console") > 0) {
                    for (int j = line.indexOf("console") + 7; j < line.length(); j++) {

                        if (!deb && line.charAt(j) != 32) {
                            deb = true;
                        }

                        if (deb && line.charAt(j) == 32) {
                            return builder.toString().toLowerCase();
                        }

                        if (deb && ' ' != line.charAt(j)) {
                            builder.append(line.charAt(j));
                        }
                    }
                }
            }

        } catch (final IOException ioe) {
            throw new IllegalStateException("Impossible de récupérer l'utilisateur local", ioe);
        }

        return StringUtils.EMPTY;
    }
    WebUtils(){}
}

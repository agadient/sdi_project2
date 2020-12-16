package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    PasswordHasher phasher;
    Map<String, String> authenticatedUIDs = new HashMap();
    private final UserRepository repository;
    public AuthController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestBody Login login) {
        Iterable<Map<String, Object>> passwordAndSalt;
        try {
            passwordAndSalt = repository.findAuthByUsername(login.getUsername());
        } catch (Exception e) {
            return "Invalid username!";
        }
        String hash = "";
        String salt = "";
        String uid = "";
        for (Map<String, Object> obj : passwordAndSalt) {
            hash = obj.get("password_hash").toString();
            salt = obj.get("salt").toString();
            uid = obj.get("user_id").toString();
        }

        Boolean validLogin = phasher.checkPassword(hash, salt, login.getPassword());

        if (validLogin) {
            String cookieToken = phasher.generateCookieToken();
            Cookie cookie = new Cookie("sessionInfo", uid + "_" + cookieToken);
            authenticatedUIDs.put(uid, cookieToken);
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            return "Logged in!";
        }
        return "Bad login!";
    }

    @GetMapping("/validateUser")
    public String validateUser(HttpServletResponse response, @CookieValue("sessionInfo") String sessionInfo) {
        List<String> infoSplit = Arrays.asList(sessionInfo.split("_"));
        if (infoSplit.size() != 2) {
            try {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (Exception e) {

            }
            return "Invalid token!";
        }
        String uid =infoSplit.get(0);
        String token = infoSplit.get(1);
        if (token.equals(authenticatedUIDs.get(uid))) {
            return "Valid session!";
        }
        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {

        }
        return "Invalid session token!";
    }

    private static class Login {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}

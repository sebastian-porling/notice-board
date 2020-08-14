package se.experis.academy.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.session.model.Response;
import se.experis.academy.session.model.User;
import se.experis.academy.session.repository.UserRepository;
import se.experis.academy.session.util.SessionKeeper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Takes care of all user related actions
 * like login, register and logout
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    final private int AGE = 6000;

    /**
     * If the user exists and the password is correct we will add the user to our session store
     * And give the user a cookie of their user name and userid.
     * @param user With username and password
     * @param session The session of the user
     * @param response The response for the user to register cookies
     * @return Response entity giving information if it was successfull
     */
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user, HttpSession session, HttpServletResponse response) {
        if (userRepository.existsByUsername(user.getUsername())) {
            User temp = userRepository.findUserByUsername(user.getUsername());
            if(user.getPassword().equals(temp.getPassword())){
                Cookie userCookie = new Cookie("username", temp.getUsername());
                Cookie idCookie = new Cookie("id", temp.getId()+"");
                session.setMaxInactiveInterval(AGE);
                userCookie.setMaxAge(AGE);
                idCookie.setMaxAge(AGE);
                response.addCookie(userCookie);
                response.addCookie(idCookie);
                SessionKeeper.getInstance().AddSession(session.getId(), temp.getId());
                return new ResponseEntity<>(new Response(null, "LOGGED IN"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Response(null, "FAIL, WRONG PASS OR USERNAME"), HttpStatus.NOT_FOUND);
    }

    /**
     *  Unregisters the user from the session store and invalidates the session.
     * @param session Users session
     * @return ResponseEntity with logged out status
     */
    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpSession session) {
        SessionKeeper.getInstance().RemoveSession(session.getId());
        session.invalidate();
        return new ResponseEntity<>(new Response(null, "LOGGED OUT"), HttpStatus.OK);
    }

    /**
     * Registers a user if the username doesn't exist and logs them in.
     * @param user User credentials
     * @param session User session
     * @param response Response for registering cookies
     * @return ResponseEntity telling if it was successful
     */
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user, HttpSession session, HttpServletResponse response) {
        if(!userRepository.existsByUsername(user.getUsername())){
            User registeredUser = userRepository.save(user);
            Cookie userCookie = new Cookie("username", registeredUser.getUsername());
            Cookie idCookie = new Cookie("id", registeredUser.getId()+"");
            session.setMaxInactiveInterval(AGE);
            userCookie.setMaxAge(AGE);
            idCookie.setMaxAge(AGE);
            response.addCookie(userCookie);
            response.addCookie(idCookie);
            SessionKeeper.getInstance().AddSession(session.getId(), registeredUser.getId());
            return new ResponseEntity<>(new Response(null, "REGISTERED"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(null, "USER ALREADY EXISTS!"), HttpStatus.BAD_REQUEST);
    }
}

package se.experis.academy.session.util;

import java.util.HashMap;

/**
 * Takes care of all the sessions
 */
public class SessionKeeper {
    private static SessionKeeper sessionKeeperInstance = null;
    private HashMap<String, Long> validSessions = new HashMap<>();

    /**
     * Checks if the session exists
     * @param session session
     * @return true if it exists
     */
    public boolean CheckSession(String session) { return validSessions.containsKey(session); }

    /**
     * Gets the user id from the session
     * @param session session
     * @return user id
     */
    public Long getSessionValue(String session) { return validSessions.get(session); }

    /**
     * Adds a session to the list
     * @param session session
     * @param id user id
     */
    public void AddSession(String session, Long id){ validSessions.put(session, id); }

    /**
     * Removes a session
     * @param session session
     */
    public void RemoveSession(String session){
        if(getInstance().CheckSession(session)) validSessions.remove(session);
    }

    /**
     * Returns the singleton instance
     * @return singleton instance of SessionKeeper
     */
    public static SessionKeeper getInstance(){
        if (sessionKeeperInstance == null) sessionKeeperInstance = new SessionKeeper();
        return sessionKeeperInstance;
    }
}

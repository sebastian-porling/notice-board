package se.experis.academy.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.session.model.Comment;
import se.experis.academy.session.model.Notice;
import se.experis.academy.session.model.Response;
import se.experis.academy.session.model.User;
import se.experis.academy.session.repository.CommentRepository;
import se.experis.academy.session.repository.NoticeRepository;
import se.experis.academy.session.repository.UserRepository;
import se.experis.academy.session.util.SessionKeeper;

import javax.servlet.http.HttpSession;

/**
 * Takes care of all Notice and comments related methods
 */
@RestController
@RequestMapping("/notice")
public class NoticeBoardController {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    /**
     * Gets all the existing notice ordered by date.
     * @return ResponseEntity with all notices and successful message
     */
    @GetMapping("/all")
    public ResponseEntity<Response> getNotices() {
        return new ResponseEntity<>(new Response(noticeRepository.findAllByOrderByDateDesc(), "SUCCESS"), HttpStatus.OK);
    }

    /**
     * Gets a notice on the given id
     * @param id id of notice
     * @return ResponseEntity with notice and message
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getNotices(@PathVariable long id) {
        return new ResponseEntity<>(new Response(noticeRepository.findById(id), "SUCCESS"), HttpStatus.OK);
    }

    /**
     * Updates an existing notice if the user is logged in and is the author.
     * @param notice Modified notice
     * @param httpSession User session
     * @return ResponseEntity with notice or null and a message if it was successful
     */
    @PatchMapping("/update")
    public ResponseEntity<Response> updateNotice(@RequestBody Notice notice, HttpSession httpSession) {
        if (SessionKeeper.getInstance().CheckSession(httpSession.getId())) {
            if (noticeRepository.existsById(notice.getId())) {
                Notice original = noticeRepository.findById(notice.getId()).get();
                if(original.getAuthor().getId() == SessionKeeper.getInstance().getSessionValue(httpSession.getId())) {
                    Notice modified = noticeRepository.save(original
                            .setContent(notice.getContent())
                            .setDate(notice.getDateOriginal()));
                    return new ResponseEntity<>(new Response(modified, "MODIFIED"), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(new Response(null, "Notice don't exists or your not allowed."), HttpStatus.BAD_REQUEST);
    }

    /**
     * Adds a comment to a Notice if the user is logged in
     * @param comment Comment from the user
     * @param noticeId Notice id
     * @param httpSession User session
     * @return ResponseEntity with notice or null and message if it was successful
     */
    @PostMapping("/comment/{id}")
    public ResponseEntity<Response> addComment(@RequestBody Comment comment, @PathVariable(name = "id") Long noticeId, HttpSession httpSession) {
        if (SessionKeeper.getInstance().CheckSession(httpSession.getId())) {
            if (userRepository.existsById(SessionKeeper.getInstance().getSessionValue(httpSession.getId()))) {
                if (noticeRepository.existsById(noticeId)) {
                    User user = userRepository.findById(SessionKeeper
                            .getInstance().getSessionValue(httpSession.getId())).get();
                    Notice notice = noticeRepository.save(noticeRepository
                            .findById(noticeId).get()
                            .addComment(commentRepository
                                    .save(comment.setAuthor(user))));
                    return new ResponseEntity<>(new Response(notice, "Comment added!"), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(new Response(null, "Couldn't add comment!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Deletes a notice if the user is logged in and is the author
     * @param id Notice id
     * @param httpSession User session
     * @return ResponseEntity with a message if it was successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteNotice(@PathVariable(name = "id") long id, HttpSession httpSession) {
        if (noticeRepository.existsById(id)) {
            Notice notice = noticeRepository.findById(id).get();
            if(notice.getAuthor().getId() == SessionKeeper.getInstance().getSessionValue(httpSession.getId())) {
                noticeRepository.deleteById(id);
                return new ResponseEntity<>(new Response(null, "Deleted!"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Response(null, "Couldn't delete notice!"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates a notice if the user is logged in
     * @param notice Notice to save in database
     * @param httpSession User session
     * @return ResponseEntity with the saves notice or null and a message if it was successful
     */
    @PostMapping("/create")
    public ResponseEntity<Response> createNotice(@RequestBody Notice notice, HttpSession httpSession) {
        if (SessionKeeper.getInstance().CheckSession(httpSession.getId())) {
            Long userId = SessionKeeper.getInstance().getSessionValue(httpSession.getId());
            User user = userRepository.findById(userId).get();
            Notice saved = noticeRepository.save(notice.setAuthor(user));
            return new ResponseEntity<>(new Response(saved, "Notice created!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(null, "Couldn't create notice!"), HttpStatus.BAD_REQUEST);
    }
}

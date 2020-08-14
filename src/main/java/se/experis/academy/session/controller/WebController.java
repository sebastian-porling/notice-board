package se.experis.academy.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.experis.academy.session.repository.NoticeRepository;

/**
 * Takes care of the index mapping
 */
@Controller
public class WebController {

    @Autowired
    NoticeRepository noticeRepository;

    /**
     * Index page
     * @return Index.html
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

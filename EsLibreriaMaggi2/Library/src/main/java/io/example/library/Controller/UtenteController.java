package io.example.library.Controller;

import io.example.library.Dao.UtenteDao;
import io.example.library.Model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtenteController {

    @Autowired
    private UtenteDao userRepository;

    @GetMapping(value = "/")
    public String signIn(User user) {
        return "Registrazione";
    }

    @PostMapping(value = "/validationRegister")
    public String validationRegister(@Valid User user, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) return "Registrazione";
        for (User u : userRepository.findAll()) {
            if (u.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())) {
                bindingResult.addError(new ObjectError("SignUpError", "l'utente esiste già"));
                return "redirect:/";
            }
        }

        userRepository.save(user);
        session.setAttribute("loggedUser", user);

        return "Menu";
    }

    @GetMapping(value = "/login")
    public String login(User user) {
        return "login";
    }

    @PostMapping(value = "/validationLogin")
    public String validateLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpSession session) {
       User loggedUser = userRepository.login(username, password);
        if (loggedUser == null) return "redirect:/login";
        session.setAttribute("loggedUser", loggedUser);
        return "Menu";

    }
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.setAttribute("loggedUser", null);
        return "redirect:/";
    }
    @GetMapping(value = "/userInfo")
    public String userInfo(Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        model.addAttribute("user", session.getAttribute("loggedUser"));
        return "InfoUtente";
    }
}

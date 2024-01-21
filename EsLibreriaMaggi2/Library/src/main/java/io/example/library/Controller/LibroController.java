package io.example.library.Controller;


import io.example.library.Dao.LibroDao;
import io.example.library.Dao.UtenteDao;
import io.example.library.Dao.UtenteLibroDao;
import io.example.library.Model.Libro;
import io.example.library.Model.User;
import io.example.library.Model.UserLibro;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class LibroController {

    @Autowired
    private LibroDao libroRepository;

    @Autowired
    private UtenteDao utenteRepository;

    @Autowired
    private UtenteLibroDao utenteLibroDao;

    @GetMapping(value = "controlPanel")
    public String controlPanel(HttpSession session){
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        return "Menu";
    }


    @GetMapping(value = "/addBook")
    public String addBookPage(Libro libro, HttpSession session) {;
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";

        return "AggiungiLibro";
    }

    @PostMapping(value = "/addBookAction")
    public String addBookAction(@Valid Libro libro, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "AggiungiLibro";

        for(Libro b : libroRepository.findAll()){
            if(Objects.equals(b.getTitolo().toLowerCase(), libro.getTitolo().toLowerCase()) &&
                    b.getAutore().toLowerCase().equals(libro.getAutore().toLowerCase()))
                return "redirect:/bookInfo";
        }

        libroRepository.save(libro);

        return "redirect:/controlPanel";
    }


    @GetMapping(value = "/bookInfo")
    public String bookInfo(Model model,HttpSession session) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";

        model.addAttribute("books", libroRepository.findAll());
        return "InfoLibro";
    }

    @GetMapping(value = "/yourBooks")
    public String yourBooks(HttpSession session, Model model){
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";

        User loggedUser = (User) session.getAttribute("loggedUser");
        Optional<User> u = utenteRepository.findById(loggedUser.getId());

        List<Libro> userBooks = new ArrayList<>();
        for (UserLibro b :  u.get().getUserBooks()){
            userBooks.add(b.getLibro());
        }

        model.addAttribute("books", userBooks);

        return "MieiLibri";
    }

    @GetMapping(value="/addFavouriteBook")
    public String addFavouriteBook(HttpSession session, @RequestParam("favourite_book_id") long id){
        User loggedUser = (User) session.getAttribute("loggedUser");

        for (UserLibro userLibro : loggedUser.getUserBooks()){
            if(userLibro.getLibro().getId() == id) return "redirect:/yourBooks";
        }

        Libro book = libroRepository.findById(id);
        UserLibro userLibro = new UserLibro(loggedUser,book);
        utenteLibroDao.save(userLibro);
        return "redirect:/yourBooks";
    }

    @GetMapping(value = "/removeFromFavourites")
    public String removeFromFavourites(HttpSession session, @RequestParam("favourite_book_id") long id){
        User loggedUser = (User) session.getAttribute("loggedUser");

        for(UserLibro userLibro : utenteLibroDao.findByUserId(loggedUser.getId())){
            if(userLibro.getLibro().getId() == id) utenteLibroDao.deleteById(userLibro.getId());
        }

        return "redirect:/yourBooks";
    }

}


package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Services.SigninServices;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author petri
 */
@Controller
public class SignInController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired AccountRepository accountRepository;
    
    @Autowired
    private SigninServices signinServices;
    
    @GetMapping("/register")
    public String view(@ModelAttribute Account account) {
        
        return "signinForm";
    }
    
    @PostMapping("/register")
    public String create(@Valid @ModelAttribute Account account, 
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "signinForm";
        }
        Optional<Account> matches = 
                accountRepository.findByUsernameAndProfilename(account.getUsername(), account.getProfilename());
        if (matches.isPresent()) {
            model.addAttribute("inuse", true);
            return "signinForm";
        }
        String uncodedPassword = account.getPassword();
        account.setPassword(passwordEncoder.encode(account.getPassword())); 
        
        this.accountRepository.save(account);
        signinServices.authWithAuthManager(request, account.getUsername(), uncodedPassword); 
        
        model.addAttribute("message", "Good redirect");
        
        return "redirect:/MyFriendbook/" + account.getProfilename();
    }
    
}

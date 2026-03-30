package ma.ens.security.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/login")
  public String login(@RequestParam(name = "oauth2Error", required = false) String oauth2Error, Model model) {
    if (oauth2Error != null) {
      model.addAttribute("error", "Echec de connexion OAuth2. Verifie l'URI de redirection et le client secret.");
    }
    return "index";
  }

  @GetMapping("/profile")
  public String profile(Model model, @AuthenticationPrincipal OAuth2User user) {
    if (user == null) {
      return "redirect:/";
    }

    // Attributs typiques renvoyés par Google (OpenID Connect / userinfo).
    model.addAttribute("name", user.getAttribute("name"));
    model.addAttribute("email", user.getAttribute("email"));
    model.addAttribute("picture", user.getAttribute("picture"));
    return "profile";
  }
}


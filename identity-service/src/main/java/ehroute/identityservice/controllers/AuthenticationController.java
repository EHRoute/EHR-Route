package ehroute.identityservice.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ehroute.identityservice.models.LoginModel;
import ehroute.identityservice.utilities.app.ApiEndpoints;
import sh.ory.hydra.ApiClient;
import sh.ory.hydra.Configuration;
import sh.ory.hydra.api.AdminApi;
import sh.ory.hydra.model.AcceptLoginRequest;
import sh.ory.hydra.model.LoginRequest;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    @GetMapping(ApiEndpoints.Authentication.Login)
    public String login(Model model, String error, @RequestParam("login_challenge") String loginChallenge) {

        if (!StringUtils.isEmpty(error)) model.addAttribute("error", "Invalid username or password");

        // TODO: Cannot get login_challenge from query param, FIX
        if (StringUtils.isEmpty(loginChallenge)) {
            return "/error/error.html";
        }

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost");

        AdminApi oauthAdmin = new AdminApi(defaultClient);

        try {

            LoginRequest loginReq = oauthAdmin.getLoginRequest(loginChallenge);

            // If auth server was already able to authenticate the user, 
            // skip will be true and we do not need to re-authenticate the user
            if (loginReq.getSkip()) {

                AcceptLoginRequest loginRes = new AcceptLoginRequest();
                loginRes.setSubject(loginReq.getSubject());

                oauthAdmin.acceptLoginRequest(loginChallenge, loginRes);
                
                return "redirect back to hydra .. to be continued";

            }

            // TODO: Continue the flow

            return "/login/login.html";

        }
        catch (Exception Ex) {
            return "/error/error.html";
        }

    }


    @PostMapping(ApiEndpoints.Authentication.Login)
    public String login(@ModelAttribute(value = "loginModel") LoginModel loginModel) {
        return "";
    }


}

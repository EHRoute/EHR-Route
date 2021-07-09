package ehroute.identityservice.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ehroute.identityservice.models.LoginModel;
import ehroute.identityservice.utilities.app.ApiEndpoints;
import sh.ory.hydra.ApiException;
import sh.ory.hydra.Configuration;
import sh.ory.hydra.api.AdminApi;
import sh.ory.hydra.model.AcceptConsentRequest;
import sh.ory.hydra.model.AcceptLoginRequest;
import sh.ory.hydra.model.ConsentRequestSession;


@Controller
@RequestMapping(ApiEndpoints.Authentication.Main)
@SessionAttributes("loginChallenge")
public class AuthenticationController {


    private AdminApi oauthAdmin;


    @GetMapping(ApiEndpoints.Authentication.Login)
    public String login(
        Model model, 
        String error, 
        @RequestParam("login_challenge") String challenge
    ) {

        // Check for validation errors
        if (!StringUtils.isEmpty(error)) model.addAttribute("error", "Invalid username or password");

        // Validate login challenge
        if (StringUtils.isEmpty(challenge)) return "/error/error.html";
        else model.addAttribute("loginChallenge", challenge);

        // Construct auth provider's admin http client
        var defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:4445");
        oauthAdmin = new AdminApi(defaultClient);

        try {

            // TODO: Investigate using getLoginRequestAsync
            var loginReqInfo = oauthAdmin.getLoginRequest(challenge);

            // If auth server was already able to authenticate the user, skip will be true and we do not need 
            // to re-authenticate the user
            if (loginReqInfo.getSkip()) {

                // Initialize a login acceptance request
                var loginAcceptRequest = new AcceptLoginRequest();
                loginAcceptRequest.setSubject(loginReqInfo.getSubject());

                // Accept the user's login
                var loginAcceptResult = oauthAdmin.acceptLoginRequest(challenge, loginAcceptRequest);

                // Redirect back to auth server
                return "redirect:" + loginAcceptResult.getRedirectTo();

            }

            return "/login/login.html";

        }
        catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            return "/error/error.html";
        }

    }


    @PostMapping(ApiEndpoints.Authentication.Login)
    public String login(
        @ModelAttribute("loginModel") @Valid final LoginModel loginModel, 
        @ModelAttribute("loginChallenge") String challenge
    ) {

        // TODO:
        // Authenticate and validate user existance

        try {

            // Inialize a login request acceptance
            var loginAcceptRequest = new AcceptLoginRequest();
            loginAcceptRequest.subject("10"); // TODO: Set User ID
            loginAcceptRequest.remember(loginModel.getRemember());
            loginAcceptRequest.rememberFor(0l);

            // Accept the user's login
            var loginAcceptResult = oauthAdmin.acceptLoginRequest(challenge, loginAcceptRequest);

            // Redirect back to auth server
            return "redirect:" + loginAcceptResult.getRedirectTo();

        }
        catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            return "/error/error.html";
        }

    }

    
    @GetMapping(ApiEndpoints.Authentication.Consent)
    public String consent(@RequestParam("consent_challenge") String challenge) {

        // Validate login challenge
        if (StringUtils.isEmpty(challenge)) return "/error/error.html";

        try {

            var consentRequest = oauthAdmin.getConsentRequest(challenge);

            // Initialize consent acceptance request
            var consentAcceptRequest = new AcceptConsentRequest();
            consentAcceptRequest.grantAccessTokenAudience(consentRequest.getRequestedAccessTokenAudience());

            // Construct session data AKA claims, available in the ID Token
            consentAcceptRequest.session(
                new ConsentRequestSession().idToken(Map.of(
                    "UserId", "10"
                ))
            );

            // Grant all requested scopes
            consentAcceptRequest.setGrantScope(consentRequest.getRequestedScope());

            // Send the consent acceptance request
            var consentAcceptResult = oauthAdmin.acceptConsentRequest(challenge, consentAcceptRequest);

            // Redirect back to auth server
            return "redirect:" + consentAcceptResult.getRedirectTo();

        }
        catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            return "/error/error.html";
        }

    }    

}

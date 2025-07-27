package com.example.SpringStarter.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SpringStarter.Models.Account;
import com.example.SpringStarter.Security.AppBeanConfig;
import com.example.SpringStarter.Service.AccountService;
import com.example.SpringStarter.Service.EmailService;
import com.example.SpringStarter.Util.AppUtil;
import com.example.SpringStarter.Util.email.EmailDetails;

import jakarta.validation.Valid;

/**
 * AccountController handles user registration and login page requests.
 */
@Controller
public class AccountController {

    // Inject AccountService to handle account-related operations
    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;


   @Autowired
   private PasswordEncoder passwordEncoder;

    @Value("${site.domain}")
    private String siteDomain;

    @Value("${photo.prefix}")
    private String photoPrefix;

    @Value("${password.token.reset.timeout.minutes}")
    private int password_token_timeout;

    /**
     * Handles GET requests for the registration page.
     * Adds a new Account object to the model for form binding.
     * 
     * @param model The model to pass data to the view.
     * @return The registration page template name.
     */
    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account(); // Create a new empty account
        model.addAttribute("account", account); // Add account to the model
        return "register"; // Return the registration view
    }

    /**
     * Handles POST requests for user registration.
     * Saves the submitted account data using AccountService.
     * 
     * @param account The account object populated from the form.
     * @return Redirects to the home page after successful registration.
     */
    @PostMapping("/register")
    public String registerAccount(@Valid @ModelAttribute Account account, BindingResult result) {
        if (result.hasErrors()) {
            return "register"; // If there are validation errors, return to the registration page
        } else {
            accountService.save(account); // Save the new account
            return "redirect:/home"; // Redirect to home page

        }

    }

    /**
     * Handles GET requests for the login page.
     * 
     * @param model The model to pass data to the view (not used here).
     * @return The login page template name.
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Return the login view
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principal) {
        String authUser = "email";

        if (principal != null) {
            authUser = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.getByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account", account);
            model.addAttribute("photo", account.getPhoto());

            return "account_views/profile";
        } else {

            return "redirect:/?error";

        }

    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String post_profile(@Valid @ModelAttribute Account account, BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "account_views/profile";

        }

        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.getByEmail(authUser);

        if (optionalAccount.isPresent()) {
            Account account_by_id = accountService.findById(account.getId()).get();
            account_by_id.setUsername(account.getUsername());
            account_by_id.setDate_of_birth(account.getDate_of_birth());
            account_by_id.setGender(account.getGender());
            account_by_id.setEmail(account.getEmail());
            account_by_id.setPassword(account.getPassword());

            accountService.save(account_by_id);

            SecurityContextHolder.clearContext();

            return "redirect:/home";
        }

        return "redirect:/?error";

    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")
    public String update_photo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
            Principal principal) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No file upload");
            return "redirect:/profile";
        } else {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {

                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String randomFileName = UUID.randomUUID().toString() + extension;
                String relativePhotoPath = "/uploads/" + randomFileName;

                String absolute_fileLocation = AppUtil.get_upload_path(randomFileName);

                Path path = Paths.get(absolute_fileLocation);

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                redirectAttributes.addFlashAttribute("message", "photo uploaded successfully");

                // get logged in user account
                String authUser = "email";
                if (principal != null) {
                    authUser = principal.getName();
                }

                Optional<Account> optionalAccount = accountService.getByEmail(authUser);

                if (optionalAccount.isPresent()) {

                    Account account = optionalAccount.get();
                    account.setPhoto(relativePhotoPath);
                    accountService.save(account);

                }

                redirectAttributes.addFlashAttribute("message", "Photo uploaded successfully");

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Photo upload failed");

            }

            return "redirect:/profile";

        }

    }

    @GetMapping("/forgot-password")
    public String forgot_password(Model model) {
        return "account_views/forgot_password";
    }

    @PostMapping("/reset-password")
    public String reset_password(@RequestParam("email") String email, RedirectAttributes attributes, Model model) {
        Optional<Account> optionalAccount = accountService.getByEmail(email);

        if (optionalAccount.isPresent()) {
            // Account account =
            // accountService.getById(optionalAccount.get().getId()).get();
            Account account = optionalAccount.get();

            String reset_token = UUID.randomUUID().toString();
            account.setPasswordResetToken(reset_token);
            account.setPasswordResetTokenExpiry(LocalDateTime.now().plusMinutes(password_token_timeout));

            accountService.save(account);

            String reset_message = "THis is the reset password link: " + siteDomain + "change-password?token="
                    + reset_token;
            EmailDetails emailDetails = new EmailDetails(account.getEmail(), reset_message, "reset password demo");

            if (!emailService.simpleEmailSender(emailDetails)) {
                attributes.addFlashAttribute("error", "Error while sending email");
                return "redirect:/forgot-password";
            }

            attributes.addFlashAttribute("message", "password reset email sent");
            return "redirect:/login";
        } else {

            attributes.addFlashAttribute("error", "no user find with this email");
            return "redirect:/forgot-password";

        }

    }

    @GetMapping("/change-password")
    public String change_password(@RequestParam("token") String token, Model model,
            RedirectAttributes redirectAttributes) {
        if (token.equals("")) {
            redirectAttributes.addFlashAttribute("error", "Invalid error");

            return "redirect:/forgot-password";
        }
        Optional<Account> optionalAccount = accountService.findByToken(token);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            long account_id = account.getId();

            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(account.getPasswordResetTokenExpiry())) {

                redirectAttributes.addFlashAttribute("error", "Token expired");
                return "account_views/forgot-password";

            }

            model.addAttribute("account", account);
            return "account_views/change_password";

        } else {

            redirectAttributes.addFlashAttribute("error", "Invalid token");
            return "account_views/forgot-password";

        }

    }

    @PostMapping("/change-password")
    public String postChangePassword(@ModelAttribute Account account, RedirectAttributes attributes){
        Optional<Account> optionalAccount = accountService.findById(account.getId());
        if (optionalAccount.isPresent()) {
            Account accountById = optionalAccount.get();
            accountById.setPassword(passwordEncoder.encode(account.getPassword()));
            accountById.setPasswordResetToken("");
            accountService.save(accountById);

        attributes.addFlashAttribute("message", "password updated");
        

        }else{
            attributes.addFlashAttribute("error", "error in update");
        }


        return "redirect:/login";



    }

}

//package uz.quickly.controller;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import uz.quickly.domain.Role;
//import uz.quickly.domain.enumeration.ERole;
//import uz.quickly.domain.User;
//import uz.quickly.payload.request.SignupRequest;
//import uz.quickly.payload.request.LoginRequest;
//import uz.quickly.payload.response.MessageResponse;
//import uz.quickly.repository.RoleRepository;
//import uz.quickly.repository.UserRepository;
//import uz.quickly.security.JwtUtils;
//import uz.quickly.service.UserDetailsImpl;
//import uz.quickly.service.UserService;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@Controller
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    final
//    UserService userService;
//
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "/login")
//    public String log_form(HttpServletRequest request, Model model) {
//        model = userService.isLogged(request, model);
//        model.addAttribute("message", "");
//        model.addAttribute("content", "login_form");
//        return "fragments/layout";
//    }
//
//    @PostMapping("/signin")
//    public String authenticateUser(@ModelAttribute(name = "userName") String userName
//            , @ModelAttribute(name = "password") String password,
//                                              HttpServletRequest request, Model model) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userName, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        model = userService.isLogged(request, model);
//        model.addAttribute("content", "index");
//        return "fragments/layout";
//
////        return ResponseEntity.ok(new JwtResponse(jwt,
////                userDetails.getId(),
////                userDetails.getUsername(),
////                userDetails.getEmail(),
////                roles)
////                );
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsUserByUserName(signUpRequest.getUserName())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUserName(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByRolName(ERole.ROLE_USER.toString())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByRolName(ERole.ROLE_ADMIN.toString())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByRolName(ERole.ROLE_USER.toString())
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
//}
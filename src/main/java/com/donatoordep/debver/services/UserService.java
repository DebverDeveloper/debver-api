package com.donatoordep.debver.services;

import com.auth0.jwt.JWT;
import com.donatoordep.debver.builders.dto.response.AuthenticationResponseDTOBuilder;
import com.donatoordep.debver.dto.request.AuthenticationRequestDTO;
import com.donatoordep.debver.dto.request.UserRequestDTO;
import com.donatoordep.debver.dto.response.AuthenticationResponseDTO;
import com.donatoordep.debver.dto.response.CartResponseDTO;
import com.donatoordep.debver.dto.response.UserResponseDTO;
import com.donatoordep.debver.entities.Code;
import com.donatoordep.debver.entities.User;
import com.donatoordep.debver.enums.RoleName;
import com.donatoordep.debver.mapper.UserMapper;
import com.donatoordep.debver.repositories.UserRepository;
import com.donatoordep.debver.security.TokenJWTService;
import com.donatoordep.debver.services.business.rules.user.findByName.FindByNameArgs;
import com.donatoordep.debver.services.business.rules.user.findByName.FindByNameValidation;
import com.donatoordep.debver.services.business.rules.user.register.RegisterUserArgs;
import com.donatoordep.debver.services.business.rules.user.register.RegisterUserValidation;
import com.donatoordep.debver.services.exceptions.CodeNotValidException;
import com.donatoordep.debver.utils.ConvertingType;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenJWTService tokenJWTService;

    @Autowired
    private List<RegisterUserValidation> userRegisterValidations;

    @Autowired
    private List<FindByNameValidation> findByNameValidations;

    @Autowired
    private MailService mailService;

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findByName(String name, Pageable pageable) {

        findByNameValidations.forEach(v -> v.verification(
                new FindByNameArgs(repository, name, pageable)));

        return mapper.convertUserPageToUserResponseDTOPage(repository.findByName(name, pageable));
    }

    public AuthenticationResponseDTO login(AuthenticationRequestDTO objectOfAuthentication) {

        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(
                objectOfAuthentication.getEmail(), objectOfAuthentication.getPassword()));

        String token = tokenJWTService.generateToken((User) authenticate.getPrincipal());

        return AuthenticationResponseDTOBuilder.builder()
                .login(authenticate.getName())
                .issuer(JWT.decode(token).getIssuer())
                .expireToken(JWT.decode(token).getExpiresAt())
                .token(token)
                .build();
    }

    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) throws MessagingException {

        userRegisterValidations.forEach(v -> v.verification(new RegisterUserArgs(dto, repository)));
        User user = mapper.convertUserRequestDTOToUser(dto);
        user.setEnabled(false);
        user.setVerificationCode(generateCode());

        user.setPassword(encoder.encode(user.getPassword()));

        user.setRoles(roleService.separateRolesWithHierarchy(
                ConvertingType.convertStringToEnum(RoleName.class, dto.getRole())));

        UserResponseDTO responseDTO = mapper.convertUserToUserResponseDTO(repository.save(user));

        sendVerificationEmail(user);

        return responseDTO;
    }

    @Transactional
    public Code verifyAccount(Code code) {
        User user = repository.findByCode(code.getCode());
        if (user != null) {
            user.setEnabled(true);
            repository.save(user);
        } else {
            throw new CodeNotValidException();
        }
        return code;
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String content = String.format("Please verify your email!!\nCode verification: %s",
                user.getVerificationCode());

        mailService.sendEmailToClient("Code", user.getEmail(), content);
    }

    private String generateCode() {
        return RandomStringUtils.randomAlphabetic(64);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO me(User user) {
        return mapper.convertUserToUserResponseDTO(user);
    }

    public void update(User user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    public CartResponseDTO myCart(User user) {
        return new CartResponseDTO((user.getCart()));
    }
}
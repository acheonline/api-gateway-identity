package ru.achernyavskiy0n.identity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.achernyavskiy0n.identity.response.UserInfo;
import ru.achernyavskiy0n.identity.dto.UserLoginDto;
import ru.achernyavskiy0n.identity.dto.UserProfileRegistrationDto;
import ru.achernyavskiy0n.identity.dto.UserUpdateDto;
import ru.achernyavskiy0n.identity.response.SuccessfulResponse;
import ru.achernyavskiy0n.identity.response.UserLoginResponse;
import ru.achernyavskiy0n.identity.service.IdentityService;
import ru.achernyavskiy0n.identity.service.IdentityServiceException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/identity")
public class IdentityController {

  private final IdentityService identityService;

  @PostMapping(path = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessfulResponse> saveProfile(
      @RequestBody UserProfileRegistrationDto userDto) throws IdentityServiceException {
    identityService.create(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
    return ResponseEntity.ok(
        new SuccessfulResponse(HttpStatus.OK.value(), "User registration successful"));
  }

  @GetMapping(path = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserInfo findUser(@PathVariable("username") String username)
      throws IdentityServiceException {
    return UserInfo.fromUser(identityService.findUser(username));
  }

  @PutMapping(path = "/user/{username}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessfulResponse> update(
      @PathVariable("username") String username, @RequestBody UserUpdateDto request)
      throws IdentityServiceException {
    identityService.update(
        username,
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getPhone());
    return ResponseEntity.ok(
        new SuccessfulResponse(HttpStatus.OK.value(), "User updated successful"));
  }

  @PostMapping(path = "/user/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserLoginResponse> create(@RequestBody UserLoginDto request)
      throws Exception {
    String token = identityService.authenticate(request.getUsername(), request.getPassword());
    return ResponseEntity.ok(
        new UserLoginResponse(true, HttpStatus.OK.value(), token, "User login successful"));
  }
}

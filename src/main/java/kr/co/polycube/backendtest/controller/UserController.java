package kr.co.polycube.backendtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.polycube.backendtest.aspect.annotation.ValidAspect;
import kr.co.polycube.backendtest.dto.request.UserRegisterReqDto;
import kr.co.polycube.backendtest.dto.request.UserUpdateReqDto;
import kr.co.polycube.backendtest.dto.response.UserGetRespDto;
import kr.co.polycube.backendtest.dto.response.UserRegisterRespDto;
import kr.co.polycube.backendtest.dto.response.UserUpdateRespDto;
import kr.co.polycube.backendtest.exception.CustomException;
import kr.co.polycube.backendtest.model.UserInfo;
import kr.co.polycube.backendtest.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ValidAspect
    public ResponseEntity<UserRegisterRespDto> registerUser(@Valid @RequestBody UserRegisterReqDto userDto) {
        UserInfo user = userService.registerUser(userDto.getId(), userDto.getName());
        return ResponseEntity.ok(new UserRegisterRespDto(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetRespDto> getUser(@PathVariable("id") Long id) {
        if (!userService.userExists(id)) {
            throw new CustomException("User not found");
        }
        UserInfo user = userService.getUser(id);
        return ResponseEntity.ok(new UserGetRespDto(user.getId(), user.getName()));
    }

    @PutMapping("/{id}")
    @ValidAspect
    public ResponseEntity<UserUpdateRespDto> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateReqDto userDto) {
        if (!userService.userExists(id)) {
            throw new CustomException("User not found");
        }
        UserInfo user = userService.updateUser(id, userDto.getName());
        return ResponseEntity.ok(new UserUpdateRespDto(user.getId(), user.getName()));
    }
}

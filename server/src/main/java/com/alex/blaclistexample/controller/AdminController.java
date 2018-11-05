package com.alex.blaclistexample.controller;

import com.alex.blaclistexample.dto.TokenDto;
import com.alex.blaclistexample.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/make_admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TokenDto makeAdmin(@RequestHeader String authToken,
                              @RequestParam String username) {
        return adminService.makeAdmin(username);
    }

    @PutMapping("/make_user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TokenDto makeUser(@RequestHeader String authToken,
                             @RequestParam String username) {
        return adminService.makeUser(username);
    }

    @PutMapping("/block")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity block(@RequestHeader String authToken,
                                @RequestParam String username) {
        adminService.block(username);
        return new ResponseEntity(HttpStatus.OK);
    }
}

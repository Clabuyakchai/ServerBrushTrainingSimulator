package com.clabuyakchai.controller;

import javax.servlet.http.HttpServletRequest;

import com.clabuyakchai.dto.StatisticsDataDTO;
import com.clabuyakchai.dto.UserDataDTO;
import com.clabuyakchai.dto.UserResponseDTO;
import com.clabuyakchai.model.LoginModel;
import com.clabuyakchai.model.Statistics;
import com.clabuyakchai.model.StatisticsModel;
import com.clabuyakchai.model.User;
import com.clabuyakchai.service.StatisticsService;
import com.clabuyakchai.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private StatisticsService statisticsService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/users/signin")
  @ApiOperation(value = "${UserController.signin}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
  public String login(@RequestBody LoginModel model) {
    return userService.signin(model.getUsername(), model.getPassword());
  }

  @PostMapping("/users/signup")
  @ApiOperation(value = "${UserController.signup}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 422, message = "Username is already in use"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
      return userService.signup(modelMapper.map(user, User.class));
  }

  @DeleteMapping(value = "/users/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "${UserController.delete}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public String delete(@ApiParam("Username") @PathVariable String username) {
    userService.delete(username);
    return username;
  }

  @GetMapping(value = "/users/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
    return modelMapper.map(userService.search(username), UserResponseDTO.class);
  }

  @GetMapping(value = "/users/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }

  @GetMapping("/statistics/{username}")
  @ApiOperation(value = "${UserController.mystatistics}")
  @ApiResponses(value = {//
    @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public List<Statistics> mystatistics(@PathVariable String username) {
    return  statisticsService.getStatisticsByUserId(username);
  }

  @PostMapping("/statistics/add")
  @ApiOperation(value = "${UserController.addstatistics}")
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 422, message = "Username is already in use"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public void addstatistics(@ApiParam("Addstatistics Statistics") @RequestBody StatisticsDataDTO statistics) {
      statisticsService.addStatistics(modelMapper.map(statistics, Statistics.class), statistics.getUsername());
  }

  @PostMapping("/statistics/addall")
  @ApiOperation(value = "${UserController.addallstatistics}")
  @ApiResponses(value = {//
          @ApiResponse(code = 400, message = "Something went wrong"), //
          @ApiResponse(code = 403, message = "Access denied"), //
          @ApiResponse(code = 422, message = "Username is already in use"), //
          @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public void addallstatistics(@ApiParam("Addstatistics Statistics") @RequestBody List<StatisticsDataDTO> statistics) {
    statisticsService.addAllStatistics(statistics);
  }
}

package com.clabuyakchai.controller;

import com.clabuyakchai.exception.CustomException;
import com.clabuyakchai.model.LoginModel;
import com.clabuyakchai.model.Statistics;
import com.clabuyakchai.model.User;
import com.clabuyakchai.service.StatisticsService;
import com.clabuyakchai.service.UserService;
import com.clabuyakchai.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WebUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserValidator userValidator;

    /*First method on start application*/
    /*Попадаем сюда на старте приложения (см. параметры аннтоции и настройки пути после деплоя) */
    @RequestMapping(value = {"/", "/signin"}, method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("userInfo", new LoginModel());

        return "signin";
    }

    /*как только на index.jsp подтвердится форма
    <spring:form method="post"  modelAttribute="userJSP" action="check-user">,
    то попадем вот сюда
     */
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String login(@ModelAttribute("userInfo") LoginModel user, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String token = null;
        if(user.getUsername() != null && user.getPassword() != null) {
            try {
                token = userService.signin(user.getUsername(), user.getPassword());
            } catch (CustomException cust){
                model.addAttribute("error", "Your username and password is invalid.");
            }
        } else {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if(token != null){
            String tokBear = "Bearer " + token;

            HttpSession session = request.getSession(false);
            session.setAttribute("userInfo", user);

            response.addHeader("Authorization", tokBear);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/userpage");
            dispatcher.forward(request, response);

        } else {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("userSignUp", new User());

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("userSignUp") User userForm, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        String token = null;

        if(userForm.getUsername() != null){
            try {
                token = userService.signup(modelMapper.map(userForm, User.class));
            } catch (CustomException cust){
                model.addAttribute("error", "Username is already in use");
            }
        } else {
            model.addAttribute("error", " Should not be empty");
        }

        if(token != null){
            String tokBear = "Bearer " + token;

            HttpSession session = request.getSession(false);

            LoginModel loginModel = new LoginModel();

            loginModel.setUsername(userForm.getUsername());
            loginModel.setPassword(userForm.getPassword());

            session.setAttribute("userInfo", loginModel);

            response.addHeader("Authorization", tokBear);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/userpage");

            dispatcher.forward(request, response);
        }
        return "signup";
    }

    @RequestMapping(value = {"/userpage"}, method = RequestMethod.POST)
    public String welcome(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HttpSession session = request.getSession(false);
        LoginModel user = (LoginModel) session.getAttribute("userInfo");
        List<Statistics> list= statisticsService.getStatisticsByUserId(user.getUsername());

        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        pw.write("<html><head><link href=\"/css/table.css\" rel=\"stylesheet\"></head><body>");
        pw.write("<table class=\"table_dark\">");

        pw.write("<tr>" +
                "<th>description</th>" +
                "<th>counter</th>" +
                "<th>data</th>" +
                "</tr>");

        for (int statisticsIndex = 0; statisticsIndex < list.size(); statisticsIndex++)
        {
            pw.write("<tr>");

            pw.write(
                    "<td>" + list.get(statisticsIndex).getDescription() + "</td>" +
                    "<td>" + list.get(statisticsIndex).getCounter() + "</td>" +
                            "<td>" + dateFormat.format(new Date(list.get(statisticsIndex).getData())) + "</td>");

            pw.write("</tr>");
        }

        pw.write("</table>");
        pw.write("</body></html>");

        pw.close();

        return "userPage";
    }
}


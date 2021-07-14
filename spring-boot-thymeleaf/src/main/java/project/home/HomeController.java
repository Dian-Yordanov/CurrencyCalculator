package project.home;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.Application;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
class HomeController {

    @RequestMapping("/")
    public String index(
            @RequestParam(value = "id", required = false) Integer id,
            Model model
    ) {
        model.addAttribute("currency1", LocalDateTime.now());
        model.addAttribute("currency2", "Record Deleted Sucessfully");
        model.addAttribute("input", new ViewInformationObject());

        return "index2";
    }

//    @GetMapping("/")
//    String index(Model model) {
//        model.addAttribute("currency1", LocalDateTime.now());
//        model.addAttribute("currency2", "Record Deleted Sucessfully");
//        model.addAttribute("input", new ViewInformationObject());
//
//
////        System.out.println("Hello test !!!!!!!!!");
//        return "index2";
//    }

    @GetMapping("/currencyDataInputURL")
    public String indexInput(@ModelAttribute ViewInformationObject viewInformationObject, Model model){

        System.out.println(viewInformationObject.getId());
        System.out.println(viewInformationObject.getContent());

        return "layouts/currencyDataInputURL";
    }

    @PostMapping("/currencyDataInputURL")
    public String indexInputSubmit(@ModelAttribute ViewInformationObject viewInformationObject, Model model){
        model.addAttribute("currencyDataInputURL", new ViewInformationObject());
        System.out.println("Hello tesvccfgfdgfdt !!!!!!!!!");

        return "redirect:/";
    }

    @GetMapping("/alternativeIndex")
    String alternativeIndex(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("msg", "Record Deleted Sucessfully");
        return "index2";
    }

    @RequestMapping(value = "/result")
    String doStuffMethod(Model model) throws ClassNotFoundException, IOException {
        System.out.println("Success");


        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));

                model.addAttribute("msg", rs.getString("name"));
                model.addAttribute("id", rs.getString("id"));

            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }



        Application.downloadInitializer();

//        https://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm?download=xml&search=&lang=BG

        return "layouts/showdata";
    }

    @GetMapping("/security/user_page")
    String userPage(Model model) {
        model.addAttribute("now", LocalDateTime.now());

        return "security/user_page";
    }

    @GetMapping("/showdata")
    String showdata(Model model) throws ClassNotFoundException{

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));

                model.addAttribute("msg", rs.getString("name"));
                model.addAttribute("id", rs.getString("id"));

            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }

        return "layouts/showdata";
    }

    @GetMapping("properties")
    @ResponseBody
    java.util.Properties properties() {
        return System.getProperties();
    }

    @GetMapping("/input")
    public String inputForm(@ModelAttribute ViewInformationObject viewInformationObject, Model model)  {
        model.addAttribute("input", new ViewInformationObject());

        System.out.println("viewInformationObject.getId()");
        System.out.println(viewInformationObject.getId());
        System.out.println(viewInformationObject.getContent());

        return "layouts/input";
    }

    @PostMapping("/input")
    public String inputSubmit(@ModelAttribute ViewInformationObject viewInformationObject, Model model) throws ClassNotFoundException{
        System.out.println(viewInformationObject.getId());
        System.out.println(viewInformationObject.getContent());
        model.addAttribute("input", viewInformationObject);

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
            {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\sample.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                statement.executeUpdate("drop table if exists person");
                statement.executeUpdate("create table person (id integer, name string)");
                statement.executeUpdate("insert into person values(1, '"+ viewInformationObject.getId() +"')");
                statement.executeUpdate("insert into person values(2, '"+ viewInformationObject.getContent() +"')");
                ResultSet rs = statement.executeQuery("select * from person");
                while(rs.next())
                {
                    // read the result set
                    System.out.println("name = " + rs.getString("name"));
                    System.out.println("id = " + rs.getInt("id"));
                }
            }
            catch(SQLException e)
            {
                // if the error message is "out of memory",
                // it probably means no database file is found
                System.err.println(e.getMessage());
            }
            finally
            {
                try
                {
                    if(connection != null)
                        connection.close();
                }
                catch(SQLException e)
                {
                    // connection close failed.
                    System.err.println(e);
                }
        }

        return "redirect:/";
    }

    @RequestMapping(value="/security/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/security/access-denied";
    }

    @GetMapping("/downloads/Exchange_Rates.pdf")
    public ResponseEntity<InputStreamResource> getTermsConditions() throws FileNotFoundException {

        String filePath = "src\\main\\resources\\downloads\\";
        String fileName = "Exchange_Rates.pdf";
        File file = new File(filePath+fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" +fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

//    @GetMapping(value = "/downloads")
//    public void showPDF(HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        InputStream inputStream = new FileInputStream(new File("/downloads/" + "Exchange_Rates.pdf"));
//        int nRead;
//        while ((nRead = inputStream.read()) != -1) {
//            response.getWriter().write(nRead);
//        }
//    }

//    @GetMapping("/downloads/Exchange_Rates.pdf")
//    public void showPDF(HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        InputStream inputStream = new FileInputStream(new File("/downloads/" + "Exchange_Rates.pdf"));
//        int nRead;
//        while ((nRead = inputStream.read()) != -1) {
//            response.getWriter().write(nRead);
//        }
//    }
}


package sample;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 *
 * @author OmOm-muron
 */
@WebServlet(urlPatterns={"/preUpload"})
public class UploadDisplayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        // Here, write code about objects to be uploaded files.
        
        req.getRequestDispatcher("/upload.jsp").forward(req, rsp);
    }
}   

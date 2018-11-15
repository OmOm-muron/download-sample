
package sample;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 *
 * @author user01
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
                
        // ダウンロード対象のディレクトリ、ファイル名を設定・取得
        String uploadDirectory = "C:/upload";
        String filename = req.getParameter("filename");
        
        if(filename == null) {
            req.setAttribute("message","ファイルのダウンロードに失敗しました。");
            RequestDispatcher rd = req.getRequestDispatcher("/done.jsp");
            rd.forward(req, rsp);
            return;
        }
        
        // ダウンロードファイルの取得
        File downloadFile = new File(uploadDirectory + "/" + filename);
        FileInputStream fis = new FileInputStream(downloadFile);
        BufferedInputStream buf = new BufferedInputStream(fis);
        
        // 全角ファイル名への対応
        filename = URLEncoder.encode(filename, "UTF-8");
        
        rsp.setContentType("application/octet-stream; charset=\"UTF-8\"");
        rsp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        
        ServletOutputStream out = rsp.getOutputStream();
        
        int length = 0;
        byte[] buffer = new byte[1024];
        while((length = buf.read(buffer)) >= 0) {
            out.write(buffer, 0, length);
        }
        out.close();
        out.flush();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        doGet(req,rsp);
    }
}

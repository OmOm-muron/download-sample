
package sample;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 *
 * @author user01
 */
@WebServlet("/preDownload")
public class DownloadDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        // アップロードディレクトリをここで指定
        String uploadDirectory = "C:/upload";
        
        // アップロードディレクトリ内のファイル一覧を取得
        // ディレクトリのFileインスタンス化
        File directory = new File(uploadDirectory);
        
        // ファイルのみ（ディレクトリは含まない）をリストで取得
        List<String> fileList = new ArrayList<String>();
        for (String filename : directory.list()) {
            String filePath = uploadDirectory + "/" + filename;
            if (new File(filePath).isFile()) {
                fileList.add(filename);
            }
        }
        
        req.setAttribute("fileList",fileList);
        
        RequestDispatcher rd = req.getRequestDispatcher("/download.jsp");
        rd.forward(req,rsp);
    }
}

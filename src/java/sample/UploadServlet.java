
package sample;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 *
 * @author user01
 */
@WebServlet(urlPatterns={"/upload"})
@MultipartConfig(location="C:/upload/") // Rewrite here so you would like to upload to.
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        // upload.jspの<input type="file" name="uploadfile">
        // からMultipart形式のアップロードコンテンツの内容を取得
        Part part = req.getPart("uploadfile");
        
        // アップロードされたコンテンツ(part)から
        // ファイル名部分(フルパス)を取得する
        String filename = null;
        for(String cd : part.getHeader("Content-Disposition").split(";")) {
            cd = cd.trim();
            log(cd); //javax.servlet.GenericServlet.log(String msg)
            
            if(cd.startsWith("filename")) {
                filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
                log("upload file: " + filename);
                break;
            }
        }
        
        // アップロードしたファイルを書き出す
        String message = null;
        if (filename != null) {
            log(">> file write start.");
            
            // OS依存のファイルパス文字列を置換
            filename = filename.replace("\\", "/");
            
            // ファイル名のみを抜き出す
            // フルパスに含まれる最後の"/"より後ろの文字列を抜き出す
            // e.g. C:/first/second/final/filename からfilenameを抜き出す
            int pos = filename.lastIndexOf("/");
            if (pos > 0) {
                filename = filename.substring(pos + 1);
            }
            log("filename : " + filename);
            part.write(filename);
            
            log("    complete!");
            
            message = "[ " + filename + " ]のアップロードが完了しました。";
        } else {
            log("upload filename is blank.");
            message = "アップロードが失敗しました。";
        }
        
        req.setAttribute("message", message);
        
        RequestDispatcher rd = req.getRequestDispatcher("/done.jsp");
        rd.forward(req, rsp);
    }
}

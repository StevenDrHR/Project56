import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;

import static spark.Spark.get;

/**
 * Created by nilmor on 10/27/2016.
 */
public class Controller {

    public String htmlToString(String htmlFile) {
        try {
            // If you are using maven then your files
            // will be in a folder called resources.
            // getResource() gets that folder
            // and any files you specify.

            URL url = getClass().getResource(htmlFile);

            // Return a String which has all
            // the contents of the file.
            Path path = Paths.get(url.toURI());

            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            // Add your own exception handlers here.
        }
        return null;
    }

    public  void RenderLoginView(){
        Controller renderView = new Controller();
        get("/hello", (req, res) -> renderView.htmlToString("HTML/RegisterScreen.html"));
    }
    public void DataInsert( Deque<Modal> list) {{
            if (list.isEmpty()) {
                System.out.println("hallo");
            }
         System.out.println(list.getFirst().getUsername().toString());

    }
}
}



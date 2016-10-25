/**
 * Created by nilmor on 10/25/2016.
 */
import com.sun.org.apache.xerces.internal.parsers.BasicParserConfiguration;

import static spark.Spark.*;

public class Main {
    BasicConfigurator.configure()
    public static void main(String[] args) {
    get("/hello", (req, res) -> "Hello World");
}
}



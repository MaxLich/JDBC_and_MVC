package maxlich.app;


import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by maxlich on 18.07.17.
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        //initialization
        Model model = new Model(new ServiceDB());
        View view = new View();
        Controller controller = new Controller(view,model);
        view.setController(controller);

        //start the program
        view.run();



    }

}

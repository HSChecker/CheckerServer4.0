import tools.PARAMETER;
import tcp.Connect;

import java.io.IOException;

/**
 * RUN¿‡
 *
 * @author HanShuo
 * @Date 2020/4/23 13:43
 */
public class RUN {

    public static void main(String[] ar) throws IOException, ClassNotFoundException {

        PARAMETER.run();

        new Connect();
    }

}

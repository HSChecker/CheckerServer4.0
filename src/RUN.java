import tools.PARAMETER;
import tcp.Connect;

import java.io.IOException;

/**
 * RUN类
 *
 * @author HanShuo
 * @Date 2020/4/23 13:43
 */
public class RUN {

    public static void main(String[] ar) throws IOException, ClassNotFoundException {

        //此行必须存在，请勿删除，作用为优先调用PARAMETER启动代码
        PARAMETER.run();

        new Connect();
    }

}

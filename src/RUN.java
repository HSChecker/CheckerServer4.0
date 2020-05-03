import AI.Mtkl.Tree;
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

        //静态参数加载
        PARAMETER.run();

        //开始运行蒙特卡洛树搜索
        new Tree(PARAMETER.threadNum);


        new Connect();
    }

}

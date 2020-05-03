package AI.Mtkl.SeachLevelContrally;

import AI.Mtkl.MtklSeachThread;

/**
 * FreeSeachThread¿‡
 *
 * @author HanShuo
 * @Date 2020/5/3 17:03
 */
public class FreeSeachThread implements LevelContrally{

    public FreeSeachThread() {
    }

    @Override
    public MtklSeachThread saveAndStopThread() {
        return null;
    }

    @Override
    public void runSeachThread() {

    }

    @Override
    public boolean getWhetherContinue() {
        return false;
    }
}

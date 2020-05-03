package AI.Mtkl.SeachLevelContrally;

import AI.Mtkl.MtklSeachThread;

/**
 * LevelContrally½Ó¿Ú
 *
 * @author HanShuo
 * @Date 2020/5/3 16:52
 */
public interface LevelContrally {

    MtklSeachThread saveAndStopThread();
    void runSeachThread();

    boolean getWhetherContinue();

}

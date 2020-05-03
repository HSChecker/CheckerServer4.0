package tcp.connectPackage.permanentPacket;

import AI.AI;
import room.Room;
import tcp.NewConnect;
import tcp.connectPackage.ConnectListen;

/**
 * createRoom¿‡
 *
 * @author HanShuo
 * @Date 2020/5/1 23:32
 */
public class createRoom extends ConnectListen {

    private NewConnect nc;

    public createRoom(NewConnect nc) {
        super("createRoom");
        this.nc = nc;
    }

    @Override
    public void run(String[] packet) {
        if(packet[1].equals("Me"))
            if(packet[2].equals("AI"))
                new Room(nc,new AI());
            else
                new Room(nc,null);
        else if(packet[1].equals("AI"))
            new Room(new AI(),nc);
        else
            new Room(null,nc);
    }
}

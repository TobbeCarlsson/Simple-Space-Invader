public class Ovvelord extends Entity {
    public int speed = 10;
    public int hdg = 1;
    public Ovvelord (GameObject go, int speed)
    {
        super(go);
    }
    public void MoveSide ()
    {
        go.MoveRelative(new int[] {speed*hdg, 0});
    }
}

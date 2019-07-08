import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
public abstract class Location extends MessageServer  implements Action {
    private String name;
    public static String showMe;
    protected List<Location> InnerLocation =Collections.synchronizedList(new ArrayList<Location>());
    protected List<Thing> InnerThing = Collections.synchronizedList (new Vector<Thing>());//наш вектор, по заданию
    protected ArrayList<Trolls> InnerTrolls = new ArrayList<Trolls>();

    public void GoTo(Location location, Trolls ... trolls){//Тролли могут ходить!

        for (Trolls tr : trolls) {
            if (getName() != location.getName()) {

                InnerTrolls.remove(trolls);
                location.InnerTrolls.add(tr);
                addMessageWithN(tr.TrollName + " ушел в " + location.getName() + " из " + getName());
                tr.ThingActionList.clear();//персонаж ушел и у него убрались прошлые действия
            }
        }
    }


    public void AddInnerLocation(Location... locations) {
        for (Location loc : locations) {
            InnerLocation.add(loc);
        }
    }

    public void AddInnerThing(Thing... things) {
        for (Thing th : things) {
            InnerThing.add(th);
        }
    }

    public void AddInnerTrolls(Trolls... trolls) {
        for (Trolls th : trolls) {
            InnerTrolls.add(th);
        }
    }

    public String DescribeLocations() {
        showMe = "fr ";

        for (Location loc : InnerLocation) {
            showMe += loc.getName();
            showMe += " ";
            DescribeThing();

            //loc.DescribeThing();
            //loc.DescribeTrolls();//подумо
            //*

        }
        return showMe;
    }




    public void DescribeThing() {
        for (Thing thing : InnerThing) {
            showMe += thing.getName();
        }
    }

    public void DescribeTrolls() {

        for (Trolls troll : InnerTrolls) {
            System.out.print(troll.getName());
            troll.doActions();
        }
        System.out.println();
    }
    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void addMessage(String message) {
        super.addMessage(message);
    }

    @Override
    public void addMessageWithN(String message) {
        super.addMessageWithN(message);
    }

    public void action() {

    }
}

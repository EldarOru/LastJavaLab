import sun.util.resources.LocaleData;

import javax.sql.rowset.serial.SerialJavaObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public  class Thing  implements Serializable {
    protected ArrayList<Thing> ThingInThing = new ArrayList<Thing>();

    public ArrayList<ThingActions> ThingActionList= new ArrayList<ThingActions>();
    public String name;
    public String direction;
    public final Date thingDate;
    public final int weight;
    public String creator;
    public String date;
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getThingDate() {
        return thingDate;
    }

    public int getWeight() {
        return weight;
    }

    public Thing(String name, String direction, int weight, String creator,String date){
        this.name = name;
        this.direction = direction;
        this.weight = weight;
        thingDate = new Date();
        this.creator = creator;
        this.date = date;
    }
    public Thing(String name, String direction, int weight, String creator) {
        this.name = name;
        this.direction = direction;
        this.weight = weight;
        thingDate = new Date();
        this.creator = creator;
    }
    public String getDate1(){return date;}
    public String getCreator(){return creator;}
    public String getName(){
        return name;
    }
    public void AddThingInThing(Thing ... things) {
        for (Thing th : things) {
            ThingInThing.add(th);
        }
    }



    public void addThingAction(ThingActions ... thingActions){
        for(ThingActions thAc : thingActions){
            ThingActionList.add(thAc);
        }
    }



    public void doActions(){
        int index = 0;
        int count = ThingActionList.size();
        for (ThingAction thAction : ThingActionList){
            thAction.run();
            index += 1;
            if (index != count){
                System.out.print("и");

            }
        }
    }
}

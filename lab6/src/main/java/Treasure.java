public class Treasure extends Thing {
    public Treasure(String name, String direction, int weight, String creator, ThingActions... thingActions) {
        super(name,direction,weight,creator);
        addThingAction(thingActions);
    }


    public void action() {

    }


    public void getDescribe() {

    }
}

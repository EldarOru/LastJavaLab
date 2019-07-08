import java.util.ArrayList;

public abstract class Trolls extends MessageServer {
    public String TrollName;
    public Trolls(String TrollName){
        this.TrollName = TrollName;
    }
    public ArrayList<ThingActions> ThingActionList= new ArrayList<ThingActions>();
    public ArrayList<Thing> Inventory = new ArrayList<Thing>();

    public void addThingAction(ThingActions ... thingActions){
        for(ThingActions thAc : thingActions){
            ThingActionList.add(thAc);
        }
    }
    public void take(Thing...things){
       addMessageWithN( getName() + " положил в инвентарь ");
        for (Thing thing : things){
            Inventory.add(thing);
            addMessageWithN(thing.getName() + " ");
        }
        addMessage("");
    }
   /* public void openChest (Thing thing) throws CheckException{//срочно нужно исключение, мне и моему другу тоже
        addMessageWithN(getName() + " пытается открыть " + thing.name);
        int i = 0;
        if (Inventory.get(i).name == "топор" & Inventory.size() != 0 ){
            addMessageWithN(getName() + " смогла открыть " + thing.name + " используя " + Inventory.get(i).name);
            PreciousStones.Diamond diamond = new PreciousStones.Diamond("алмаз","n",1);
            PreciousStones.Emerald emerald = new PreciousStones.Emerald("изумруд","n",2);
            take(diamond,emerald);
        }
        else {
            throw new CheckException(getName() + " не смогла открыть сундук ");
        }
    }*/
    public void doActions(String ... actionNames){
        int index = 0;
        int count = actionNames.length;
        for (String acname : actionNames){
            for (ThingActions thAction : ThingActionList){
                if (thAction.getName().equals(acname)){
                    thAction.run();
                    index += 1;
                    if (index != count){
                        System.out.print("и");
                    }
                    break;
                }
            }
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

    public String getName(){
        return TrollName;
    }
}

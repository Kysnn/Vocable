package MineDict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Sinan
 */
public class LineController {

    private ArrayList<Vocable> Vocables = new ArrayList<Vocable>();

    public ArrayList<Vocable> getVocables() {
        return Vocables;
    }

    public void AddNewVocable(String IEng, String ITr, String IExmp) {
        Vocable NewVocable;
        Boolean Added = false;
        for (int i = 0; i < Vocables.size(); i++) {
            if (Vocables.get(i).VEng.equals(IEng)) {
                if (!Vocables.get(i).VTr_List.contains(ITr)) {
                    Vocables.get(i).VTr_List.add(ITr);
                }
                Vocables.get(i).VExample.add(ITr + "--" + IExmp);
                Added = true;
                break;
            }
        }
        if (!Added) {
            NewVocable = new Vocable(IEng);
            NewVocable.VTr_List.add(ITr);
            NewVocable.VExample.add(ITr + "--" + IExmp);
            Vocables.add(NewVocable);

        }

    }

    public void Output() {
        for (Vocable Item : Vocables) {
            System.out.println("Eng: " + Item.VEng);
            System.out.println("Tr:");
            for (String Tr : Item.VTr_List) {
                System.out.println(Tr);
            }
            System.out.println("Example:");
            for (String Exmp : Item.VExample) {
                System.out.println(Exmp);
            }
            System.out.println("-----------------");
        }

    }

    public ArrayList<String> FillScroll() {
        ArrayList<String> Transfer = new ArrayList<String>();
        for (Vocable Vocb : Vocables) {
            Transfer.add(Vocb.VEng);

        }
        Collections.sort(Transfer);
        System.out.println(Transfer.toString());
        return Transfer;
    }

    public ArrayList<String> Search(String Str) {
        ArrayList<String> Transfer = new ArrayList<String>();
        for (Vocable Vcb : Vocables) {
            if (Vcb.VEng.toLowerCase().contains(Str.toLowerCase())) {
                Transfer.add(Vcb.VEng);
            }

        }
        if (!Transfer.isEmpty()) {
            Collections.sort(Transfer);
        }
        return Transfer;

    }

    public ArrayList<String> ReturnTr(String Str) {
        ArrayList<String> Transfer = new ArrayList<String>();
        for (Vocable Vcb : Vocables) {
            if (Vcb.VEng.equals(Str)) {
                Transfer = Vcb.VTr_List;
                break;
            }

        }
        Collections.sort(Transfer);
        return Transfer;
    }

    public ArrayList<String> ReturnExmp(String StrEng, String StrTr) {
        ArrayList<String> Transfer = new ArrayList<String>();
        for (Vocable Vcb : Vocables) {
            if (Vcb.VEng.equals(StrEng)) {
                for (String VExmp : Vcb.VExample) {
                    if (VExmp.split("--")[0].equals(StrTr)) {
                        Transfer.add(VExmp);
                    }
                }
            }

        }
        return Transfer;
    }
    
   public void RemoveWord(String Str){
       for(int i=0; i<Vocables.size(); i++){
           if(Vocables.get(i).VEng.equals(Str)){
               Vocables.remove(i);   
           }
           
       }
       
   }

    public static class Vocable implements Serializable {

        public String VEng;
        public ArrayList<String> VExample = new ArrayList<String>();
        public ArrayList<String> VTr_List = new ArrayList<String>();

        public Vocable(String VEng) {
            this.VEng = VEng;
        }

    }
}

package facade.securite;

/**
 * Created by YohanBoichut on 06/12/2016.
 */
public class Challenge {


    String cleSecrete;
    String valeurChallenge;

    public Challenge(String cleSecrete, String valeurChallenge) {
        this.cleSecrete = cleSecrete;
        this.valeurChallenge = valeurChallenge;
    }

    public String getValeurChallenge() {
        return valeurChallenge;
    }

    @Override
   public String toString() {
       return this.cleSecrete+this.valeurChallenge;

   }

    public boolean chalengeReleve(String valeurProposee) {
        return valeurProposee.equals(valeurChallenge);
    }
}

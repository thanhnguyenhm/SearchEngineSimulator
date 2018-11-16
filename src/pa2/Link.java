package pa2;

/**
 * Class Link represents a typical Link object
 * @author thanhnguyen
 */
public class Link{
    //declare instance variables
    private int id;
    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int totalScore;
    private String name;
    private int pageRank;

    public Link(int id, int score1, int score2, int score3, int score4, String name) {
        this.id = id;
        this.totalScore = score1 + score2 + score3 + score4;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.name = name;
    }

    public Link(String name, int totalScore) {
        this.name = name;
        this.totalScore = totalScore;
    }

    public Link(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPageRank(int pageRank) { this.pageRank = pageRank; }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public int getScore3() {
        return score3;
    }

    public int getScore4() {
        return score4;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getPageRank() { return pageRank; }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String out;
        if(this.getId()<10) {
            if(this.getTotalScore()<100) {
                out = "Link: 0" + getId() + "::Score: 0" + getTotalScore() +
                        "::Title: " + getName();
            } else
                out = "Link: 0" + getId() + "::Score: " + getTotalScore() +
                        "::Title: " + getName();
        }
        else {
            if(this.getTotalScore()<100) {
                out = "Link: " + getId() + "::Score: 0" + getTotalScore() +
                        "::Title: " + getName();
            }else
                out = "Link: " + getId() + "::Score: " + getTotalScore() +
                        "::Title: " + getName();
        }
        return out;
    }

    public String toString(int rank) {

        String out;
        String rankStr = "";
        if(rank < 10) {
            rankStr = "0" + rank;
        } else { rankStr = "" + rank; }

        if(this.getId()<10) {
            if(this.getTotalScore()<100) {
                out = "Rank " + rankStr + ": Link: 0" + getId() + "::Score: 0" + getTotalScore() +
                        "::Title: " + getName();
            } else
                out = "Rank " + rankStr + ": Link: 0" + getId() + "::Score: " + getTotalScore() +
                        "::Title: " + getName();
        }
        else {
            if(this.getTotalScore()<100) {
                out = "Rank " + rankStr + ": Link: " + getId() + "::Score: 0" + getTotalScore() +
                        "::Title: " + getName();
            }else
                out = "Rank " + rankStr + ": Link: " + getId() + "::Score: " + getTotalScore() +
                        "::Title: " + getName();
        }
        return out;
    }
}

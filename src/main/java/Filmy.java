import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "filmy")
public class Filmy {

    @Id
    @GeneratedValue//(strategy=GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int movie_id;
    @Column(name = "movie_name")
    private String movie_name;
    @Column(name = "pegi")
    private String pegi;
    @Column(name = "release_d")
    private int release_d;
    @Column(name = "score")
    private int score;




    public Filmy(int id, String movie_name, String pegi, int release_d, int score) {
        this.movie_id = id;
        this.movie_name = movie_name;
        this.pegi = pegi;
        this.release_d = release_d;
        this.score = score;
    }

    public Filmy() {

    }


    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public int getRelease_d() {
        return release_d;
    }

    public void setRelease_d(int release_d) {
        this.release_d = release_d;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Filmy{" +
                "id=" + movie_id +
                ", movie_name='" + movie_name + '\'' +
                ", pegi=" + pegi +
                ", release_d=" + release_d +
                ", score=" + score +
                '}';
    }
}



